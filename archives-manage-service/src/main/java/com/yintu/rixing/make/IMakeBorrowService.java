package com.yintu.rixing.make;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.dto.make.MakeBorrowApproveDto;
import com.yintu.rixing.dto.make.MakeBorrowQueryDto;
import com.yintu.rixing.dto.make.MakeBorrowRemoteFormDto;
import com.yintu.rixing.vo.make.MakeBorrowVo;

/**
 * <p>
 * 利用中心的借阅申请表 服务类
 * </p>
 *
 * @author mlf
 * @since 2021-02-03
 */
public interface IMakeBorrowService extends IService<MakeBorrow> {

    void saveRemote(MakeBorrowRemoteFormDto borrowRemoteFormDto);

    void approve(MakeBorrowApproveDto makeBorrowApproveDto);

    Page<MakeBorrowVo> page(MakeBorrowQueryDto makeBorrowQueryElectronicDto);


}
