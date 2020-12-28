package com.yintu.rixing.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author mlf
 * @since 2020-12-22
 */
@Mapper
public interface SysDepartmentMapper extends BaseMapper<SysDepartment> {
    @Select("SELECT NAME FROM sys_department WHERE id=(SELECT department_id FROM sys_department_user WHERE id=#{id})")
    SysDepartment findById(Integer id);

}
