package com.yintu.rixing.system;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统档案库字段默认表 服务类
 * </p>
 *
 * @author mlf
 * @since 2021-01-22
 */
public interface ISysArchivesLibraryFieldDefaultService extends IService<SysArchivesLibraryFieldDefault> {
    
    List<Integer> listByDataKey(String dataKey);
}
