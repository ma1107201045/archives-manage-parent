package com.yintu.rixing.system;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author mlf
 * @since 2020-12-23
 */
@Mapper
public interface SysTemplateMapper extends BaseMapper<SysTemplate> {

    @Select("SHOW  FULL FIELDS FROM ${tablename}")
    List<SysTableMessge> findByTable(@Param("tablename") String tablename);

    @Select("ALTER TABLE ${tablename} ADD ${field}   ${valuetype}(${fieldlength})  ")
    Integer innertFleId(Map map);

    @Delete("ALTER TABLE ${tablename} DROP ${fieldName};")
    boolean deleField(String tablename, String fieldName);

    @Update("ALTER TABLE ${tablename} CHANGE ${laofield} ${newfield} ${type}(${fieldlength})")
    Integer editfield(Map<String, Object> map);

    @Select("select tablename from sys_template where id=#{id}")
    SysTemplate findById(@Param("id") Integer id);

    @Select("select tablename from sys_template where tepname=#{tepname}")
    SysTemplate findByname(String tepname);

    @Select("select * from sys_template where pid=#{id}")
    List<SysTemplate> findByPId(@Param("id") Integer id);
}
