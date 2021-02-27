package com.yintu.rixing.index;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author: mlf
 * @Date: 2021/2/26 11:06:33
 * @Version: 1.0
 */
@Mapper
public interface InxLocalAndRemoteBorrowApplyAndInformMapper {


    /**
     * 本地借阅申请
     *
     * @return .
     */
    List<Map<String, Object>> selectInxLocalBorrowApplyData();

    /**
     * 远程借阅申请
     *
     * @return .
     */
    List<Map<String, Object>> selectInxRemoteBorrowApplyData();
}
