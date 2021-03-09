package com.yintu.rixing.common;

import com.yintu.rixing.dto.common.CommPersonalDetailsFormDto;
import com.yintu.rixing.dto.system.SysUserFormDto;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: mlf
 * @Date: 2021/3/9 10:31:46
 * @Version: 1.0
 */
public interface ICommPersonalDetailsService {

    @Transactional(rollbackFor = {Exception.class})
    void updateById(CommPersonalDetailsFormDto commPersonalDetailsFormDto);


}
