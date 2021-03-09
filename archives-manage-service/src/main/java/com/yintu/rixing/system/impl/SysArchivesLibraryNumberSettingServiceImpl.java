package com.yintu.rixing.system.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysArchivesLibraryNumberSettingDto;
import com.yintu.rixing.system.ISysArchivesLibraryNumberSettingService;
import com.yintu.rixing.system.SysArchivesLibraryNumberSetting;
import com.yintu.rixing.system.SysArchivesLibraryNumberSettingMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统档案库字段表（档案设置表） 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2021-03-09
 */
@Service
public class SysArchivesLibraryNumberSettingServiceImpl extends ServiceImpl<SysArchivesLibraryNumberSettingMapper, SysArchivesLibraryNumberSetting> implements ISysArchivesLibraryNumberSettingService {


    @Override
    public void archivesLibraryNumberSetting(SysArchivesLibraryNumberSettingDto sysArchivesLibraryNumberSettingDto) {
        Integer archivesLibraryId = sysArchivesLibraryNumberSettingDto.getArchivesLibraryId();
        SysArchivesLibraryNumberSetting sysArchivesLibraryNumberSetting = new SysArchivesLibraryNumberSetting();
        BeanUtil.copyProperties(sysArchivesLibraryNumberSettingDto, sysArchivesLibraryNumberSetting);
        QueryWrapper<SysArchivesLibraryNumberSetting> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.lambda().eq(SysArchivesLibraryNumberSetting::getArchivesLibraryId, archivesLibraryId);
        this.remove(queryWrapper1);
        this.save(sysArchivesLibraryNumberSetting);
    }

    @Override
    public List<SysArchivesLibraryNumberSetting> findByArchivesLibraryId(Integer archivesLibraryId) {
        QueryWrapper<SysArchivesLibraryNumberSetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysArchivesLibraryNumberSetting::getArchivesLibraryId, archivesLibraryId);
        return this.list(queryWrapper);
    }
}
