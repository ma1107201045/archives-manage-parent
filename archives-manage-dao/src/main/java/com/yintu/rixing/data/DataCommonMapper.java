package com.yintu.rixing.data;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2021/1/20 10:14:03
 * @Version: 1.0
 */
@Mapper
public interface DataCommonMapper {


    Map<String, Object> selectByPrimaryKey(DataCommon dataCommon);

    List<Map<String, Object>> selectByPrimaryKeys(String tableName, Set<Integer> ids);
}
