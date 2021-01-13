package com.yintu.rixing.make.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.make.IMakeBorrowService;
import com.yintu.rixing.make.MakeBorrow;
import com.yintu.rixing.make.MakeBorrowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 * 利用中心的借阅申请表 服务实现类
 * </p>
 *
 * @author Mr.liu
 * @since 2021-01-11
 */
@Service
public class MakeBorrowServiceImpl extends ServiceImpl<MakeBorrowMapper, MakeBorrow> implements IMakeBorrowService {

    @Autowired
    private MakeBorrowMapper makeBorrowMapper;

    @Override
    public List<MakeBorrow> findEntityBorrowDatas(String name, String certificateNumber) {
        return makeBorrowMapper.findEntityBorrowDatas(name,certificateNumber);
    }

    @Override
    public List<MakeBorrow> findElectronicBorrowDatas(String name, String certificateNumber) {
        return makeBorrowMapper.findElectronicBorrowDatas(name,certificateNumber);
    }
}
