package com.yintu.rixing.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author mlf
 * @since 2020-11-26
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    @Select("select id,certificate_type,certificate_number,phone,create_time  from sys_user where true_name=#{borrower}")
    SysUser findById(@Param("borrower") String borrower);
}
