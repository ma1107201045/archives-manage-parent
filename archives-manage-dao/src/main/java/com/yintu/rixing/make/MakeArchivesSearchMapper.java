package com.yintu.rixing.make;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.pojo.MakeArchivesSearchPojo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: mlf
 * @Date: 2021/2/2 17:43:24
 * @Version: 1.0
 */
@Mapper
public interface MakeArchivesSearchMapper {

    Page<MakeArchivesSearchPojo> selectByKeyWord(Page<MakeArchivesSearchPojo> page, String keyWord);
}
