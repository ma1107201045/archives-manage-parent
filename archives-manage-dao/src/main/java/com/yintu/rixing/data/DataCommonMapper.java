package com.yintu.rixing.data;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2021/1/20 10:14:03
 * @Version: 1.0
 */
@Mapper
public interface DataCommonMapper {

    Map<String, Object> abc(@Param("data") DataCommonAll dataCommonAll);
}
