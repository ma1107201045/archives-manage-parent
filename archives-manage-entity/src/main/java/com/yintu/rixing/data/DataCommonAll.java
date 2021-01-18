package com.yintu.rixing.data;

import lombok.Data;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/18 19:04:07
 * @Version: 1.0
 */
@Data
public class DataCommonAll {

    private String tableName;

    private List<DataCommon> dataCommons;
}
