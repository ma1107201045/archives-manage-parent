package com.yintu.rixing.config.component;

import com.yintu.rixing.make.IMakeBorrowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author: mlf
 * @Date: 2021/2/6 9:12:32
 * @Version: 1.0
 */
@Component
@Slf4j
public class CustomScheduled {
    @Autowired
    private IMakeBorrowService iMakeBorrowService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void task01() {
        iMakeBorrowService.execute();
        log.info("凌晨过滤借阅过期信息");
    }
}
