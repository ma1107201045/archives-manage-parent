package com.yintu.rixing.data;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2021/1/25 16:10:13
 * @Version: 1.0
 */
@Mapper
public interface DataRecycleBinMapper {

    void insertSelective(DataCommon dataCommon);

    void insertSelectiveBatch(DataCommon dataCommon);

    void deleteByPrimaryKey(DataCommon dataCommon);

    void deleteByPrimaryKeys(Set<Integer> ids, String tableName);

    void updateByPrimaryKeySelective(DataCommon dataCommon);

    void updateStatusByPrimaryKeySelective(DataCommon dataCommon);

    Map<String, Object> selectByPrimaryKey(DataCommon dataCommon);

    Page<Map<String, Object>> selectPage(Page<Map<String, Object>> page, DataCommon dataCommon);
}
