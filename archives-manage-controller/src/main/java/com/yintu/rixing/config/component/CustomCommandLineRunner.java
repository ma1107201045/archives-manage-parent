package com.yintu.rixing.config.component;

import cn.hutool.core.io.FileUtil;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.util.MachineCodeUtil;
import com.yintu.rixing.util.PathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * @Author: mlf
 * @Date: 2021/1/13 15:53:03
 * @Version: 1.0
 */
@Component
@Slf4j
public class CustomCommandLineRunner implements CommandLineRunner {


    @Autowired // 注入到容器中
    private Environment environment;

    @Override
    public void run(String... args) {
        File file1 = new File(PathUtil.getFilePath());
        if (!file1.exists()) {
            if (!file1.mkdirs()) {
                throw new BaseRuntimeException("创建文件目录有误");
            }
        }
        File file2 = new File(PathUtil.getBackupPath());
        if (!file2.exists()) {
            if (!file2.mkdirs()) {
                throw new BaseRuntimeException("创建备份目录有误");
            }
        }
        File file3 = new File(PathUtil.getLogPath());
        if (!file3.exists()) {
            if (!file3.mkdirs()) {
                throw new BaseRuntimeException("创建日志目录有误");
            }
        }

        String[] activesProfiles = environment.getActiveProfiles();
        for (String activesProfile : activesProfiles) {
            if ("prod".equals(activesProfile)) {
                String machineCode = FileUtil.readString("/machine-code.txt", StandardCharsets.UTF_8);
                if (machineCode != null && !"".equals(machineCode)) {
                    if (!machineCode.equals(MachineCodeUtil.getMachineCode())) {
                        System.exit(0);
                    }
                }
            }
        }
    }
}
