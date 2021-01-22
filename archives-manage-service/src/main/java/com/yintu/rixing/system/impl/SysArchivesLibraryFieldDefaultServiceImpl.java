package com.yintu.rixing.system.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.system.ISysArchivesLibraryFieldDefaultService;
import com.yintu.rixing.system.SysArchivesLibrary;
import com.yintu.rixing.system.SysArchivesLibraryFieldDefault;
import com.yintu.rixing.system.SysArchivesLibraryFieldDefaultMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统档案库字段默认表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2021-01-22
 */
@Service
public class SysArchivesLibraryFieldDefaultServiceImpl extends ServiceImpl<SysArchivesLibraryFieldDefaultMapper, SysArchivesLibraryFieldDefault> implements ISysArchivesLibraryFieldDefaultService {

    @Override
    public List<Integer> listByDataKey(String dataKey) {
        if (StrUtil.isEmpty(dataKey))
            throw new BaseRuntimeException("key不能为空");
        QueryWrapper<SysArchivesLibraryFieldDefault> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(SysArchivesLibraryFieldDefault::getDataKey, dataKey);
        return this.listObjs(queryWrapper, id -> (Integer) id);
    }
}
