package com.yintu.rixing.gererator;

import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MyBatisGenerator {

    public static void main(String[] args) throws Exception {
//        File configFile = new File("D:\\IDEAProject\\archives-manage-parent\\archives-manage-common\\src\\main\\resources\\generator-config.xml");
        InputStream is = MyBatisGenerator.class.getResourceAsStream("/generator-config.xml");
        List<String> warnings = new ArrayList<>();

        ConfigurationParser cp = new ConfigurationParser(warnings);

        Configuration config = cp.parseConfiguration(is);

        DefaultShellCallback callback = new DefaultShellCallback(true);

        org.mybatis.generator.api.MyBatisGenerator myBatisGenerator = new org.mybatis.generator.api.MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);

    }
}
