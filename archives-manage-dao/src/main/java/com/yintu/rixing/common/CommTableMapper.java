package com.yintu.rixing.common;

import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: mlf
 * @Date: 2021/1/11 18:25:24
 * @Version: 1.0
 */
@Mapper
public interface CommTableMapper {

    void create(String tableName, String filedNameStr);

    void dropByTableName(String tableName);

    void alterByTableName(String oldTableName, String newTableName);

    void descByTableName(String tableName);
}
