package com.yintu.rixing.make;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 利用中心的借阅申请表 服务类
 * </p>
 *
 * @author Mr.liu
 * @since 2021-01-11
 */
public interface IMakeBorrowService extends IService<MakeBorrow> {

    List<MakeBorrow> findElectronicBorrowDatas(String name, String certificateNumber);

    List<MakeBorrow> findEntityBorrowDatas(String name, String certificateNumber);
}
