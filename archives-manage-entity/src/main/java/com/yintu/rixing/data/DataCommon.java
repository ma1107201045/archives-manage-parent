package com.yintu.rixing.data;

import lombok.Data;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/18 19:04:07
 * @Version: 1.0
 */
@Data
public class DataCommon {

    private String tableName;

    private Integer id;

    private List<DataCommonKV> dataCommonKVs;

    private List<List<DataCommonKV>> lists;


}
