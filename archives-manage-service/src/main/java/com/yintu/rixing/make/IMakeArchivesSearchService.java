package com.yintu.rixing.make;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yintu.rixing.dto.make.MakeArchivesSearchDto;
import com.yintu.rixing.vo.data.DataCommonVo;
import com.yintu.rixing.vo.make.MakeArchivesSearchVo;

/**
 * @Author: mlf
 * @Date: 2021/2/2 16:58:57
 * @Version: 1.0
 */
public interface IMakeArchivesSearchService {


    IPage<MakeArchivesSearchVo> listByKeyWord(MakeArchivesSearchDto makeArchivesSearchDto);

    DataCommonVo searchEntityArchives(Integer num, Integer size, String searchThings);

}
