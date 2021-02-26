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
     * 本地或者远程借阅申请
     *
     * @param borrowType 借阅类型 1.本地 2.远程
     * @return .
     */
    List<Map<String, Object>> selectInxLocalOrRemoteBorrowApplyData(Short borrowType);

}
