package com.yintu.rixing.data.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.data.DataArchivesLibraryFileMapper;
import com.yintu.rixing.data.DataArchivesLibraryFile;
import com.yintu.rixing.data.DataCommon;
import com.yintu.rixing.data.IDataArchivesLibraryFileService;
import com.yintu.rixing.dto.data.DataArchivesLibraryFileFormDto;
import com.yintu.rixing.dto.data.DataArchivesLibraryFileQueryDto;
import com.yintu.rixing.enumobject.EnumFlag;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.system.ISysArchivesLibraryService;
import com.yintu.rixing.system.SysArchivesLibrary;
import com.yintu.rixing.system.SysUser;
import com.yintu.rixing.util.AssertUtil;
import com.yintu.rixing.util.FilePageUtil;
import com.yintu.rixing.util.FileParseUtil;
import com.yintu.rixing.util.TableNameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.object.UpdatableSqlQuery;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

/**
 * <p>
 * 数据档案库文件表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2021-01-19
 */
@Service
public class DataArchivesLibraryFileServiceImpl extends ServiceImpl<DataArchivesLibraryFileMapper, DataArchivesLibraryFile> implements IDataArchivesLibraryFileService {

    @Autowired
    private ISysArchivesLibraryService iSysArchivesLibraryService;
    @Autowired
    private DataCommonService dataCommonService;

    @Override
    public void save(DataArchivesLibraryFileFormDto dataArchivesLibraryFileFormDto) {
        this.parametersToProofread(dataArchivesLibraryFileFormDto);//参数校对
        List<String> originalNames = dataArchivesLibraryFileFormDto.getOriginalNames();
        List<DataArchivesLibraryFile> dataArchivesLibraryFiles = new ArrayList<>();
        for (int i = 0; i < originalNames.size(); i++) {
            DataArchivesLibraryFile dataArchivesLibraryFile = new DataArchivesLibraryFile();
            BeanUtil.copyProperties(dataArchivesLibraryFileFormDto, dataArchivesLibraryFile);
            String path = dataArchivesLibraryFileFormDto.getPaths().get(i);
            String name = dataArchivesLibraryFileFormDto.getNames().get(i);
            String requestMapping = dataArchivesLibraryFileFormDto.getRequestMappings().get(i);
            dataArchivesLibraryFile.setOriginalName(originalNames.get(i));
            dataArchivesLibraryFile.setPath(dataArchivesLibraryFileFormDto.getPaths().get(i));
            dataArchivesLibraryFile.setSize(dataArchivesLibraryFileFormDto.getSizes().get(i));
            dataArchivesLibraryFile.setUnit(dataArchivesLibraryFileFormDto.getUnits().get(i));
            dataArchivesLibraryFile.setName(dataArchivesLibraryFileFormDto.getNames().get(i));
            dataArchivesLibraryFile.setRequestMapping(requestMapping);
            dataArchivesLibraryFile.setContext(FileParseUtil.parse(path + File.separator + name));
            //dataArchivesLibraryFile.setPage(FilePageUtil.getPage(requestMapping));
            dataArchivesLibraryFile.setPage(0);
            dataArchivesLibraryFile.setFormalLibrary(EnumFlag.False.getValue());
            dataArchivesLibraryFiles.add(dataArchivesLibraryFile);
        }
        this.saveBatch(dataArchivesLibraryFiles);
    }

    @Override
    public void removeByIds(Set<Integer> ids) {
        List<DataArchivesLibraryFile> dataArchivesLibraryFiles = this.listByIds(ids);
        for (DataArchivesLibraryFile dataArchivesLibraryFile : dataArchivesLibraryFiles) {
            String path = dataArchivesLibraryFile.getPath();
            String name = dataArchivesLibraryFile.getName();
            String newPath = path + "\\" + name;
            if (path.contains("/")) {
                newPath = path + "/" + name;
            }
            FileUtil.del(newPath);
        }
        super.removeByIds(ids);
    }

    public void parametersToProofread(DataArchivesLibraryFileFormDto dataArchivesLibraryFileFormDto) {
        List<String> originalNames = dataArchivesLibraryFileFormDto.getOriginalNames();
        List<String> paths = dataArchivesLibraryFileFormDto.getPaths();
        List<Double> sizes = dataArchivesLibraryFileFormDto.getSizes();
        List<String> units = dataArchivesLibraryFileFormDto.getUnits();
        List<String> names = dataArchivesLibraryFileFormDto.getNames();
        List<String> requestMappings = dataArchivesLibraryFileFormDto.getRequestMappings();
        if (originalNames.size() != paths.size() || paths.size() != sizes.size() || sizes.size() != units.size()
                || units.size() != names.size() || names.size() != requestMappings.size())
            throw new BaseRuntimeException("文件信息长度不一致");
        Integer archivesLibraryId = dataArchivesLibraryFileFormDto.getArchivesLibraryId();
        Integer dataId = dataArchivesLibraryFileFormDto.getDataId();
        SysArchivesLibrary sysArchivesLibrary = iSysArchivesLibraryService.getById(archivesLibraryId);
        AssertUtil.notNull(sysArchivesLibrary, "档案库不存在");
        String tableName = TableNameUtil.getFullTableName(sysArchivesLibrary.getDataKey());
        DataCommon dataCommonAll = new DataCommon();
        dataCommonAll.setTableName(tableName);
        dataCommonAll.setId(dataId);
        Map<String, Object> map = dataCommonService.getById(dataCommonAll);
        AssertUtil.notNull(map, "档案库数据不存在");
    }

