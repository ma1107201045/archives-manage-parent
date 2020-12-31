package com.yintu.rixing.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.dto.common.IdDto;
import com.yintu.rixing.dto.common.PageDto;
import com.yintu.rixing.util.ResultDataUtil;

import java.io.Serializable;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2020/11/27 13:18
 * @Version: 1.0
 */
public interface BaseController<F extends IdDto, Q extends PageDto, E, PK extends Serializable> {

    ResultDataUtil<Object> add(F formDto);

    ResultDataUtil<Object> remove(Set<PK> id);

    ResultDataUtil<Object> edit(PK id, F formDto);

    ResultDataUtil<E> findById(PK id);

    ResultDataUtil<Page<E>> findPage(Q queryDto);

}
