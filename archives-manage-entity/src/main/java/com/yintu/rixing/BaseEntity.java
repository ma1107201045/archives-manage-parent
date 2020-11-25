package com.yintu.rixing;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @Author: mlf
 * @Date: 2020/11/25 13:32
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseEntity extends IdEntity {

    private static final long serialVersionUID = 7607862306834821931L;

    private String createBy;

    private Date createTime;

    private String modifiedBy;

    private Date modifiedTime;
}
