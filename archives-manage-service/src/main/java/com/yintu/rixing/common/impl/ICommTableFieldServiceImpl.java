package com.yintu.rixing.common.impl;

import com.yintu.rixing.common.CommTableField;
import com.yintu.rixing.common.CommTableFieldMapper;
import com.yintu.rixing.common.ICommTableFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/12 10:15:42
 * @Version: 1.0
 */
@Service
public class ICommTableFieldServiceImpl implements ICommTableFieldService {
    @Autowired
    private CommTableFieldMapper commTableFieldMapper;

    public void addTable(String tableName, List<CommTableField> commTableFields) {
        commTableFieldMapper.createTable(tableName, commTableFields);
    }
}
