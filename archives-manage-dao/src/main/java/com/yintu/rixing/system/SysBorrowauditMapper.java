package com.yintu.rixing.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author mlf
 * @since 2020-12-25
 */
@Mapper
public interface SysBorrowauditMapper extends BaseMapper<SysBorrowaudit> {
    @Select("select * from sys_borrowaudit where  borrower=#{borrower}")
    SysBorrowaudit findByName(@Param("borrower") String borrower);

}
