package com.yintu.rixing.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.security.SecLogDto;
import com.yintu.rixing.enumobject.EnumAuthType;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.security.ISecLogService;
import com.yintu.rixing.security.SecLog;
import com.yintu.rixing.system.SysUser;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.util.TreeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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

    @Log(level = EnumLogLevel.TRACE, module = "公共模块", context = "查询菜单栏信息")
    @GetMapping
    @ApiOperation(value = "查询菜单栏信息", notes = " 查询菜单栏信息")
    public ResultDataUtil<Object> findPage() {
        SysUser sysUser = Authenticator.getPrincipal();
        List<TreeUtil> treeUtils = new ArrayList<>();
        if (sysUser != null) {
            if (sysUser.getAuthType().equals(EnumAuthType.ADMIN.getValue()))
                treeUtils = iCommMenuService.findMenus(-1, null);
            else
                treeUtils = iCommMenuService.findMenus(-1, sysUser.getId());
        }
        return ResultDataUtil.ok("查询菜单栏信息成功", treeUtils);
    }

}
