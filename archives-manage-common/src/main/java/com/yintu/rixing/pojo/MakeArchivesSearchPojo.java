package com.yintu.rixing.pojo;

import lombok.Data;

/**
 * @Author: mlf
 * @Date: 2021/2/2 17:46:21
 * @Version: 1.0
 */
@Data
public class MakeArchivesSearchPojo {


    /**
     * 档案库id
     */
    private Integer archivesLibId;

    /**
     * 档案库类型 1.电子 2.实体
     */
    private Short archivesLibType;

    /**
     * 档案目录id
     */
    private Integer archivesDirectoryId;
    /**
     * 档案目录文件id
     */
    private Integer archivesFileId;
    /**
     * 档案目录文件名称
     */
    private String archivesFileOriginalName;
    /**
     * 档案目录文件内容
     */
    private String archivesFileContext;
}