    @Override
    public void updateById(DataArchivesLibraryFileFormDto dataArchivesLibraryFileFormDto) {
        this.parametersToProofread(dataArchivesLibraryFileFormDto);//参数校对
        Integer id = dataArchivesLibraryFileFormDto.getId();
        DataArchivesLibraryFile dataArchivesLibraryFile = this.getById(id);
        AssertUtil.notNull(dataArchivesLibraryFile, "档案库文件不存在");
        BeanUtil.copyProperties(dataArchivesLibraryFileFormDto, dataArchivesLibraryFile);
        this.updateById(dataArchivesLibraryFile);
    }


    @Override
    public void moveById(Integer id1, Integer id2) {
        DataArchivesLibraryFile dataArchivesLibraryFile1 = this.getById(id1);
        DataArchivesLibraryFile dataArchivesLibraryFile2 = this.getById(id2);
        if (dataArchivesLibraryFile1 != null && dataArchivesLibraryFile2 != null) {
            Integer order1 = dataArchivesLibraryFile1.getOrder();
            Integer order2 = dataArchivesLibraryFile2.getOrder();
            if (!order1.equals(order2)) { //如果顺序不相同需要修改
                dataArchivesLibraryFile1.setOrder(order2);
                dataArchivesLibraryFile2.setOrder(order1);
                this.saveOrUpdate(dataArchivesLibraryFile1);
                this.saveOrUpdate(dataArchivesLibraryFile2);
            }
        }
    }

    @Override
    public void resetByIds(Set<Integer> ids, String by) {
        if (ids.size() > 1) {
            QueryWrapper<DataArchivesLibraryFile> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().in(DataArchivesLibraryFile::getId, ids);
            if ("top".equals(by))
                queryWrapper.lambda().orderByAsc(DataArchivesLibraryFile::getOrder);
            else queryWrapper.lambda().orderByDesc(DataArchivesLibraryFile::getOrder);
            List<DataArchivesLibraryFile> oldDataArchivesLibraryFiles = this.list(queryWrapper);
            if (oldDataArchivesLibraryFiles.size() > 1) {
                int size = oldDataArchivesLibraryFiles.size();
                Integer order = oldDataArchivesLibraryFiles.get(0).getOrder();
                List<DataArchivesLibraryFile> newDataArchivesLibraryFiles = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    DataArchivesLibraryFile dataArchivesLibraryFile = oldDataArchivesLibraryFiles.get(i);
                    if (i == size - 1) {
                        dataArchivesLibraryFile.setOrder(order);
                    } else {
                        dataArchivesLibraryFile.setOrder(oldDataArchivesLibraryFiles.get(i + 1).getOrder());
                    }
                    newDataArchivesLibraryFiles.add(dataArchivesLibraryFile);
                }
                this.updateBatchById(newDataArchivesLibraryFiles);
            }
        }
    }

    @Override
    public void updateRemark(Integer id, String remark) {
        DataArchivesLibraryFile dataArchivesLibraryFile = this.getById(id);
        if (dataArchivesLibraryFile != null) {
            dataArchivesLibraryFile.setRemark(remark);
            this.saveOrUpdate(dataArchivesLibraryFile);
        }
    }

    @Override
    public void updateFormalLibrary(Short formalLibrary, Integer archivesLibraryId, Integer dataId) {
        List<Integer> ids = this.listByArchivesLibraryIdAndDataId(archivesLibraryId, dataId);
        if (!ids.isEmpty()) {
            UpdateWrapper<DataArchivesLibraryFile> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda().set(DataArchivesLibraryFile::getFormalLibrary, formalLibrary)
                    .eq(DataArchivesLibraryFile::getArchivesLibraryId, archivesLibraryId)
                    .eq(DataArchivesLibraryFile::getDataId, dataId);
            this.update(updateWrapper);
        }
    }

    @Override
    public List<Integer> listByArchivesLibraryIdAndDataId(Integer archivesLibraryId, Integer dataId) {
        AssertUtil.notNull(archivesLibraryId, "档案库id不能为空");
        AssertUtil.notNull(dataId, "档案目录不能为空");
        QueryWrapper<DataArchivesLibraryFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(DataArchivesLibraryFile::getId)
                .eq(DataArchivesLibraryFile::getArchivesLibraryId, archivesLibraryId)
                .eq(DataArchivesLibraryFile::getDataId, dataId);
        return this.listObjs(queryWrapper, id -> (Integer) id);
    }

    @Override
    public Page<DataArchivesLibraryFile> page(DataArchivesLibraryFileQueryDto dataArchivesLibraryFileQueryDto) {
        Integer num = dataArchivesLibraryFileQueryDto.getNum();
        Integer size = dataArchivesLibraryFileQueryDto.getSize();
        Integer archivesLibraryId = dataArchivesLibraryFileQueryDto.getArchivesLibraryId();
        Integer dataId = dataArchivesLibraryFileQueryDto.getDataId();
        QueryWrapper<DataArchivesLibraryFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(DataArchivesLibraryFile::getArchivesLibraryId, archivesLibraryId).eq(DataArchivesLibraryFile::getDataId, dataId);
        queryWrapper.lambda().orderByAsc(DataArchivesLibraryFile::getOrder);
        return this.page(new Page<>(num, size), queryWrapper);
    }

}
