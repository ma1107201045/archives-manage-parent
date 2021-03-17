package com.yintu.rixing.make;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.pojo.MakeArchivesSearchPojo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2021/2/2 17:43:24
 * @Version: 1.0
 */
@Mapper
public interface MakeArchivesSearchMapper {

    Page<MakeArchivesSearchPojo> selectByKeyWord(Page<MakeArchivesSearchPojo> page, String keyWord);

    Page<Map<String, Object>> findElectronicsDatasBySomethings(Page page, String searchThings, String fullTableName);

    Page<MakeArchivesSearchPojo> selectFileByKeyWord(Page<MakeArchivesSearchPojo> page, String keyWord);

    Page<MakeArchivesSearchPojo> selectFileByWords(Page<MakeArchivesSearchPojo> page, String searchThings, Integer archivesDirectoryId, Integer archivesLibId);

    Page<Map<Object, String>> findDatasBySomethings(Page page, String fullTableName, String searchThings);

    Page<MakeArchivesSearchPojo> selectFileByIds(Page<MakeArchivesSearchPojo> page, Integer archivesDirectoryId, Integer archivesLibId);
}
