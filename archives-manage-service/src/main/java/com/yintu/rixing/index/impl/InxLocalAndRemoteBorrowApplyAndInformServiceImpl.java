package com.yintu.rixing.index.impl;

import com.yintu.rixing.index.IInxLocalAndRemoteBorrowApplyAndInformService;
import com.yintu.rixing.index.InxLocalAndRemoteBorrowApplyAndInformMapper;
import com.yintu.rixing.vo.index.InxLocalAndRemoteBorrowApplyAndInformVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: mlf
 * @Date: 2021/2/26 11:50:30
 * @Version: 1.0
 */
@Service
public class InxLocalAndRemoteBorrowApplyAndInformServiceImpl implements IInxLocalAndRemoteBorrowApplyAndInformService {

    @Autowired
    private InxLocalAndRemoteBorrowApplyAndInformMapper inxLocalAndRemoteBorrowApplyAndInformMapper;

    @Override
    public InxLocalAndRemoteBorrowApplyAndInformVo findInxLocalOrRemoteBorrowApplyAndInformData(Short borrowType) {
        return null;
    }

}
