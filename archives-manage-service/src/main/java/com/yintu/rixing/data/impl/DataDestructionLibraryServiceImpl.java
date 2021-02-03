package com.yintu.rixing.data.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.data.*;
import com.yintu.rixing.dto.data.DataCommonFormDto;
import com.yintu.rixing.dto.data.DataCommonQueryDto;
import com.yintu.rixing.enumobject.EnumArchivesLibraryDefaultField;
import com.yintu.rixing.enumobject.EnumArchivesOrder;
import com.yintu.rixing.enumobject.EnumFlag;
import com.yintu.rixing.system.SysDepartment;
import com.yintu.rixing.vo.data.DataCommonFieldVo;
import com.yintu.rixing.vo.data.DataCommonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2021/1/25 17:18:09
 * @Version: 1.0
 */
@Service
public class DataDestructionLibraryServiceImpl extends DataCommonService implements IDataDestructionLibraryService {
    @Autowired
    private DataDestructionLibraryMapper destructionLibraryMapper;

    @Autowired
    private IDataFormalLibraryService iDataFormalLibraryService;

    @Autowired
    private IDataArchivesLibraryFileService iDataArchivesLibraryFileService;


    @Override
    public void updateById(DataCommonFormDto dataCommonFormDto) {
        DataCommon dataCommon = this.saveOrUpdateHandler(dataCommonFormDto);
        Map<String, Object> map = destructionLibraryMapper.selectByPrimaryKey(dataCommon);
        if (map != null) {
            destructionLibraryMapper.updateByPrimaryKeySelective(dataCommon);
        }
    }


    @Override
    public void updateStatusById(Integer id, Integer archivesLibraryId, Short status) {
        DataCommon dataCommon = this.removeOrGetHandler(id, archivesLibraryId);
        Map<String, Object> map = destructionLibraryMapper.selectByPrimaryKey(dataCommon);
        if (map != null) {
            List<DataCommonKV> dataCommonKVS = new ArrayList<>();
            DataCommonKV dataCommonKV = new DataCommonKV();
            dataCommonKV.setFieldName(EnumArchivesLibraryDefaultField.STATUS.getDataKey());
            dataCommonKV.setFieldValue(status);
            dataCommonKVS.add(dataCommonKV);
            dataCommon.setDataCommonKVs(dataCommonKVS);
            destructionLibraryMapper.updateByPrimaryKeySelective(dataCommon);
        }
    }

    @Override
    public Map<String, Object> getById(Integer id, Integer archivesLibraryId) {
        DataCommon dataCommon = this.removeOrGetHandler(id, archivesLibraryId);
        return destructionLibraryMapper.selectByPrimaryKey(dataCommon);
    }

    @Override
    public DataCommonVo getPage(DataCommonQueryDto dataCommonPageDto) {
        Integer archivesLibraryId = dataCommonPageDto.getArchivesLibraryId();
        DataCommon queryDataCommon = this.page(dataCommonPageDto);
        //销毁库查询时候 去检测正式库中超过期限的数据
        DataCommon changeDataCommon = new DataCommon();
        changeDataCommon.setTableName(queryDataCommon.getTableName());
        changeDataCommon.setDataCommonKVs(new ArrayList<>());
        changeDataCommon.getDataCommonKVs().add(this.getStatusField(EnumArchivesOrder.FORMAL_LIBRARY.getValue()));
        List<Map<String, Object>> list = iDataFormalLibraryService.getList(queryDataCommon);
        //判断有效期是否过期
        for (Map<String, Object> map : list) {
            List<DataCommonKV> dataCommonKVS = new ArrayList<>();
            String dataKey = EnumArchivesLibraryDefaultField.VALID_PERIOD.getDataKey();
            if (map.containsKey(dataKey)) {
                Object o = map.get(dataKey);
                if (o != null) {
                    if (DateUtil.parseDate(DateUtil.today()).isBeforeOrEquals((Date) o)) {
                        DataCommonKV dataCommonKV = new DataCommonKV();
                        dataCommonKV.setFieldName(EnumArchivesLibraryDefaultField.STATUS.getDataKey());
                        dataCommonKV.setFieldValue(EnumArchivesOrder.DESTRUCTION_LIBRARY.getValue());
                        dataCommonKVS.add(dataCommonKV);
                        Integer id = (Integer) map.get(EnumArchivesLibraryDefaultField.ID.getDataKey());
                        changeDataCommon.setId(id);
                        changeDataCommon.setDataCommonKVs(dataCommonKVS);
                        //更新正式库到销毁库
                        iDataFormalLibraryService.updateById(changeDataCommon);
                        //更新正式库到销毁库 改变档案文件状态
                        iDataArchivesLibraryFileService.updateFormalLibrary(EnumFlag.False.getValue(), archivesLibraryId, id);
                    }
                }
            }
        }


        queryDataCommon.getDataCommonKVs().add(this.getStatusField(EnumArchivesOrder.DESTRUCTION_LIBRARY.getValue()));
        Integer num = dataCommonPageDto.getNum();
        Integer size = dataCommonPageDto.getSize();
        DataCommonVo dataCommonVo = new DataCommonVo();
        List<DataCommonFieldVo> dataCommonFieldVos = this.getDataCommonFieldVos(archivesLibraryId);
        Page<Map<String, Object>> page = this.destructionLibraryMapper.selectPage(new Page<>(num, size), queryDataCommon);
        //特殊字段需要处理
        page.getRecords().forEach(map -> {
            String dataKey = EnumArchivesLibraryDefaultField.DEPARTMENT_ID.getDataKey();
            if (map.containsKey(dataKey)) {
                Integer departmentId = (Integer) map.get(dataKey);
                SysDepartment sysDepartment = iSysDepartmentService.getById(departmentId);
                map.put(dataKey, sysDepartment.getName());
            }
        });
        dataCommonVo.setFields(dataCommonFieldVos);
        dataCommonVo.setPage(page);
        return dataCommonVo;
    }
}
