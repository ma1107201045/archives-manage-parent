package com.yintu.rixing.config.component;

import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.util.PathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @Author: mlf
 * @Date: 2021/1/13 15:53:03
 * @Version: 1.0
 */
@Component
@Slf4j
public class CustomCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        File file1 = new File(PathUtil.getFilePath());
        if (!file1.exists())
            if (!file1.mkdirs())
                throw new BaseRuntimeException("创建文件目录有误");
        File file2 = new File(PathUtil.getBackupPath());
        if (!file2.exists())
            if (!file2.mkdirs())
                throw new BaseRuntimeException("创建备份目录有误");
        File file3 = new File(PathUtil.getLogPath());
        if (!file3.exists())
            if (!file3.mkdirs())
                throw new BaseRuntimeException("创建日志目录有误");
    }
}
