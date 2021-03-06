package com.yintu.rixing.make;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 档案编研表 Mapper 接口
 * </p>
 *
 * @author Mr.liu
 * @since 2021-02-22
 */
@Mapper
public interface MakeCompilationMapper extends BaseMapper<MakeCompilation> {

    Page<MakeCompilation> findAllNotAudit(Page page, String topicName, Integer userId);

    Page<MakeCompilation> findAllAlreadyAudit(Page page, String topicName, Integer userId);

    Page<MakeCompilation> findAllMyCompilation(Page page, String topicName, Integer userId);
}
