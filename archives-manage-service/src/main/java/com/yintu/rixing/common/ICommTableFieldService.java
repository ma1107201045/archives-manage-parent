package com.yintu.rixing.common;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/11 18:09:52
 * @Version: 1.0
 */
public interface ICommTableFieldService {

    /**
     * 创建表
     *
     * @param tableName       表明
     * @param commTableFields 字段详情
     */
    @Transactional(rollbackFor = {Exception.class})
    void addTable(String tableName, List<CommTableField> commTableFields);
}
