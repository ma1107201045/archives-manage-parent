package com.yintu.rixing.vo.system;

import lombok.Data;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2020/12/29 15:36:24
 * @Version: 1.0
 */
@Data
public class SysPermissionVo {

    private String url;

    private String method;

    List<SysRoleVo> sysRoleVos;
}
