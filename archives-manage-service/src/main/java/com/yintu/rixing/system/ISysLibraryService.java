package com.yintu.rixing.system;


import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mlf
 * @since 2020-12-18
 */
public interface ISysLibraryService extends IService<SysLibrary> {


   List<Map<String, Object>> findById(Integer id);




}
