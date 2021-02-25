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

    /**
     * 查询首页统计数据
     *
     * @param userType 用户类型 1：内部 2：远程
     * @return ..
     */
    List<Long> selectInxDataStatistics(Short userType);

}
