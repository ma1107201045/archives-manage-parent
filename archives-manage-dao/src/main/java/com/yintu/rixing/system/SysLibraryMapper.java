package com.yintu.rixing.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * Mapper 接口
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

    @Update("CREATE TABLE ${newtablename} LIKE ${laotablename}  ")
    Integer createNewTable(@Param("newtablename") String newtablename, @Param("laotablename") String laotablename);

    @Update("update sys_library set tablename=#{newtableName} where pid=#{id} ")
    Integer updateByIdTablename(Integer id, String newtableName);

    @Select("SELECT COUNT(1) AS COUNT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME=#{tablename}")
    Integer seleBytablename(String tablename);

    @Select("select * from sys_library where id=#{id}")
    SysLibrary findByOne(@Param("id") Integer id);

    @Select("DESC  ${tablename} ${field}")
    SysTableMessge findByField(String tablename, String field);

    @Update(" ALTER TABLE ${tablename} MODIFY ${sfieldName}  ${valuetype}(${fieldlength})  after  ${mfieldName};")
    Integer xsfield(Map<String, Object> map);//向上

    @Update(" ALTER TABLE ${tablename} MODIFY ${mfieldName}  ${valuetype}(${fieldlength})  after  ${xfieldName};")
    Integer xxfield(Map<String, Object> map);

}
