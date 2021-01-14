package com.yintu.rixing.system.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.common.CommTableField;
import com.yintu.rixing.common.ICommTableFieldService;
import com.yintu.rixing.dto.system.SysArchivesLibraryFormDto;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.system.*;
import com.yintu.rixing.util.TableNameUtil;
import com.yintu.rixing.util.TreeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.util.Length;

import java.util.ArrayList;
import java.util.List;

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
    private ISysTemplateLibraryFieldTypeService iSysTemplateLibraryFieldTypeService;
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
            List<Integer> ids2 = this.listByDataKey(dataKey);
            if (!ids2.isEmpty())
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
            List<SysTemplateLibraryField> sysTemplateLibraryFields = iSysTemplateLibraryFieldService.listByTemplateLibraryId(templateLibraryId);
            for (SysTemplateLibraryField sysTemplateLibraryField : sysTemplateLibraryFields) {
                //从模板库中的字段导复制到档案库的字段
                SysArchivesLibraryField sysArchivesLibraryField = new SysArchivesLibraryField();
                BeanUtil.copyProperties(sysTemplateLibraryField, sysArchivesLibraryField, "id");
                sysArchivesLibraryField.setArchivesLibraryId(sysArchivesLibrary.getId());
                sysArchivesLibraryFields.add(sysArchivesLibraryField);
                //动态创建表
                CommTableField commTableField = new CommTableField();
                commTableField.setFieldName(sysTemplateLibraryField.getDataKey());
                SysTemplateLibraryFieldType sysTemplateLibraryFieldType = iSysTemplateLibraryFieldTypeService.getById(sysTemplateLibraryField.getTemplateLibraryFieldTypeId());
                commTableField.setDataType(sysTemplateLibraryFieldType.getDataKey());
                commTableField.setLength(sysTemplateLibraryField.getLength());
                commTableField.setIsNull(sysTemplateLibraryField.getRequired() == 1 ? (short) 0 : (short) 1);
                commTableField.setIsIndex(sysTemplateLibraryField.getIndex().shortValue());
                commTableField.setComment(sysTemplateLibraryField.getName());
                commTableFields.add(commTableField);
            }
            iSysArchivesLibraryFieldService.saveBatch(sysArchivesLibraryFields);
            iCommTableFieldService.addTable(TableNameUtil.getFullTableName(dataKey), name, commTableFields);
        }
    }

    @Override
    public void removeTree(Integer id) {
        SysArchivesLibrary sysArchivesLibrary = this.getById(id);
        if (sysArchivesLibrary != null && sysArchivesLibrary.getType() == 2) {
            String oldDataKey = sysArchivesLibrary.getDataKey();
            iCommTableFieldService.removeTableByTableName(TableNameUtil.getFullTableName(oldDataKey));
        }
        this.removeById(id);
        QueryWrapper<SysArchivesLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(SysArchivesLibrary::getId)
                .eq(SysArchivesLibrary::getParentId, id);
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
            List<Integer> ids2 = this.listByDataKey(dataKey);
            if (!ids2.isEmpty() && !ids2.get(0).equals(id))
                throw new BaseRuntimeException("key不能重复");
        }
        SysArchivesLibrary sysArchivesLibrary = this.getById(id);
        SysArchivesLibrary rollBack = new SysArchivesLibrary();//回滚做准备
        BeanUtil.copyProperties(sysArchivesLibrary, rollBack);
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
                String tableName2 = TableNameUtil.getFullTableName(dataKey);
                //更改表的注释
                if (!oldName.equals(name))
                    iCommTableFieldService.editTableCommentByTableName(tableName1, name);
                //更改表的表名
                if (!oldDataKey.equals(dataKey)) {
                    iCommTableFieldService.editTableNameByTableName(tableName1, tableName2);
                }
                //如果更换模板库则删除属于此模板库的字段以及表，并且重新添加字段跟表结构
                if (!oldTemplateLibraryId.equals(templateLibraryId)) {
                    List<Integer> ids = iSysArchivesLibraryFieldService.listByArchivesLibraryIdAndTemplateLibraryId(id, oldTemplateLibraryId);
                    if (!ids.isEmpty())
                        iSysArchivesLibraryFieldService.removeByIds(ids);
                    List<SysArchivesLibraryField> sysArchivesLibraryFields = new ArrayList<>();
                    List<CommTableField> commTableFields = new ArrayList<>();
                    List<SysTemplateLibraryField> sysTemplateLibraryFields = iSysTemplateLibraryFieldService.listByTemplateLibraryId(templateLibraryId);
                    for (SysTemplateLibraryField sysTemplateLibraryField : sysTemplateLibraryFields) {
                        //从模板库中的字段导复制到档案库的字段
                        SysArchivesLibraryField sysArchivesLibraryField = new SysArchivesLibraryField();
                        BeanUtil.copyProperties(sysTemplateLibraryField, sysArchivesLibraryField, "id");
                        sysArchivesLibraryField.setArchivesLibraryId(id);
                        sysArchivesLibraryFields.add(sysArchivesLibraryField);
                        //动态创建表
                        CommTableField commTableField = new CommTableField();
                        commTableField.setFieldName(sysTemplateLibraryField.getDataKey());
                        SysTemplateLibraryFieldType sysTemplateLibraryFieldType = iSysTemplateLibraryFieldTypeService.getById(sysTemplateLibraryField.getTemplateLibraryFieldTypeId());
                        commTableField.setDataType(sysTemplateLibraryFieldType.getDataKey());
                        commTableField.setLength(sysTemplateLibraryField.getLength());
                        commTableField.setIsNull(sysTemplateLibraryField.getRequired() == 1 ? (short) 0 : (short) 1);
                        commTableField.setIsIndex(sysTemplateLibraryField.getIndex().shortValue());
                        commTableField.setComment(sysTemplateLibraryField.getName());
                        commTableFields.add(commTableField);
                    }
                    String[] dataKeys = sysArchivesLibraryFields.stream().map(SysArchivesLibraryField::getDataKey).toArray(String[]::new);
                    List<Integer> idList = iSysArchivesLibraryFieldService.listByArchivesLibraryIdDataKeys(id, dataKeys);
                    if (!idList.isEmpty()) {
                        //回滚之前的所有操作
                        this.updateById(rollBack);
                        iCommTableFieldService.editTableCommentByTableName(tableName2, rollBack.getName());
                        iCommTableFieldService.editTableNameByTableName(tableName2, rollBack.getDataKey());
                        throw new BaseRuntimeException("key不能重复");
                    }
                    iSysArchivesLibraryFieldService.saveBatch(sysArchivesLibraryFields);
                    //DDL操作
                    iCommTableFieldService.removeTableByTableName(tableName1);
                    iCommTableFieldService.addTable(TableNameUtil.getFullTableName(dataKey), name, commTableFields);
                }
            }
        }
    }


    @Override
    public List<Integer> listByNumber(Integer number) {
        QueryWrapper<SysArchivesLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysArchivesLibrary::getNumber, number);
        return this.listObjs(queryWrapper, id -> (Integer) id);
    }

    @Override
    public List<Integer> listByIdAndType(Integer id, Short type) {
        if (id == null)
            throw new BaseRuntimeException("档案库id不能为空");
        QueryWrapper<SysArchivesLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysArchivesLibrary::getParentId, id);
        return this.listObjs(queryWrapper, integer -> (Integer) integer);
    }

    @Override
    public List<Integer> listByDataKey(String dataKey) {
        if (StrUtil.isEmpty(dataKey))
            throw new BaseRuntimeException("key不能为空");
        QueryWrapper<SysArchivesLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(SysArchivesLibrary.class, tableFieldInfo -> tableFieldInfo.getColumn().equals("id"))
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
