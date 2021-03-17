package com.yintu.rixing.archives.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.yintu.rixing.archives.ArchAbstractService;
import com.yintu.rixing.archives.ArchArchivesQuantityStatisticsMapper;
import com.yintu.rixing.archives.ArchSearchArchivesQuantityStatisticsMapper;
import com.yintu.rixing.archives.IArchSearchArchivesQuantityStatisticsService;
import com.yintu.rixing.dto.archives.ArchCommonQueryDto;
import com.yintu.rixing.dto.archives.ArchivesStatsQueryDto;
import com.yintu.rixing.enumobject.EnumArchivesLibraryDefaultField;
import com.yintu.rixing.enumobject.EnumArchivesOrder;
import com.yintu.rixing.system.SysArchivesLibrary;
import com.yintu.rixing.util.TableNameUtil;
import com.yintu.rixing.vo.archives.ArchArchivesQuantityStatisticsDataVo;
import com.yintu.rixing.vo.archives.ArchSearchArchivesQuantityStatisticsDataVo;
import com.yintu.rixing.vo.archives.ArchivesCommonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: mlf
 * @Date: 2021/2/20 15:09:44
 * @Version: 1.0
 */
@Service
public class ArchSearchArchivesQuantityStatisticsServiceImpl extends ArchAbstractService implements IArchSearchArchivesQuantityStatisticsService {

    @Autowired
    private ArchSearchArchivesQuantityStatisticsMapper archSearchArchivesQuantityStatisticsMapper;

    protected static final String SUFFIX = ".xlsx";

    @Override
    public ArchSearchArchivesQuantityStatisticsDataVo findArchSearchArchivesQuantityStatisticsData(ArchCommonQueryDto archCommonQueryDto) {
        Integer departmentId = archCommonQueryDto.getDepartmentId();
        Date startDate = archCommonQueryDto.getStartDate();
        Date endDate = archCommonQueryDto.getEndDate();
        List<Integer> archivesIds = archCommonQueryDto.getArchivesIds();
        List<SysArchivesLibrary> archivesLibraries = iSysArchivesLibraryService.listByIds(archivesIds);
        List<Long> values = new ArrayList<>();
        for (SysArchivesLibrary archivesLibrary : archivesLibraries) {
            Integer id = archivesLibrary.getId();
            String tableName = TableNameUtil.getFullTableName(archivesLibrary.getDataKey());
            Long count = archSearchArchivesQuantityStatisticsMapper.selectSearchArchivesQuantityStatisticsData(id, tableName, (short) 1, departmentId, startDate, endDate);
            values.add(count);
        }
        ArchSearchArchivesQuantityStatisticsDataVo archArchivesQuantityStatisticsDataVo = new ArchSearchArchivesQuantityStatisticsDataVo();
        archArchivesQuantityStatisticsDataVo.setNames(archivesLibraries.stream().map(SysArchivesLibrary::getName).collect(Collectors.toList()));
        archArchivesQuantityStatisticsDataVo.setValues(values);
        return archArchivesQuantityStatisticsDataVo;
    }

    /**
     * 获取档案统计数据
     * @param archivesStatsQueryDto
     * @return
     */
    @Override
    public List<ArchivesCommonVo> findArchivesInfo(ArchivesStatsQueryDto archivesStatsQueryDto)
    {
        Date startDate = archivesStatsQueryDto.getStartDate();
        Date endDate = archivesStatsQueryDto.getEndDate();
        List<Integer> archivesIds = archivesStatsQueryDto.getArchivesIds();
        List<SysArchivesLibrary> archivesLibraries = iSysArchivesLibraryService.listByIds(archivesIds);
        List<ArchivesCommonVo> result = new ArrayList<>();
        for (SysArchivesLibrary archivesLibrary : archivesLibraries) {
            ArchivesCommonVo archivesCommonVo = new ArchivesCommonVo();
            Integer id = archivesLibrary.getId();
            String tableName = TableNameUtil.getFullTableName(archivesLibrary.getDataKey());
            Long count = archSearchArchivesQuantityStatisticsMapper.selectSearchArchivesQuantityStatisticsData(id, tableName, (short) 1, null, startDate, endDate);
            archivesCommonVo.setId(id);
            archivesCommonVo.setName(archivesLibrary.getName());
            archivesCommonVo.setNowValues(count);
            archivesCommonVo.setType(archivesLibrary.getType());
            archivesCommonVo.setArchivesType(archivesLibrary.getArchType());
//            archivesCommonVo.setCreateDate(archivesLibrary.getCreateTime());
            result.add(archivesCommonVo);
        }
        return result;
    }

    /**
     * 获取档案查询统计数据
     *
     * @param archivesStatsQueryDto
     * @return
     */
    @Override
    public List<ArchivesCommonVo> findArchivesInfos(ArchivesStatsQueryDto archivesStatsQueryDto) {


        Date startDate = archivesStatsQueryDto.getStartDate();
        Date endDate = archivesStatsQueryDto.getEndDate();
        List<Integer> archivesIds = archivesStatsQueryDto.getArchivesIds();
        List<SysArchivesLibrary> archivesLibraries = iSysArchivesLibraryService.listByIds(archivesIds);
        List<ArchivesCommonVo> result = new ArrayList<>();
        for (SysArchivesLibrary archivesLibrary : archivesLibraries) {
            Integer id = archivesLibrary.getId();
            String tableName = TableNameUtil.getFullTableName(archivesLibrary.getDataKey());
            Long count = archSearchArchivesQuantityStatisticsMapper.selectSearchArchivesQuantityStatisticsData(id, tableName, (short) 1, null, startDate, endDate);
            ArchivesCommonVo archivesCommonVo = new ArchivesCommonVo();
            archivesCommonVo.setId(id);
            archivesCommonVo.setNowValues(count == null ? 0L : count);
            archivesCommonVo.setName(archivesLibrary.getName());
            archivesCommonVo.setType(archivesLibrary.getType());
            archivesCommonVo.setArchivesType(archivesLibrary.getArchType());
            result.add(archivesCommonVo);
        }
        return result;
    }

    /**
     * 导出表格数据
     *
     * @param response
     * @param archivesStatsQueryDto
     */
    @Override
    public void exportExcel(HttpServletResponse response, ArchivesStatsQueryDto archivesStatsQueryDto) throws IOException {

        List<ArchivesCommonVo> archivesInfo = this.findArchivesInfo(archivesStatsQueryDto);
        long timestamp = DateUtil.date().getTime();
        ExcelWriter excelWriter = ExcelUtil.getWriter(true);
        String fullName;
        //导出数据
        excelWriter.setOnlyAlias(true);//仅仅显示表头的数据，则可以过滤掉无用的字段
        List<Map<String, Object>> records = new ArrayList<>();
        if(archivesInfo.size()>0){
            for (int i = 0; i < archivesInfo.size(); i++) {
                Map<String, Object> map = new LinkedHashMap<>();
                ArchivesCommonVo archivesCommonVo = archivesInfo.get(i);
                map.put("序号",i + 1);
                map.put("档案库名称",archivesCommonVo.getName());
                map.put("档案类型",archivesCommonVo.getArchivesType() == 1 ? "案卷级" : "一文一件");
                map.put("查档数量",archivesCommonVo.getNowValues());
                records.add(map);
            }
        }
        excelWriter.write(records, true);
        fullName = "档案查询统计" + timestamp + SUFFIX;

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fullName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        ServletOutputStream out = response.getOutputStream();
        excelWriter.flush(out, true);
        excelWriter.close();
        IoUtil.close(out);
    }

}
