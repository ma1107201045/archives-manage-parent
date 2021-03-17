package com.yintu.rixing.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * <p>
 * 系统档案库表 Mapper 接口
 * </p>
 *
 * @author mlf
 * @since 2021-01-11
 */
@Mapper
public interface SysArchivesLibraryMapper extends BaseMapper<SysArchivesLibrary> {

    Map<Object, String> findHourseData(String fullTableName, Integer archivesDirectoryId);

}
