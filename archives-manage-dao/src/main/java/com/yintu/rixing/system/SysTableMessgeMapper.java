package com.yintu.rixing.system;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;


@Mapper
public interface SysTableMessgeMapper extends BaseMapper<SysTableMessge> {
    @Select("SHOW  FULL FIELDS FROM ${tablename}")
    List<SysTableMessge> findByTable(@Param("tablename") String tablename);

    boolean innertFleId( Map map);

    @Delete("ALTER TABLE ${tablename} DROP ${standardfields};")
    boolean deleField(String tablename, String standardfields);


}
