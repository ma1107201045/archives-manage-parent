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
     * @param userType      用户类型 1：内部 2：远程
     * @param formalLibrary 是否正式库 1：是 2：否
     * @param tableNames    表集合
     * @param status        档案状态：0.档案收集 1.临时库 2.整理库 3.正式库 4.回收站 5.销毁库 6.回退管理 7.病档管理 8.档案销毁
     * @return ..
     */
    List<Long> selectInxDataStatisticsData(Short userType, Short formalLibrary, List<String> tableNames, Short status);

}
