package com.yintu.rixing.make;


import com.yintu.rixing.pojo.MakeArchivesSearchPojo;
import com.yintu.rixing.vo.data.DataCommonVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.dto.make.MakeArchivesSearchDto;
import com.yintu.rixing.vo.make.MakeArchivesSearchElectronicVo;
import com.yintu.rixing.vo.make.MakeArchivesSearchVo;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/2/2 16:58:57
 * @Version: 1.0
 */
public interface IMakeArchivesSearchService {


    DataCommonVo searchEntityArchives(Integer num, Integer size, String searchThings, Short searchType, Short userType, Integer userId);

    /**
     * 档案检索电子档
     *
     * @param makeArchivesSearchDto ..
     * @return ..
     */
    Page<MakeArchivesSearchElectronicVo> listElectronicByKeyWord(MakeArchivesSearchDto makeArchivesSearchDto);

    DataCommonVo findElectronicsDatasBySomethings(Integer num, Integer size, Integer archiveId, String searchThings);

    Page<MakeArchivesSearchVo> listArchivesByKeyWord(MakeArchivesSearchDto makeArchivesSearchDto);

    Page<MakeArchivesSearchPojo> page(Integer num, Integer size, String searchThings, Integer archivesDirectoryId, Integer archivesLibId);

    Page<MakeArchivesSearchVo> findDatasBySomethings(Integer num, Integer size, Integer archiveId, String searchThings);

    Page<MakeArchivesSearchPojo> searchArchivesFileByIds(Integer num, Integer size, Integer archivesDirectoryId, Integer archivesLibId);

}
