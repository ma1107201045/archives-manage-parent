package com.yintu.rixing.data;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2021/1/18 16:21:31
 * @Version: 1.0
 */
@Mapper
public interface DataArchivesCollectionMapper {

    void insertSelective(DataCommonAll dataCommonAll);

    void deleteByPrimaryKey(DataCommonAll dataCommonAll);

    void deleteByPrimaryKeys(Set<Integer> ids, String tableName);

    void updateByPrimaryKeySelective(DataCommonAll dataCommonAll);

    Map<String, Object> selectByPrimaryKey(DataCommonAll dataCommonAll);

    Page<Map<String, Object>> selectPage(Page<Map<String, Object>> page, String tableName);

}
