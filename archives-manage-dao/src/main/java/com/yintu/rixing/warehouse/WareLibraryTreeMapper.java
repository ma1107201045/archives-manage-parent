package com.yintu.rixing.warehouse;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 库房管理的树表 Mapper 接口
 * </p>
 *
 * @author Mr.liu
 * @since 2021-01-28
 */
@Mapper
public interface WareLibraryTreeMapper extends BaseMapper<WareLibraryTree> {

    Integer findParentId(Integer integer);
}
