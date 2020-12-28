package com.yintu.rixing.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T extends Serializable, PK extends Serializable> {

    void add(T entity);

    void remove(PK id);

    void edit(T entity);

    T findById(PK id);

    List<T> findAll();

    Page<T> findPage(Integer num, Integer size);

}
