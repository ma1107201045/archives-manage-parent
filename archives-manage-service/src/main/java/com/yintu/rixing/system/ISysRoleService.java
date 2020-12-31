package com.yintu.rixing.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yintu.rixing.dto.system.SysRoleFormDto;
import com.yintu.rixing.dto.system.SysRoleQueryDto;
import com.yintu.rixing.util.TreeUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author mlf
 * @since 2020-11-26
 */
public interface ISysRoleService extends IService<SysRole> {

    @Transactional(rollbackFor = {Exception.class})
    void save(SysRoleFormDto sysRoleFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void updateById(SysRoleFormDto sysRoleFormDto);

    @Transactional(rollbackFor = {Exception.class})
    void savePermissionsById(Integer id, Set<Integer> permissionIds);

    Page<SysRole> page(SysRoleQueryDto sysRoleQueryDto);

    List<SysPermission> sysPermissionsByIdAndParentId(Integer id, Integer parentId);

    void sysPermissionTreeByIdAndParentId(Integer id, Integer parentId, List<TreeUtil> treeNodeUtils);


}
