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
    void saveSysRolePermissionsById(Integer id, Set<Integer> permissionIds);

    void saveSysArchivesLibraryById(Integer id, Set<Integer> archivesIds);

    Page<SysRole> page(SysRoleQueryDto sysRoleQueryDto);

    List<SysUser> sysUsersById(Integer id);

    List<SysPermission> sysPermissionsByIdAndPermissionId(Integer id, Integer permissionId);

    void sysPermissionTreeByIdAndPermissionId(Integer id, Integer permissionId, List<TreeUtil> treeUtils);

    List<SysArchivesLibrary> sysArchivesLibrariesByIdAndArchivesLibraryId(Integer id, Integer archivesLibraryId);

    void sysArchivesLibraryTreeByIdAndArchivesLibraryId(Integer id, Integer archivesLibraryId, List<TreeUtil> treeUtils);


}
