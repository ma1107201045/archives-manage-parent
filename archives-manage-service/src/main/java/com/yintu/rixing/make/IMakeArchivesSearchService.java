package com.yintu.rixing.make;


import com.yintu.rixing.vo.data.DataCommonVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.dto.make.MakeArchivesSearchDto;
import com.yintu.rixing.vo.make.MakeArchivesSearchElectronicVo;

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
}
