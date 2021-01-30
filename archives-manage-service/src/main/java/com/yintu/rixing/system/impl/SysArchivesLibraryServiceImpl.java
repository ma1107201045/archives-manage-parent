package com.yintu.rixing.system.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.common.CommTableField;
import com.yintu.rixing.common.ICommTableFieldService;
import com.yintu.rixing.dto.system.SysArchivesLibraryFormDto;
import com.yintu.rixing.enumobject.EnumFlag;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.system.*;
import com.yintu.rixing.util.TableNameUtil;
import com.yintu.rixing.util.TreeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统档案库表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2021-01-11
 */
@Service
@Slf4j
public class SysArchivesLibraryServiceImpl extends ServiceImpl<SysArchivesLibraryMapper, SysArchivesLibrary> implements ISysArchivesLibraryService {

    @Autowired
    private ISysTemplateLibraryFieldService iSysTemplateLibraryFieldService;
    @Autowired
    private ISysArchivesLibraryFieldDefaultService iSysArchivesLibraryFieldDefaultService;
    @Autowired
    private ISysArchivesLibraryFieldService iSysArchivesLibraryFieldService;
    @Autowired
    private ICommTableFieldService iCommTableFieldService;

    @Override
    public void save(SysArchivesLibraryFormDto sysArchivesLibraryFormDto) {
        Integer parentId = sysArchivesLibraryFormDto.getParentId();
        String name = sysArchivesLibraryFormDto.getName();
        Short type = sysArchivesLibraryFormDto.getType();
        Integer number = sysArchivesLibraryFormDto.getNumber();
        String dataKey = sysArchivesLibraryFormDto.getDataKey();
        Integer templateLibraryId = sysArchivesLibraryFormDto.getTemplateLibraryId();
        if (type == 2 && (number == null || dataKey == null || templateLibraryId == null))
            throw new BaseRuntimeException("档案库编号或者key或者模板库id不能为空");
        if (type == 2) {
            List<Integer> ids1 = this.listByNumber(number);
            if (!ids1.isEmpty())
                throw new BaseRuntimeException("档案编号不能重复");
            List<Integer> ids2 = this.iSysArchivesLibraryFieldDefaultService.listByDataKey(dataKey);
            if (!ids2.isEmpty())
                throw new BaseRuntimeException("key不能与系统默认重复");
            List<Integer> ids3 = this.listByDataKey(dataKey);
            if (!ids3.isEmpty())
                throw new BaseRuntimeException("key不能重复");
        }
        SysArchivesLibrary sysArchivesLibrary = new SysArchivesLibrary();
        if (parentId != -1) {
            sysArchivesLibrary = this.getById(sysArchivesLibraryFormDto.getParentId());
            if (sysArchivesLibrary != null) {
                if (type == 1 && sysArchivesLibrary.getType() == 2)
                    throw new BaseRuntimeException("档案库下边不能添加目录");
            } else return;
        }

        BeanUtil.copyProperties(sysArchivesLibraryFormDto, sysArchivesLibrary);
        this.save(sysArchivesLibrary);
        if (type == 2) {
            List<SysArchivesLibraryField> sysArchivesLibraryFields = new ArrayList<>();
            List<CommTableField> commTableFields = new ArrayList<>();

            List<SysArchivesLibraryFieldDefault> sysArchivesLibraryFieldDefaults = iSysArchivesLibraryFieldDefaultService.list();
            for (SysArchivesLibraryFieldDefault sysArchivesLibraryFieldDefault : sysArchivesLibraryFieldDefaults) {
                //从系统默认中的字段复制到档案库的字段
                SysArchivesLibraryField sysArchivesLibraryField = new SysArchivesLibraryField();
                BeanUtil.copyProperties(sysArchivesLibraryFieldDefault, sysArchivesLibraryField, "id");
                sysArchivesLibraryField.setArchivesLibraryId(sysArchivesLibrary.getId());
                sysArchivesLibraryField.setSystem(EnumFlag.True.getValue());
                sysArchivesLibraryFields.add(sysArchivesLibraryField);
                CommTableField commTableField = iCommTableFieldService.findByDataKeyAndSysArchivesLibraryField(dataKey, sysArchivesLibraryField);
                commTableFields.add(commTableField);
            }

            List<SysTemplateLibraryField> sysTemplateLibraryFields = iSysTemplateLibraryFieldService.listByTemplateLibraryId(templateLibraryId);
            for (SysTemplateLibraryField sysTemplateLibraryField : sysTemplateLibraryFields) {
                String dataKey1 = sysTemplateLibraryField.getDataKey();
                //从模板库中的字段复制到档案库的字段
                List<Integer> idList = sysArchivesLibraryFieldDefaults.stream().filter(sysArchivesLibraryFieldDefault -> sysArchivesLibraryFieldDefault.getDataKey().equals(dataKey1)).map(SysArchivesLibraryFieldDefault::getId).collect(Collectors.toList());
                //判断模板库字段是否跟系统默认字段一样
                if (idList.isEmpty()) {
                    //判断模板库字段是否跟档案库回退记录表定义字段一样
                    if (!dataKey1.equals(iCommTableFieldService.findFixed().getFieldName())) {
                        SysArchivesLibraryField sysArchivesLibraryField = new SysArchivesLibraryField();
                        BeanUtil.copyProperties(sysTemplateLibraryField, sysArchivesLibraryField, "id");
                        sysArchivesLibraryField.setArchivesLibraryId(sysArchivesLibrary.getId());
                        sysArchivesLibraryFields.add(sysArchivesLibraryField);
                        sysArchivesLibraryField.setSystem(EnumFlag.False.getValue());
                        CommTableField commTableField = iCommTableFieldService.findByDataKeyAndSysArchivesLibraryField(dataKey, sysArchivesLibraryField);
                        commTableFields.add(commTableField);
                    }
                }
            }
            iSysArchivesLibraryFieldService.saveBatch(sysArchivesLibraryFields);
            iCommTableFieldService.addTable(TableNameUtil.getFullTableName(dataKey), name, commTableFields);
            //新增回退管理记录表
            commTableFields.add(iCommTableFieldService.findFixed());
            iCommTableFieldService.addTable(TableNameUtil.getRollbackTableName(dataKey), name + TableNameUtil.ROLLBACK_COMMENT_SUFFIX, commTableFields);
        }
    }

