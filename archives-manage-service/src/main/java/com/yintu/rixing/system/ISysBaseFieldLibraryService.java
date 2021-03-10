package com.yintu.rixing.system;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统基础字段库表 服务类
 * </p>
 *
 * @author mlf
 * @since 2021-01-22
 */
public interface ISysBaseFieldLibraryService extends IService<SysBaseFieldLibrary> {

    List<Integer> listByDataKey(String dataKey);
}
