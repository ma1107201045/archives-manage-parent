package com.yintu.rixing.warehouse;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface WareTemplateLibraryFiledMapper extends BaseMapper<WareTemplateLibraryField> {

    Integer findTable(String tableName);

    List<Map<String, Object>> findTurnLeftState(String tableName);

    void addWarehouse(List<String> keys, List<Object> values, String tableName);

    void addWarehousee(StringBuilder sb1, StringBuilder sb2, String tableName);

    Page<Map<String, Object>> findAllEntityArchivesPage(Page page);

    Page<Map<String, Object>> findInWarehousePage(Page page);

    Page<Map<String, Object>> findOutWarehouse(Page page);

    void updateWarehouse(StringBuilder sb1, String tableName, Integer id);

    void deleteWarehouse(Integer id);

    void outWarehouse(Integer id);

    void inWarehouse(Integer id);

    Page<Map<String, Object>> findAllEntityArchivesBySomethings(Page page, StringBuilder sb1);

    Page<Map<String, Object>> findInWarehouseBySomethings(Page page, StringBuilder sb1);

    Page<Map<String, Object>> findOutWarehouseBySomethings(Page page, StringBuilder sb1);
}