    @Override
    public void removeTree(Integer id) {
        SysArchivesLibrary sysArchivesLibrary = this.getById(id);
        if (sysArchivesLibrary != null && sysArchivesLibrary.getType() == 2) {
            String oldDataKey = sysArchivesLibrary.getDataKey();
            iCommTableFieldService.removeTableByTableName(TableNameUtil.getFullTableName(oldDataKey));
            iCommTableFieldService.removeTableByTableName(TableNameUtil.getRollbackTableName(oldDataKey));
        }
        this.removeById(id);
        QueryWrapper<SysArchivesLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysArchivesLibrary::getParentId, id);
        List<Integer> ids = this.listObjs(queryWrapper, obj -> (Integer) obj);
        for (Integer integer : ids) {
            this.removeTree(integer);
        }
    }

    @Override
    public void updateById(SysArchivesLibraryFormDto sysArchivesLibraryFormDto) {
        Integer id = sysArchivesLibraryFormDto.getId();
        Integer parentId = sysArchivesLibraryFormDto.getParentId();
        String name = sysArchivesLibraryFormDto.getName();
        Short type = sysArchivesLibraryFormDto.getType();
        Integer number = sysArchivesLibraryFormDto.getNumber();
        String dataKey = sysArchivesLibraryFormDto.getDataKey();
        Integer templateLibraryId = sysArchivesLibraryFormDto.getTemplateLibraryId();
        if (type == 2 && (number == null || dataKey == null || templateLibraryId == null))
            throw new BaseRuntimeException("档案库编号或者key或者模板库id不能为空");
        if (type == 2) {
            List<Integer> ids1 = this.listByNumber(number);
            if (!ids1.isEmpty() && !ids1.get(0).equals(id))
                throw new BaseRuntimeException("档案编号不能重复");
            List<Integer> ids2 = this.iSysArchivesLibraryFieldDefaultService.listByDataKey(dataKey);
            if (!ids2.isEmpty())
                throw new BaseRuntimeException("key不能与系统默认重复");
            List<Integer> ids3 = this.listByDataKey(dataKey);
            if (!ids3.isEmpty() && !ids3.get(0).equals(id))
                throw new BaseRuntimeException("key不能重复");
        }

        SysArchivesLibrary sysArchivesLibrary = this.getById(id);
        if (sysArchivesLibrary != null) {
            Short oldType = sysArchivesLibrary.getType();
            String oldName = sysArchivesLibrary.getName();
            String oldDataKey = sysArchivesLibrary.getDataKey();
            Integer oldTemplateLibraryId = sysArchivesLibrary.getTemplateLibraryId();
            //判断修改跟上级的类型对比
            if (parentId != -1) {
                SysArchivesLibrary last = this.getById(parentId);
                if (last != null) {
                    if (type == 1 && last.getType() == 2)
                        throw new BaseRuntimeException("目录上边不能有档案库");
                } else return;
            }
            //判断修改的跟当前的类型对比
            if (type == 1 && oldType == 2) {
                sysArchivesLibrary.setNumber(null);
                sysArchivesLibrary.setDataKey(null);
                sysArchivesLibrary.setTemplateLibraryId(null);
                //删除表
                iCommTableFieldService.removeTableByTableName(TableNameUtil.getFullTableName(oldDataKey));
                iCommTableFieldService.removeTableByTableName(TableNameUtil.getRollbackTableName(oldDataKey));
            }
            //判断修改跟下级的类型对比
            if (type == 2) {
                List<Integer> ids = this.listByIdAndType(id, (short) 1);
                if (!ids.isEmpty())
                    throw new BaseRuntimeException("档案库下边不能有目录");
            }
            BeanUtil.copyProperties(sysArchivesLibraryFormDto, sysArchivesLibrary);
            this.updateById(sysArchivesLibrary);
            if (type == 2) {
                String tableName1 = TableNameUtil.getFullTableName(oldDataKey);
                String tableName11 = TableNameUtil.getRollbackTableName(oldDataKey);
                String tableName2 = TableNameUtil.getFullTableName(dataKey);
                String tableName21 = TableNameUtil.getRollbackTableName(dataKey);
                iCommTableFieldService.isHasDataByTableName(tableName1);
                //更改表的注释
                String finalName = oldName;
                if (!oldName.equals(name)) {
                    finalName = name;
                    iCommTableFieldService.editTableCommentByTableName(tableName1, name);
                    iCommTableFieldService.editTableCommentByTableName(tableName11, name + TableNameUtil.ROLLBACK_COMMENT_SUFFIX);
                }
                //更改表的表名
                String finalTableName1 = tableName1;
                String finalTableName2 = tableName11;
                if (!oldDataKey.equals(dataKey)) {
                    finalTableName1 = tableName2;
                    finalTableName2 = tableName21;
                    iCommTableFieldService.editTableNameByTableName(tableName1, tableName2);
                    iCommTableFieldService.editTableNameByTableName(tableName11, tableName21);
                }
                //先删除系统的字段
                List<Integer> ids = iSysArchivesLibraryFieldService.listByArchivesLibraryIdAndSystem(id, EnumFlag.True.getValue());
                iSysArchivesLibraryFieldService.removeByIds(ids);
                //如果更换模板库则删除属于此模板库的字段以及表，并且重新添加字段跟表结构
                if (!oldTemplateLibraryId.equals(templateLibraryId)) {
                    ids = iSysArchivesLibraryFieldService.listByArchivesLibraryIdAndTemplateLibraryId(id, oldTemplateLibraryId);
                    if (!ids.isEmpty())
                        iSysArchivesLibraryFieldService.removeByIds(ids);

                    List<SysArchivesLibraryField> sysArchivesLibraryFields = new ArrayList<>();
                    List<CommTableField> commTableFields = new ArrayList<>();

                    List<SysArchivesLibraryFieldDefault> sysArchivesLibraryFieldDefaults = iSysArchivesLibraryFieldDefaultService.list();
                    for (SysArchivesLibraryFieldDefault sysArchivesLibraryFieldDefault : sysArchivesLibraryFieldDefaults) {
                        //从系统默认中的字段复制到档案库的字段
                        SysArchivesLibraryField sysArchivesLibraryField = new SysArchivesLibraryField();
                        BeanUtil.copyProperties(sysArchivesLibraryFieldDefault, sysArchivesLibraryField, "id");
                        sysArchivesLibraryField.setArchivesLibraryId(sysArchivesLibrary.getId());
                        sysArchivesLibraryField.setSystem(EnumFlag.True.getValue());
                        sysArchivesLibraryFields.add(sysArchivesLibraryField);
                        CommTableField commTableField = iCommTableFieldService.findByDataKeyAndSysArchivesLibraryField(dataKey, sysArchivesLibraryField);
                        commTableFields.add(commTableField);
                    }

                    List<SysTemplateLibraryField> sysTemplateLibraryFields = iSysTemplateLibraryFieldService.listByTemplateLibraryId(templateLibraryId);
                    for (SysTemplateLibraryField sysTemplateLibraryField : sysTemplateLibraryFields) {
                        String dataKey1 = sysTemplateLibraryField.getDataKey();
                        //判断模板库字段是否跟档案库之前添加的字段一样
                        List<Integer> idList = iSysArchivesLibraryFieldService.listByArchivesLibraryIdDataKeys(id, dataKey1);
                        if (idList.isEmpty()) {
                            //判断模板库字段是否跟系统默认字段一样
                            idList = sysArchivesLibraryFieldDefaults.stream().filter(sysArchivesLibraryFieldDefault -> sysArchivesLibraryFieldDefault.getDataKey().equals(dataKey1)).map(SysArchivesLibraryFieldDefault::getId).collect(Collectors.toList());
                            if (idList.isEmpty()) {
                                //判断模板库字段是否跟档案库回退记录表定义字段一样
                                if (!dataKey1.equals(iCommTableFieldService.findFixed().getFieldName())) {
                                    SysArchivesLibraryField sysArchivesLibraryField = new SysArchivesLibraryField();
                                    BeanUtil.copyProperties(sysTemplateLibraryField, sysArchivesLibraryField, "id");
                                    sysArchivesLibraryField.setArchivesLibraryId(id);
                                    sysArchivesLibraryField.setSystem(EnumFlag.False.getValue());
                                    sysArchivesLibraryFields.add(sysArchivesLibraryField);
                                    CommTableField commTableField = iCommTableFieldService.findByDataKeyAndSysArchivesLibraryField(dataKey, sysArchivesLibraryField);
                                    commTableFields.add(commTableField);
                                }
                            }
                        }
                    }
                    iSysArchivesLibraryFieldService.saveBatch(sysArchivesLibraryFields);
                    //DDL操作
                    iCommTableFieldService.removeTableByTableName(finalTableName1);
                    iCommTableFieldService.removeTableByTableName(finalTableName2);

                    iCommTableFieldService.addTable(finalTableName1, finalName, commTableFields);
                    //新增回退管理记录表
                    commTableFields.add(iCommTableFieldService.findFixed());
                    iCommTableFieldService.addTable(finalTableName2, finalName + TableNameUtil.ROLLBACK_COMMENT_SUFFIX, commTableFields);
                }
            }
        }
    }


    @Override
    public List<Integer> listByNumber(Integer number) {
        QueryWrapper<SysArchivesLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(SysArchivesLibrary::getId)
                .eq(SysArchivesLibrary::getNumber, number);
        return this.listObjs(queryWrapper, id -> (Integer) id);
    }

    @Override
    public List<Integer> listByIdAndType(Integer id, Short type) {
        if (id == null)
            throw new BaseRuntimeException("档案库id不能为空");
        QueryWrapper<SysArchivesLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(SysArchivesLibrary::getId)
                .eq(SysArchivesLibrary::getParentId, id);
        return this.listObjs(queryWrapper, integer -> (Integer) integer);
    }

    @Override
    public List<Integer> listByDataKey(String dataKey) {
        if (StrUtil.isEmpty(dataKey))
            throw new BaseRuntimeException("key不能为空");
        QueryWrapper<SysArchivesLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(SysArchivesLibrary::getId)
                .eq(SysArchivesLibrary::getDataKey, dataKey);
        return this.listObjs(queryWrapper, id -> (Integer) id);
    }

    @Override
    public List<TreeUtil> listTree(Integer parentId) {
        QueryWrapper<SysArchivesLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysArchivesLibrary::getParentId, parentId);
        List<SysArchivesLibrary> sysArchivesLibraries = this.list(queryWrapper);
        List<TreeUtil> treeUtils = new ArrayList<>();
        for (SysArchivesLibrary sysArchivesLibrary : sysArchivesLibraries) {
            TreeUtil treeUtil = new TreeUtil();
            treeUtil.setId(sysArchivesLibrary.getId().longValue());
            treeUtil.setLabel(sysArchivesLibrary.getName());
            treeUtil.setA_attr(BeanUtil.beanToMap(sysArchivesLibrary));
            treeUtil.setChildren(this.listTree(sysArchivesLibrary.getId()));
            treeUtils.add(treeUtil);
        }
        return treeUtils;
    }
}
