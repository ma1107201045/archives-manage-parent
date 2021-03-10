package com.yintu.rixing.system.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.system.ISysBaseFieldLibraryService;
import com.yintu.rixing.system.SysBaseFieldLibrary;

import com.yintu.rixing.system.SysBaseFieldLibraryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统基础字段库表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2021-01-22
 */
@Service
public class SysBaseFieldLibraryServiceImpl extends ServiceImpl<SysBaseFieldLibraryMapper, SysBaseFieldLibrary> implements ISysBaseFieldLibraryService {

    @Override
    public List<Integer> listByDataKey(String dataKey) {
        if (StrUtil.isEmpty(dataKey)) {
            throw new BaseRuntimeException("key（英文字段名称）不能为空");
        }
        QueryWrapper<SysBaseFieldLibrary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(SysBaseFieldLibrary::getId)
                .eq(SysBaseFieldLibrary::getDataKey, dataKey);
        return this.listObjs(queryWrapper, id -> (Integer) id);
    }
}
