package com.yintu.rixing.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.dto.system.SysArchivesLibraryNumberSettingDto;

import java.util.List;

/**
 * <p>
 * 系统档案库字段表（档案设置表） 服务类
 * </p>
 *
 * @author mlf
 * @since 2021-03-09
 */
public interface ISysArchivesLibraryNumberSettingService extends IService<SysArchivesLibraryNumberSetting> {

    /**
     * 档号设置
     *
     * @param sysArchivesLibraryNumberSettingDto ..
     */
    void archivesLibraryNumberSetting(SysArchivesLibraryNumberSettingDto sysArchivesLibraryNumberSettingDto);

    /**
     * 返回设置条目
     *
     * @param archivesLibraryId 档案库id
     * @return ..
     */
    List<SysArchivesLibraryNumberSetting> findByArchivesLibraryId(Integer archivesLibraryId);

}
