package com.yintu.rixing.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mlf
 * @since 2020-12-18
 */
@Mapper
public interface SysLibraryMapper extends BaseMapper<SysLibrary> {

    @Select("select * from sys_library where pid=#{id}")
    List<SysLibrary> findById(@Param("id") Integer id);


}
