package com.yintu.rixing.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.dto.system.SysDepartmentFormDto;
import com.yintu.rixing.util.TreeUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author mlf
 * @since 2020-12-22
 */
public interface ISysDepartmentService extends IService<SysDepartment> {

    @Transactional(rollbackFor = {Exception.class})
    void save(SysDepartmentFormDto sysDepartmentFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void removeTree(Integer id);

    @Transactional(rollbackFor = {Exception.class})
    void updateById(SysDepartmentFormDto sysDepartmentFormDto);

    List<TreeUtil> listTree(Integer parentId);

}
