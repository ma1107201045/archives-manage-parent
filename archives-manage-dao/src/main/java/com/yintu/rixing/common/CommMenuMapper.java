package com.yintu.rixing.common;

import com.yintu.rixing.vo.common.CommMenuVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/4 10:42:51
 * @Version: 1.0
 */
@Mapper
public interface CommMenuMapper {


    List<CommMenuVo> selectByExample(Integer permissionId, Short menu);
    
    List<CommMenuVo> selectByExampleAndUserId(Integer permissionId, Short menu, Integer userId);
}
