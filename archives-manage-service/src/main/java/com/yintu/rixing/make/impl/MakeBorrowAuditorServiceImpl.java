package com.yintu.rixing.make.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.make.MakeBorrowAuditorMapper;
import com.yintu.rixing.make.MakeBorrowAuditor;
import com.yintu.rixing.make.IMakeBorrowAuditorService;
import com.yintu.rixing.pojo.MakeBorrowAuditorPojo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 借阅申请文件和审核人中间表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2021-02-03
 */
@Service
public class MakeBorrowAuditorServiceImpl extends ServiceImpl<MakeBorrowAuditorMapper, MakeBorrowAuditor> implements IMakeBorrowAuditorService {

    @Override
    public List<MakeBorrowAuditor> listByMakeBorrowAuditorPojo(MakeBorrowAuditorPojo makeBorrowAuditorPojo) {
        Integer makeBorrowId = makeBorrowAuditorPojo.getMakeBorrowId();
        Integer sort = makeBorrowAuditorPojo.getSort();
        Short activate = makeBorrowAuditorPojo.getActivate();
        QueryWrapper<MakeBorrowAuditor> queryWrapper = new QueryWrapper<>();
        if (makeBorrowId != null)
            queryWrapper.lambda().eq(MakeBorrowAuditor::getMakeBorrowId, makeBorrowId);
        if (sort != null)
            queryWrapper.lambda().eq(MakeBorrowAuditor::getSort, sort);
        if (activate != null)
            queryWrapper.lambda().eq(MakeBorrowAuditor::getActivate, activate);
        return this.list(queryWrapper);
    }
}
