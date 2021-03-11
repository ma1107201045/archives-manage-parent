package com.yintu.rixing.system.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.common.CommTableField;
import com.yintu.rixing.common.ICommTableFieldService;
import com.yintu.rixing.dto.system.SysArchivesLibraryFormDto;
import com.yintu.rixing.enumobject.EnumFieldType;
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
    private ISysBaseFieldLibraryService iSysBaseFieldLibraryService;
    @Autowired
    private ISysArchivesLibraryFieldService iSysArchivesLibraryFieldService;
    @Autowired
    private ICommTableFieldService iCommTableFieldService;

    @Override
    public void save(SysArchivesLibraryFormDto sysArchivesLibraryFormDto) {
        String name = sysArchivesLibraryFormDto.getName();
        Short type = sysArchivesLibraryFormDto.getType();
        Short archType = sysArchivesLibraryFormDto.getArchType();
        String dataKey = sysArchivesLibraryFormDto.getDataKey();
        SysArchivesLibrary sysArchivesLibrary = new SysArchivesLibrary();
        if (type == 2) {
            if (StrUtil.isEmpty(dataKey)) {
                throw new BaseRuntimeException("key（英文名称）不能为空");
            }
            List<Integer> ids = this.listByDataKey(dataKey);
            if (!ids.isEmpty()) {
                throw new BaseRuntimeException("key（英文名称）重复");
            }
        }
        BeanUtil.copyProperties(sysArchivesLibraryFormDto, sysArchivesLibrary);
        this.save(sysArchivesLibrary);
        if (type == 2) {
            Integer archivesLibraryId = sysArchivesLibrary.getId();
            //案卷级
            if (archType == 1) {


            } else {
                //一文一件
                List<SysArchivesLibraryField> sysArchivesLibraryFields = new ArrayList<>();
                List<CommTableField> commTableFields = new ArrayList<>();
                //新建的档案库需要同步基础字段库
                List<SysBaseFieldLibrary> sysBaseFieldLibraries = iSysBaseFieldLibraryService.list();
                for (SysBaseFieldLibrary sysBaseFieldLibrary : sysBaseFieldLibraries) {
                    SysArchivesLibraryField sysArchivesLibraryField = new SysArchivesLibraryField();
                    BeanUtil.copyProperties(sysBaseFieldLibrary, sysArchivesLibraryField, "id");
                    sysArchivesLibraryField.setArchivesLibraryId(archivesLibraryId);
                    sysArchivesLibraryField.setFromType(EnumFieldType.BASE.getValue());
                    sysArchivesLibraryFields.add(sysArchivesLibraryField);
                    CommTableField commTableField = iCommTableFieldService.findByDataKeyAndSysArchivesLibraryField(sysArchivesLibraryField.getDataKey(), sysArchivesLibraryField);
                    commTableFields.add(commTableField);
                }
                iSysArchivesLibraryFieldService.saveBatch(sysArchivesLibraryFields);
                iCommTableFieldService.addTable(TableNameUtil.getFullTableName(dataKey), name, commTableFields);
            }
        }
    }


    @Override
    public void removeTree(Integer id) {
        SysArchivesLibrary sysArchivesLibrary = this.getById(id);
        if (sysArchivesLibrary != null) {
            this.removeById(id);
            QueryWrapper<SysArchivesLibrary> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(SysArchivesLibrary::getParentId, id);
            List<Integer> ids = this.listObjs(queryWrapper, obj -> (Integer) obj);
            for (Integer integer : ids) {
                this.removeTree(integer);
            }
            Short type = sysArchivesLibrary.getType();
            Short archType = sysArchivesLibrary.getArchType();
            if (type == 2) {
                //案卷级
                if (archType == 1) {

                } else {
                    //一文一件
                    String dataKey = sysArchivesLibrary.getDataKey();
                    String tableName = TableNameUtil.getFullTableName(dataKey);
                    iCommTableFieldService.isHasDataByTableName(tableName);
                    iCommTableFieldService.removeTableByTableName(tableName);
                }
            }
        }
    }

    @Override
    public void updateById(SysArchivesLibraryFormDto sysArchivesLibraryFormDto) {
        Integer id = sysArchivesLibraryFormDto.getId();
        String name = sysArchivesLibraryFormDto.getName();
        Short type = sysArchivesLibraryFormDto.getType();
        Short archType = sysArchivesLibraryFormDto.getArchType();
        String dataKey = sysArchivesLibraryFormDto.getDataKey();
        SysArchivesLibrary sysArchivesLibrary = this.getById(id);
        if (sysArchivesLibrary != null) {
            Short oldType = sysArchivesLibrary.getType();
            Short oldArchType = sysArchivesLibrary.getArchType();
            String oldName = sysArchivesLibrary.getName();
            String oldDataKey = sysArchivesLibrary.getDataKey();
            if (!type.equals(oldType)) {
                //判断修改的跟当前的类型对比
                if (type == 1 && oldType == 2) {
                    sysArchivesLibrary.setDataKey("");
                    //先删除之前档案库的字段
                    iSysArchivesLibraryFieldService.removeByArchivesLibraryId(id);
                    String oldTableName = TableNameUtil.getFullTableName(oldDataKey);
                    //判断表中是否有数据
                    iCommTableFieldService.isHasDataByTableName(oldTableName);
                    //删除表
                    iCommTableFieldService.removeTableByTableName(oldTableName);
                    //
                } else {
                    if (StrUtil.isEmpty(dataKey)) {
                        throw new BaseRuntimeException("key（英文名称）不能为空");
                    }
                    List<Integer> ids = this.listByDataKey(dataKey);
                    if (!ids.isEmpty()) {
                        throw new BaseRuntimeException("key（英文名称）重复");
                    }
                    if (archType == 1) {


                    } else {
                        //一文一件
                        List<SysArchivesLibraryField> sysArchivesLibraryFields = new ArrayList<>();
                        List<CommTableField> commTableFields = new ArrayList<>();
                        //新建的档案库需要同步基础字段库
                        List<SysBaseFieldLibrary> sysBaseFieldLibraries = iSysBaseFieldLibraryService.list();
                        for (SysBaseFieldLibrary sysBaseFieldLibrary : sysBaseFieldLibraries) {
                            SysArchivesLibraryField sysArchivesLibraryField = new SysArchivesLibraryField();
                            BeanUtil.copyProperties(sysBaseFieldLibrary, sysArchivesLibraryField, "id");
                            sysArchivesLibraryField.setArchivesLibraryId(id);
                            sysArchivesLibraryField.setFromType(EnumFieldType.BASE.getValue());
                            sysArchivesLibraryFields.add(sysArchivesLibraryField);
                            CommTableField commTableField = iCommTableFieldService.findByDataKeyAndSysArchivesLibraryField(sysArchivesLibraryField.getDataKey(), sysArchivesLibraryField);
                            commTableFields.add(commTableField);
                        }
                        iSysArchivesLibraryFieldService.saveBatch(sysArchivesLibraryFields);
                        iCommTableFieldService.addTable(TableNameUtil.getFullTableName(dataKey), name, commTableFields);
                    }
                }
            }
            BeanUtil.copyProperties(sysArchivesLibraryFormDto, sysArchivesLibrary);
            this.updateById(sysArchivesLibrary);
            if (type == 2 && oldType == 2) {
                if (!oldArchType.equals(archType)) {
                    if (oldArchType == 1 && archType == 2) {

                    } else {

                    }
                } else {
                    //案卷级
                    if (archType == 1) {


                    } else {
                        //一文一件
                        String oldTableName = TableNameUtil.getFullTableName(oldDataKey);
                        //更改表的注释
                        if (!oldName.equals(name)) {
                            iCommTableFieldService.editTableCommentByTableName(oldTableName, name);
                        }
                        //更改表的表名
                        if (!oldDataKey.equals(dataKey)) {
                            String tableName = TableNameUtil.getFullTableName(dataKey);
                            iCommTableFieldService.editTableNameByTableName(oldTableName, tableName);
                        }
                    }
                }
            }
        }
    }


    @Override
    public List<Integer> listByIdAndType(Integer id, Short type) {
        if (id == null) {
            throw new BaseRuntimeException("档案库id不能为空");
        }
        QueryWrapper<SysArchivesLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(SysArchivesLibrary::getId)
                .eq(SysArchivesLibrary::getParentId, id);
        return this.listObjs(queryWrapper, integer -> (Integer) integer);
    }

    @Override
    public List<Integer> listByDataKey(String dataKey) {
        if (StrUtil.isEmpty(dataKey)) {
            throw new BaseRuntimeException("key不能为空");
        }
        QueryWrapper<SysArchivesLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(SysArchivesLibrary::getId)
                .eq(SysArchivesLibrary::getDataKey, dataKey);
        return this.listObjs(queryWrapper, id -> (Integer) id);
    }

    @Override
    public List<TreeUtil> listTree(Integer id) {
        QueryWrapper<SysArchivesLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysArchivesLibrary::getParentId, id);
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

    @Override
    public List<SysArchivesLibrary> listByType(Short type) {
        QueryWrapper<SysArchivesLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysArchivesLibrary::getType, type);
        return this.list(queryWrapper);
    }

}
