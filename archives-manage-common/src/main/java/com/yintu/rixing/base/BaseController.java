package com.yintu.rixing.base;

import com.yintu.rixing.util.ResultDataUtil;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2020/11/27 13:18
 * @Version: 1.0
 */
public interface BaseController<T, E, PK extends Serializable> {

    ResultDataUtil<Object> add(T entity);

    ResultDataUtil<Object> remove(Set<PK> id);

    ResultDataUtil<Object> edit(PK id, T entity);

    ResultDataUtil<E> findById(PK id);

}
