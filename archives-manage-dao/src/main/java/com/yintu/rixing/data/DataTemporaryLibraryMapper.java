package com.yintu.rixing.data;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2021/1/21 16:07:29
 * @Version: 1.0
 */
@Mapper
public interface DataTemporaryLibraryMapper {


    void insertSelective(DataCommonAll dataCommonAll);

    void insertSelectiveBatch(DataCommonAll dataCommonAll);

    void deleteByPrimaryKey(DataCommonAll dataCommonAll);

    void deleteByPrimaryKeys(Set<Integer> ids, String tableName);

    void updateByPrimaryKeySelective(DataCommonAll dataCommonAll);

    Map<String, Object> selectByPrimaryKey(Integer id, String tableName);

    Page<Map<String, Object>> selectPage(Page<Map<String, Object>> page, DataCommonAll dataCommonAll);

}
