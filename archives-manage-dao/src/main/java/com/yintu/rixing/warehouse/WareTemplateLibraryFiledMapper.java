package com.yintu.rixing.warehouse;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;
import java.util.Set;

@Mapper
public interface WareTemplateLibraryFiledMapper extends BaseMapper<WareTemplateLibraryField> {

    Integer findTable(String tableName);

    Map<String, Object> findTurnLeftState(String tableName);

    void addWarehouse(Set<String> key, Set<Object> value, String tableName);
}