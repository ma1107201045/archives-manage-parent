package com.yintu.rixing.pojo;

import lombok.Data;

import java.util.List;

/**
 * @Author: mlf
 * @Date: 2020/12/29 15:36:24
 * @Version: 1.0
 */
@Data
public class SysPermissionPojo {

    private String url;

    private String method;

    List<SysRolePojo> sysRoleVos;
}
