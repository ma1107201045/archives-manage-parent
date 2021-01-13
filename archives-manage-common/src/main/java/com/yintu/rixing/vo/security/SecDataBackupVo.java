package com.yintu.rixing.vo.security;

import lombok.Data;

/**
 * @Author: mlf
 * @Date: 2021/1/13 17:29:02
 * @Version: 1.0
 */
@Data
public class SecDataBackupVo {

    private Long tableCount;

    private Long recordCount;

    private Double recordSize;
}
