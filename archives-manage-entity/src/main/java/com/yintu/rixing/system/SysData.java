package com.yintu.rixing.system;

import lombok.Data;

/**
 * 首页数据统计的实体类
 */

@Data
public class SysData {
    private Integer cdnumber;       //查档人数
    private Integer daywnumber;     //馆藏档案原文总页数
    private Integer gcfilenumber;   //馆藏文件目录数量
    private Integer gdfilenumber;   //归档文件数量
}
