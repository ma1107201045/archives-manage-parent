package com.yintu.rixing.make;


import com.yintu.rixing.vo.data.DataCommonVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.dto.make.MakeArchivesSearchElectronicDto;
import com.yintu.rixing.vo.make.MakeArchivesSearchElectronicVo;

/**
 * @Author: mlf
 * @Date: 2021/2/2 16:58:57
 * @Version: 1.0
 */
public interface IMakeArchivesSearchService {




    DataCommonVo searchEntityArchives(Integer num, Integer size, String searchThings);

    /**
     * 档案检索电子档
     *
     * @param makeArchivesSearchElectronicDto ..
     * @return ..
     */
    Page<MakeArchivesSearchElectronicVo> listElectronicByKeyWord(MakeArchivesSearchElectronicDto makeArchivesSearchElectronicDto);
}
