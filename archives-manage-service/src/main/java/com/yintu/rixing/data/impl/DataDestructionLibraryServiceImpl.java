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
            int status = (int) map.get(EnumArchivesLibraryDefaultField.STATUS.getDataKey());
            if(status != 3){
                continue;
            }
            List<DataCommonKV> dataCommonKVS = new ArrayList<>();
            String dataKey = EnumArchivesLibraryDefaultField.RETENTION_PERIOD.getDataKey();
            if (map.containsKey(dataKey)) {
                Object o = map.get(dataKey);
                if (o != null) {
                    Date createTime = (Date) map.get(EnumArchivesLibraryDefaultField.CREATE_TIME.getDataKey());
                    if(createTime!=null){
                        String v = o.toString();
                        Date validPeriod = null;
                        if("5年".equalsIgnoreCase(v)){
                            validPeriod = new Date(createTime.getTime() + (60L*60L*24L*365L*5L*1000L));
                        }else if("10年".equalsIgnoreCase(v)){
                            validPeriod = new Date(createTime.getTime() + (60L*60L*24L*365L*10L*1000L));
                        }else if("15年".equalsIgnoreCase(v)){
                            validPeriod = new Date(createTime.getTime() + (60L*60L*24L*365L*15L*1000L));
                        }else if("20年".equalsIgnoreCase(v)){
                            validPeriod = new Date(createTime.getTime() + (60L*60L*24L*365L*20L*1000L));
                        }else if("25年".equalsIgnoreCase(v)){
                            validPeriod = new Date(createTime.getTime() + (60L*60L*24L*365L*25L*1000L));
                        }else if("30年".equalsIgnoreCase(v)){
                            validPeriod = new Date(createTime.getTime() + (60L*60L*24L*365L*30L*1000L));
                        }
                        if(validPeriod!=null){
                            if (DateUtil.parseDate(DateUtil.today()).isAfterOrEquals(validPeriod)) {
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

    /**
     * 普通搜索
     * @param dataCommonPageDto
     * @return
     */
    @Override
    public DataCommonVo getPageEasy(DataCommonQueryDto dataCommonPageDto) {
        DataCommon dataCommon = this.page(dataCommonPageDto);
        Integer archivesLibraryId = dataCommonPageDto.getArchivesLibraryId();
//        DataCommon queryDataCommon = this.page(dataCommonPageDto);
        //销毁库查询时候 去检测正式库中超过期限的数据
        DataCommon changeDataCommon = new DataCommon();
        changeDataCommon.setTableName(dataCommon.getTableName());
        changeDataCommon.setDataCommonKVs(new ArrayList<>());
        changeDataCommon.getDataCommonKVs().add(this.getStatusField(EnumArchivesOrder.FORMAL_LIBRARY.getValue()));
        List<Map<String, Object>> list = iDataFormalLibraryService.getList(dataCommon);
        //判断有效期是否过期
        for (Map<String, Object> map : list) {
            int status = (int) map.get(EnumArchivesLibraryDefaultField.STATUS.getDataKey());
            if(status != 3){
                continue;
            }
            List<DataCommonKV> dataCommonKVS = new ArrayList<>();
            String dataKey = EnumArchivesLibraryDefaultField.RETENTION_PERIOD.getDataKey();
            if (map.containsKey(dataKey)) {
                Object o = map.get(dataKey);
                if (o != null) {
                    Date createTime = (Date) map.get(EnumArchivesLibraryDefaultField.CREATE_TIME.getDataKey());
                    if(createTime!=null){
                        String v = o.toString();
                        Date validPeriod = null;
                        if("5年".equalsIgnoreCase(v)){
                            validPeriod = new Date(createTime.getTime() + (60L*60L*24L*365L*5L*1000L));
                        }else if("10年".equalsIgnoreCase(v)){
                            validPeriod = new Date(createTime.getTime() + (60L*60L*24L*365L*10L*1000L));
                        }else if("15年".equalsIgnoreCase(v)){
                            validPeriod = new Date(createTime.getTime() + (60L*60L*24L*365L*15L*1000L));
                        }else if("20年".equalsIgnoreCase(v)){
                            validPeriod = new Date(createTime.getTime() + (60L*60L*24L*365L*20L*1000L));
                        }else if("25年".equalsIgnoreCase(v)){
                            validPeriod = new Date(createTime.getTime() + (60L*60L*24L*365L*25L*1000L));
                        }else if("30年".equalsIgnoreCase(v)){
                            validPeriod = new Date(createTime.getTime() + (60L*60L*24L*365L*30L*1000L));
                        }
                        if(validPeriod!=null){
                            if (DateUtil.parseDate(DateUtil.today()).isAfterOrEquals(validPeriod)) {
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
            }
        }
        DataCommonKV statusField = this.getStatusField(EnumArchivesOrder.DESTRUCTION_LIBRARY.getValue());
        Integer num = dataCommonPageDto.getNum();
        Integer size = dataCommonPageDto.getSize();
        DataCommonVo dataCommonVo = new DataCommonVo();
        List<DataCommonFieldVo> dataCommonFieldVos = this.getDataCommonFieldVos(archivesLibraryId);
        if(dataCommonFieldVos.size()>0){
            for (DataCommonFieldVo dataCommonFieldVo : dataCommonFieldVos) {
                if(dataCommonFieldVo.getQuery()){
                    DataCommonKV dataCommonKV = new DataCommonKV();
                    dataCommonKV.setFieldName(dataCommonFieldVo.getProp());
                    String keyword = dataCommonPageDto.getParams().get("keyword");
                    if(keyword == null){
                        keyword = "";
                    }
                    dataCommonKV.setFieldValue(keyword.trim());
                    dataCommon.getDataCommonKVs().add(dataCommonKV);
                }
            }
        }
        Page<Map<String, Object>> page = this.destructionLibraryMapper.selectPageEasy(new Page<>(num, size),statusField, dataCommon);
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


    /**
     * 高级搜索
     * @param dataCommonPageDto
     * @return
     */
    @Override
    public DataCommonVo getPageComplex(DataCommonQueryDto dataCommonPageDto) {
        DataCommon dataCommon = this.pageComplex(dataCommonPageDto);
        Integer archivesLibraryId = dataCommonPageDto.getArchivesLibraryId();
//        DataCommon queryDataCommon = this.page(dataCommonPageDto);
        //销毁库查询时候 去检测正式库中超过期限的数据
        DataCommon changeDataCommon = new DataCommon();
        changeDataCommon.setTableName(dataCommon.getTableName());
        changeDataCommon.setDataCommonKVs(new ArrayList<>());
        changeDataCommon.getDataCommonKVs().add(this.getStatusField(EnumArchivesOrder.FORMAL_LIBRARY.getValue()));
        List<Map<String, Object>> list = iDataFormalLibraryService.getList(dataCommon);
        //判断有效期是否过期
        for (Map<String, Object> map : list) {
            int status = (int) map.get(EnumArchivesLibraryDefaultField.STATUS.getDataKey());
            if(status != 3){
                continue;
            }
            List<DataCommonKV> dataCommonKVS = new ArrayList<>();
            String dataKey = EnumArchivesLibraryDefaultField.RETENTION_PERIOD.getDataKey();
            if (map.containsKey(dataKey)) {
                Object o = map.get(dataKey);
                if (o != null) {
                    Date createTime = (Date) map.get(EnumArchivesLibraryDefaultField.CREATE_TIME.getDataKey());
                    if(createTime!=null){
                        String v = o.toString();
                        Date validPeriod = null;
                        if("5年".equalsIgnoreCase(v)){
                            validPeriod = new Date(createTime.getTime() + (60L*60L*24L*365L*5L*1000L));
                        }else if("10年".equalsIgnoreCase(v)){
                            validPeriod = new Date(createTime.getTime() + (60L*60L*24L*365L*10L*1000L));
                        }else if("15年".equalsIgnoreCase(v)){
                            validPeriod = new Date(createTime.getTime() + (60L*60L*24L*365L*15L*1000L));
                        }else if("20年".equalsIgnoreCase(v)){
                            validPeriod = new Date(createTime.getTime() + (60L*60L*24L*365L*20L*1000L));
                        }else if("25年".equalsIgnoreCase(v)){
                            validPeriod = new Date(createTime.getTime() + (60L*60L*24L*365L*25L*1000L));
                        }else if("30年".equalsIgnoreCase(v)){
                            validPeriod = new Date(createTime.getTime() + (60L*60L*24L*365L*30L*1000L));
                        }
                        if(validPeriod!=null){
                            if (DateUtil.parseDate(DateUtil.today()).isAfterOrEquals(validPeriod)) {
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
            }
        }
        dataCommon.getDataCommonKVs().add(this.getStatusField(EnumArchivesOrder.DESTRUCTION_LIBRARY.getValue()));
        Integer num = dataCommonPageDto.getNum();
        Integer size = dataCommonPageDto.getSize();

        DataCommonVo dataCommonVo = new DataCommonVo();
        List<DataCommonFieldVo> dataCommonFieldVos = this.getDataCommonFieldVos(archivesLibraryId);
        Page<Map<String, Object>> page = this.destructionLibraryMapper.selectPageComplex(new Page<>(num, size), dataCommon);
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
