package com.yintu.rixing.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.dto.base.IdDto;
import com.yintu.rixing.dto.base.PageDto;
import com.yintu.rixing.util.ResultDataUtil;

import java.io.Serializable;
import java.util.Set;

/**
 * @Author: mlf
 * @Date: 2020/11/27 13:18
 * @Version: 1.0
 */
public interface BaseController<F extends IdDto, Q extends PageDto, E, PK extends Serializable> {

    /**
     * 添加数据
     *
     * @param formDto 通用表单提交DTO
     * @return 返回数据
     */
    ResultDataUtil<Object> add(F formDto);

    /**
     * 批量删除数据
     *
     * @param ids id集合
     * @return 返回数据
     */
    ResultDataUtil<Object> remove(Set<PK> ids);

    /**
     * 修改数据
     *
     * @param id      主键id
     * @param formDto 通用表单提交DTO
     * @return 返回数据
     */
    ResultDataUtil<Object> edit(PK id, F formDto);

    /**
     * 查询单条数据
     *
     * @param id 主键id
     * @return 返回数据
     */
    ResultDataUtil<E> findById(PK id);

    /**
     * 查询分页数据
     *
     * @param queryDto 查询
     * @return 返回数据
     */
    ResultDataUtil<Page<E>> findPage(Q queryDto);

}
