package com.yintu.rixing.notification.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yintu.rixing.notification.INotNoticemessageService;
import com.yintu.rixing.notification.NotNoticemessage;
import com.yintu.rixing.notification.NotNoticemessageMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 通知公告表 服务实现类
 * </p>
 *
 * @author Mr.liu
 * @since 2020-12-30
 */
@Service
public class NotNoticemessageServiceImpl extends ServiceImpl<NotNoticemessageMapper, NotNoticemessage> implements INotNoticemessageService {

    @Override
    public List<NotNoticemessage> listByLimit(Integer num) {
        QueryWrapper<NotNoticemessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().orderByDesc(NotNoticemessage::getId).last("limit ").last(num.toString());
        return this.list(queryWrapper);
    }
}
