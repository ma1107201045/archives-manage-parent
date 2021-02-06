package com.yintu.rixing.make;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.dto.make.MakeBorrowApproveDto;
import com.yintu.rixing.dto.make.MakeBorrowQueryDto;
import com.yintu.rixing.dto.make.MakeBorrowRemoteFormDto;
import com.yintu.rixing.system.SysUser;
import com.yintu.rixing.vo.make.MakeBorrowTransferVo;
import com.yintu.rixing.vo.make.MakeBorrowVo;

import java.util.List;

/**
 * <p>
 * 利用中心的借阅申请表 服务类
 * </p>
 *
 * @author mlf
 * @since 2021-02-03
 */
public interface IMakeBorrowService extends IService<MakeBorrow> {

    @Transactional(rollbackFor = {Exception.class})
    void add(MakeBorrow makeBorrow);

    /**
     * 远程借阅
     *
     * @param borrowRemoteFormDto ...
     */
    @Transactional(rollbackFor = {Exception.class})
    void saveRemote(MakeBorrowRemoteFormDto borrowRemoteFormDto);

    /**
     * 审核
     *
     * @param makeBorrowApproveDto ..
     */
    @Transactional(rollbackFor = {Exception.class})
    void approve(MakeBorrowApproveDto makeBorrowApproveDto);

    /**
     * 转交列表
     *
     * @param id 借阅记录id
     * @return ..
     */
    List<MakeBorrowTransferVo> listTransferById(Integer id, Integer currentUserId);

    /**
     * 借阅列表
     *
     * @param makeBorrowQueryElectronicDto ...
     * @return ..
     */
    Page<MakeBorrowVo> page(MakeBorrowQueryDto makeBorrowQueryElectronicDto);

    /**
     * 凌晨执行借阅到期的
     */
    @Transactional(rollbackFor = {Exception.class})
    void execute();

}
