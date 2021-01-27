package com.yintu.rixing.demo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author mlf
 * @since 2020-11-25
 */
@Mapper
public interface DeTestMapper extends BaseMapper<DeTest> {

    Map<String, Object> selectById();
}
