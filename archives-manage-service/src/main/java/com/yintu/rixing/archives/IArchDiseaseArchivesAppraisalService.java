package com.yintu.rixing.archives;

import com.yintu.rixing.dto.archives.ArchCommonQueryDto;
import com.yintu.rixing.vo.archives.ArchArchivesBorrowStatisticsDataVo;
import com.yintu.rixing.vo.archives.ArchCommonVo;
import com.yintu.rixing.vo.archives.ArchDiseaseArchivesAppraisalDataVo;
import com.yintu.rixing.vo.archives.ArchUsingPurposeStatisticsDataVo;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/2/22 10:34:54
 * @Version: 1.0
 */
public interface IArchDiseaseArchivesAppraisalService {

    /**
     * 查询条件中每个档案库的名称
     *
     * @return 名称集
     */
    List<ArchCommonVo> findArchivesName();

    /**
     * 查询每个库状态的每个档案库的统计个数
     *
     * @param archCommonQueryDto 入参
     * @return 统计值集
     */
    ArchDiseaseArchivesAppraisalDataVo findArchDiseaseArchivesAppraisalData(ArchCommonQueryDto archCommonQueryDto);
}
