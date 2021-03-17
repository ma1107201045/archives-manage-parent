package com.yintu.rixing.make;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.vo.make.MakeArchivesSearchVo;

import java.util.List;

/**
 * <p>
 * 档案收藏表 服务类
 * </p>
 *
 * @author Mr.liu
 * @since 2021-03-16
 */
public interface IMakeArchivesCollectionService extends IService<MakeArchivesCollection> {

    Page<MakeArchivesSearchVo> findMyCollection(Integer userId, Integer num, Integer size);
}
