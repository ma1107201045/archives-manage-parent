package com.yintu.rixing.make.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.make.IMakeCompilationService;
import com.yintu.rixing.make.MakeCompilation;
import com.yintu.rixing.make.MakeCompilationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 档案编研表 服务实现类
 * </p>
 *
 * @author Mr.liu
 * @since 2021-02-22
 */
@Service
public class MakeCompilationServiceImpl extends ServiceImpl<MakeCompilationMapper, MakeCompilation> implements IMakeCompilationService {
    @Autowired
    private MakeCompilationMapper makeCompilationMapper;

    @Override
    public Page<MakeCompilation> findAllMyCompilation(Integer num, Integer size, String topicName, Integer userId) {
        Page page=new Page();
        page.setSize(size);
        page.setCurrent(num);
        Page<MakeCompilation> makeCompilationPage=makeCompilationMapper.findAllMyCompilation(page,topicName,userId);
        return makeCompilationPage;
    }

    @Override
    public Page<MakeCompilation> findAllAlreadyAudit(Integer num, Integer size, String topicName, Integer userId) {
        Page page=new Page();
        page.setSize(size);
        page.setCurrent(num);
        Page<MakeCompilation> makeCompilationPage=makeCompilationMapper.findAllAlreadyAudit(page,topicName,userId);
        return makeCompilationPage;
    }

    @Override
    public Page<MakeCompilation> findAllNotAudit(Integer num, Integer size, String topicName, Integer userId) {
        Page page=new Page();
        page.setSize(size);
        page.setCurrent(num);
        Page<MakeCompilation> makeCompilationPage=makeCompilationMapper.findAllNotAudit(page,topicName,userId);
        return makeCompilationPage;
    }
}
