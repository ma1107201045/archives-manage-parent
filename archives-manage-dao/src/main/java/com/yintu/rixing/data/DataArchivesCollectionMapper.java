package com.yintu.rixing.data;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/18 16:21:31
 * @Version: 1.0
 */
@Mapper
public interface DataArchivesCollectionMapper {

    void insertSelective(DataCommonAll dataCommonAll);
}
