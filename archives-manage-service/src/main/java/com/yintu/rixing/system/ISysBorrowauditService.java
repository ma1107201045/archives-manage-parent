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
 * @since 2020-12-25
 */
public interface ISysBorrowauditService extends IService<SysBorrowaudit> {

    List<SysBorrowaudit> findById(Integer id);

    Map<String, Object> chakan(String borrower);

    Map<String, Object> djborrower(String borrower);

}
