package com.yintu.rixing.archives.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.yintu.rixing.archives.ArchAbstractService;
import com.yintu.rixing.archives.ArchArchivesBorrowStatisticsMapper;
import com.yintu.rixing.archives.IArchArchivesBorrowStatisticsService;
import com.yintu.rixing.dto.archives.ArchCommonQueryDto;
import com.yintu.rixing.dto.archives.ArchivesStatsQueryDto;
import com.yintu.rixing.enumobject.EnumArchivesLibraryDefaultField;
import com.yintu.rixing.enumobject.EnumArchivesOrder;
import com.yintu.rixing.enumobject.EnumAuditStatus;
import com.yintu.rixing.system.SysArchivesLibrary;
import com.yintu.rixing.util.TableNameUtil;
import com.yintu.rixing.vo.archives.ArchArchivesBorrowStatisticsDataVo;
import com.yintu.rixing.vo.archives.ArchArchivesQuantityStatisticsDataVo;
import com.yintu.rixing.vo.archives.ArchivesCommonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: mlf
 * @Date: 2021/2/19 17:11:43
 * @Version: 1.0
 */
@Service
public class ArchArchivesBorrowStatisticsServiceImpl extends ArchAbstractService implements IArchArchivesBorrowStatisticsService {

    @Autowired
    private ArchArchivesBorrowStatisticsMapper archArchivesBorrowStatisticsMapper;

    protected static final String SUFFIX = ".xlsx";

    @Override
    public ArchArchivesBorrowStatisticsDataVo findArchArchivesBorrowStatisticsData(ArchCommonQueryDto archCommonQueryDto) {
        Integer departmentId = archCommonQueryDto.getDepartmentId();
        Date startDate = archCommonQueryDto.getStartDate();
        Date endDate = archCommonQueryDto.getEndDate();
        List<Integer> archivesIds = archCommonQueryDto.getArchivesIds();
        List<SysArchivesLibrary> archivesLibraries = iSysArchivesLibraryService.listByIds(archivesIds);
        List<List<Long>> lists = new ArrayList<>();
        List<Long> list1 = new ArrayList<>();
        List<Long> list2 = new ArrayList<>();
        List<Long> list3 = new ArrayList<>();
        for (SysArchivesLibrary archivesLibrary : archivesLibraries) {
            Integer id = archivesLibrary.getId();
            String tableName = TableNameUtil.getFullTableName(archivesLibrary.getDataKey());
            Map<String, Long> map = archArchivesBorrowStatisticsMapper.selectArchivesBorrowStatisticsData(id, tableName, (short) 1, departmentId, startDate, endDate);
            list1.add(map == null ? 0L : map.get("1"));
            list2.add(map == null ? 0L : map.get("2"));
            list3.add(map == null ? 0L : map.get("3"));
        }
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);
        ArchArchivesBorrowStatisticsDataVo archArchivesBorrowStatisticsDataVo = new ArchArchivesBorrowStatisticsDataVo();
        archArchivesBorrowStatisticsDataVo.setNames(archivesLibraries.stream().map(SysArchivesLibrary::getName).collect(Collectors.toList()));
        archArchivesBorrowStatisticsDataVo.setValues(lists);
        return archArchivesBorrowStatisticsDataVo;
    }

    /**
     * 获取馆藏档案统计数据
     *
     * @param archivesStatsQueryDto
     * @return
     */
    @Override
    public List<ArchivesCommonVo> findArchivesInfo(ArchivesStatsQueryDto archivesStatsQueryDto) {


        Date startDate = archivesStatsQueryDto.getStartDate();
        Date endDate = archivesStatsQueryDto.getEndDate();
        List<Integer> archivesIds = archivesStatsQueryDto.getArchivesIds();
        List<SysArchivesLibrary> archivesLibraries = iSysArchivesLibraryService.listByIds(archivesIds);
        List<ArchivesCommonVo> result = new ArrayList<>();
        for (SysArchivesLibrary archivesLibrary : archivesLibraries) {
            Integer id = archivesLibrary.getId();
            String tableName = TableNameUtil.getFullTableName(archivesLibrary.getDataKey());
            Map<String, Long> map = archArchivesBorrowStatisticsMapper.selectArchivesBorrowStatisticsData(id, tableName, (short) 1, null, startDate, endDate);
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("audit", map == null ? 0L : map.get("1"));
            dataMap.put("pass", map == null ? 0L : map.get("2"));
            dataMap.put("reject", map == null ? 0L : map.get("3"));
            ArchivesCommonVo archivesCommonVo = new ArchivesCommonVo();
            archivesCommonVo.setId(id);
            archivesCommonVo.setDataMap(dataMap);
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
                map.put("审核中",archivesCommonVo.getDataMap().get("audit"));
                map.put("通过",archivesCommonVo.getDataMap().get("pass"));
                map.put("拒绝",archivesCommonVo.getDataMap().get("reject"));
                records.add(map);
            }
        }
        excelWriter.write(records, true);
        fullName = "档案借阅统计" + timestamp + SUFFIX;

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fullName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        ServletOutputStream out = response.getOutputStream();
        excelWriter.flush(out, true);
        excelWriter.close();
        IoUtil.close(out);
    }
}
