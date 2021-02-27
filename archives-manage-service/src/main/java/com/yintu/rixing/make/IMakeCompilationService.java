package com.yintu.rixing.make;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 档案编研表 服务类
 * </p>
 *
 * @author Mr.liu
 * @since 2021-02-22
 */
public interface IMakeCompilationService extends IService<MakeCompilation> {

    Page<MakeCompilation> findAllNotAudit(Integer num, Integer size, String topicName, Integer userId);

    Page<MakeCompilation> findAllAlreadyAudit(Integer num, Integer size, String topicName, Integer userId);

    Page<MakeCompilation> findAllMyCompilation(Integer num, Integer size, String topicName, Integer userId);
}
