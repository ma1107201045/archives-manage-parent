package com.yintu.rixing.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2021/3/3 16:21:06
 * @Version: 1.0
 */
public class Test {

    public static void main(String[] args) throws IOException, InvalidFormatException {
        ExcelReader excelReader = ExcelUtil.getReader(FileUtil.file("E:\\日兴软件测试数据4\\日兴软件测试数据4\\河南电大\\基建档案条目.xls"));
        List<Map<String, Object>> records = excelReader.readAll();
        System.out.println(records);

        InputStream inp = new FileInputStream(FileUtil.file("C:\\Users\\Admin\\Desktop\\档案收集模板卷内档案1614760698448.xlsx"));
        Workbook wb = WorkbookFactory.create(inp);
        Sheet sheet = wb.getSheetAt(0);
        Row row1 = sheet.getRow(0);
        Row row2 = sheet.getRow(1);


    }
}
