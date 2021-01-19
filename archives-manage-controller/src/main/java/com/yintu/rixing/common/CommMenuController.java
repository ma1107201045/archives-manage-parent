package com.yintu.rixing.common;

import com.alibaba.fastjson.JSONObject;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.enumobject.EnumAuthType;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.system.SysUser;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.util.TreeUtil;
import com.yintu.rixing.vo.common.CommAuthorityVo;
import com.yintu.rixing.vo.common.CommPermissionVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/4 10:32:14
 * @Version: 1.0
 */
@RestController
@RequestMapping("/common/comm-menu")
@Api(tags = "菜单栏接口")
@ApiSort(1)
public class CommMenuController extends Authenticator {

    @Autowired
    private ICommMenuService iCommMenuService;

    @Log(level = EnumLogLevel.TRACE, module = "公共模块", context = "查询菜单栏以及权限信息")
    @GetMapping
    @ApiOperation(value = "查询菜单栏以及权限信息", notes = "查询菜单栏以及权限信息")
    public ResultDataUtil<CommPermissionVo> findPage() {
        CommPermissionVo commPermissionVo = new CommPermissionVo();
        SysUser sysUser = Authenticator.getPrincipal();
        List<TreeUtil> treeUtils = null;
        List<CommAuthorityVo> commAuthorityVos = null;
        if (sysUser != null) {
            if (sysUser.getAuthType().equals(EnumAuthType.ADMIN.getValue())) {
                treeUtils = iCommMenuService.findMenus(-1, null);
                commAuthorityVos = iCommMenuService.findAuthorities(null);
            } else {
                Integer userId = sysUser.getId();
                treeUtils = iCommMenuService.findMenus(-1, userId);
                iCommMenuService.findAuthorities(userId);
            }
        }
        commPermissionVo.setMenu(treeUtils);
        commPermissionVo.setAuthorities(commAuthorityVos);
        return ResultDataUtil.ok("查询菜单栏信息以及权限成功", commPermissionVo);
    }

}
