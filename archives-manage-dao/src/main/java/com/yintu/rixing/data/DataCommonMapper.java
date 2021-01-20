package com.yintu.rixing.data;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2021/1/20 10:14:03
 * @Version: 1.0
 */
@Mapper
public interface DataCommonMapper {

    Map<String, Object> selectByPrimaryKey(DataCommonAll dataCommonAll);
}
