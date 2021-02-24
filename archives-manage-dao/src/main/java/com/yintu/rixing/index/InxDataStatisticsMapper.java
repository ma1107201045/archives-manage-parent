package com.yintu.rixing.index;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/2/24 16:59:08
 * @Version: 1.0
 */
@Mapper
public interface InxDataStatisticsMapper {

    List<Long> selectall();

}
