package com.yintu.rixing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: mlf
 * @Date: 2021/2/5 10:22:59
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MakeBorrowAuditorPojo {

    /**
     * 借阅记录id
     */
    private Integer makeBorrowId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 当前人是否在审核中 1.是 0.否
     */
    private Short activate;
}
