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
public class SysArchivesLibraryServiceImpl extends ServiceImpl<SysArchivesLibraryMapper, SysArchivesLibrary> implements ISysArchivesLibraryService {

    @Autowired
    private ISysTemplateLibraryFieldService iSysTemplateLibraryFieldService;
    @Autowired
    private ISysTemplateLibraryFieldTypeService iSysTemplateLibraryFieldTypeService;
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
            //动态创建表
            List<SysTemplateLibraryField> sysTemplateLibraryFields = iSysTemplateLibraryFieldService.listByTemplateLibraryId(templateLibraryId);
            List<CommTableField> commTableFields = new ArrayList<>();
            for (SysTemplateLibraryField sysTemplateLibraryField : sysTemplateLibraryFields) {
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
            iCommTableFieldService.addTable(TableNameUtil.getFullTableName(dataKey), name, commTableFields);
        }
        SysArchivesLibrary sysArchivesLibrary = new SysArchivesLibrary();
        if (parentId != -1) {
            sysArchivesLibrary = this.getById(sysArchivesLibraryFormDto.getParentId());
            if (sysArchivesLibrary != null) {
                if (sysArchivesLibrary.getType() == 2 && type == 1)
                    throw new BaseRuntimeException("档案库下边不能添加目录");
            } else return;
        }
        BeanUtil.copyProperties(sysArchivesLibraryFormDto, sysArchivesLibrary);
        this.save(sysArchivesLibrary);
    }

    @Override
    public void removeTree(Integer id) {
        this.removeById(id);
        SysArchivesLibrary sysArchivesLibrary = this.getById(id);
        if (sysArchivesLibrary != null) {
            String oldDataKey = sysArchivesLibrary.getDataKey();
            iCommTableFieldService.removeTableByTableName(TableNameUtil.getFullTableName(oldDataKey));
        }
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
            //只能更新表注释跟表名
            SysArchivesLibrary sysArchivesLibrary = this.getById(id);
            if (sysArchivesLibrary != null) {
                String oldName = sysArchivesLibrary.getName();
                String oldDataKey = sysArchivesLibrary.getDataKey();
                ;
                if (!oldName.equals(name))
                    iCommTableFieldService.editTableCommentByTableName(oldName, name);
                if (!oldDataKey.equals(dataKey)) {
                    iCommTableFieldService.editTableNameByTableName(TableNameUtil.getFullTableName(oldDataKey), TableNameUtil.getFullTableName(dataKey));
                }
            }
        }
        SysArchivesLibrary sysArchivesLibrary = this.getById(id);
        if (sysArchivesLibrary != null) {
            if (parentId != -1) {
                if (sysArchivesLibrary.getType() == 2 && type == 1) {
                    sysArchivesLibrary.setNumber(null);
                    sysArchivesLibrary.setDataKey("");
                    sysArchivesLibrary.setTemplateLibraryId(null);
                    //删除表
                    String oldDataKey = sysArchivesLibrary.getDataKey();
                    iCommTableFieldService.removeTableByTableName(TableNameUtil.getFullTableName(oldDataKey));
                }
                SysArchivesLibrary last = this.getById(parentId);
                if (last != null) {
                    if (last.getType() == 2 && type == 1)
                        throw new BaseRuntimeException("档案库下边不能修改目录");

                    List<Short> nextTypes = this.listByParentId(sysArchivesLibrary.getId());
                    for (Short nextType : nextTypes) {
                        if (nextType == 1 && type == 2)
                            throw new BaseRuntimeException("档案库下边不能有目录");
                    }

                } else return;
            }
            BeanUtil.copyProperties(sysArchivesLibraryFormDto, sysArchivesLibrary);
            this.updateById(sysArchivesLibrary);
        }
    }

    @Override
    public List<Integer> listByNumber(Integer number) {
        QueryWrapper<SysArchivesLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(SysArchivesLibrary.class, tableFieldInfo -> tableFieldInfo.getColumn().equals("id"))
                .eq(SysArchivesLibrary::getNumber, number);
        return this.listObjs(queryWrapper, id -> (Integer) id);
    }

    @Override
    public List<Short> listByParentId(Integer parentId) {
        if (parentId == null)
            throw new BaseRuntimeException("档案库id不能为空");
        QueryWrapper<SysArchivesLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(SysArchivesLibrary.class, tableFieldInfo -> tableFieldInfo.getColumn().equals("type"))
                .eq(SysArchivesLibrary::getParentId, parentId);
        return this.listObjs(queryWrapper, type -> (Short) type);
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
