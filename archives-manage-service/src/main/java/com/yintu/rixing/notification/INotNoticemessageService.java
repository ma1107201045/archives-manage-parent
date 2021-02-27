package com.yintu.rixing.notification;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 通知公告表 服务类
 * </p>
 *
 * @author Mr.liu
 * @since 2020-12-30
 */
public interface INotNoticemessageService extends IService<NotNoticemessage> {

    List<NotNoticemessage> listByLimit(Integer num);
}
