package com.yintu.rixing.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.dto.system.SysPermissionFormDto;
import com.yintu.rixing.util.TreeNodeUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 系统权限表 服务类
 * </p>
 *
 * @author mlf
 * @since 2020-11-26
 */
public interface ISysPermissionService extends IService<SysPermission> {

    @Transactional(rollbackFor = {Exception.class})
    void save(SysPermissionFormDto sysPermissionFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void updateById(SysPermissionFormDto sysPermissionFormDto);

    List<TreeNodeUtil> listTree(Integer parentId);
}
