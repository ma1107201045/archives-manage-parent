package com.yintu.rixing.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

import java.util.List;
import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2021/3/3 16:21:06
 * @Version: 1.0
 */
public class Test {

    public static void main(String[] args) {
        ExcelReader excelReader = ExcelUtil.getReader(FileUtil.file("E:\\日兴软件测试数据4\\日兴软件测试数据4\\河南电大\\基建档案条目.xls"));
        List<Map<String, Object>> records = excelReader.readAll();
        System.out.println(records);
    }
}
