package com.yintu.rixing.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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
    /**
     * .
     *
     * @param borrower 借阅者
     * @return .
     */
    @Select("select id,certificate_type,certificate_number,phone,create_time  from sys_user where true_name=#{borrower}")
    SysUser findById(@Param("borrower") String borrower);

    /**
     * 查询用户所配置的档案库数据
     *
     * @param id                用户id
     * @param archivesLibraryId 档案库id
     * @return ..
     */
    List<SysArchivesLibrary> selectSysArchivesLibraryByIdAndArchivesLibraryId(Integer id, Integer archivesLibraryId);
}
