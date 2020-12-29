package com.yintu.rixing.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2020/11/27 13:18
 * @Version: 1.0
 */
public interface BaseController<T, PK extends Serializable> {

    Map<String, Object> add(T entity);

    Map<String, Object> remove(Set<PK> id);

    Map<String, Object> edit(PK id, T entity);

    Map<String, Object> findById(PK id);

}
