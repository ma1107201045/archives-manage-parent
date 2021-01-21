package com.yintu.rixing.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yintu.rixing.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 系统日志表
 * </p>
 *
 * @Author: mlf
 * @Date: 2020/11/25 14:49
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysUser对象", description = "系统日志表")
public class SysUser extends BaseEntity implements UserDetails {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名", position = 6)
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "密码", position = 7)
    @TableField("password")
    @JsonIgnore
    @JSONField(serialize = false)
    private String password;

    @ApiModelProperty(value = "用户名称", position = 8)
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty(value = "证件类型", position = 9)
    @TableField("certificate_type")
    private Short certificateType;

    @ApiModelProperty(value = "证件号码", position = 10)
    @TableField("certificate_number")
    private String certificateNumber;

    @ApiModelProperty(value = "邮箱", position = 11)
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "地址", position = 12)
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "电话或者手机号码", position = 13)
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "账户过期", position = 14)
    @TableField("account_expired")
    private Short accountExpired;

    @ApiModelProperty(value = "账户锁定", position = 15)
    @TableField("account_locked")
    private Short accountLocked;

    @ApiModelProperty(value = "密码过期", position = 16)
    @TableField("credentials_expired")
    private Short credentialsExpired;

    @ApiModelProperty(value = "账户禁用", position = 17)
    @TableField("account_enabled")
    private Short accountEnabled;

    @ApiModelProperty(value = "用户类型 0.普通用户 1.管理员用户", position = 18)
    @TableField("auth_type")
    private Short authType;

    @Version
    private Integer version;

    @ApiModelProperty(value = "用户拥有角色集", position = 19)
    @TableField(exist = false)
    private List<SysRole> sysRoles;

    @ApiModelProperty(value = "用户所在部门集", position = 20)
    @TableField(exist = false)
    private List<SysDepartment> sysDepartments;


    @JsonIgnore
    @JSONField(serialize = false)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> list = new ArrayList<>(sysRoles.size());
        for (SysRole sysRole : sysRoles) {
            list.add(new SimpleGrantedAuthority(sysRole.getName()));
        }
        return list;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }


    @JsonIgnore
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonExpired() {
        return !(accountExpired == null || accountExpired == 1);
    }


    @JsonIgnore
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonLocked() {
        return !(accountLocked == null || accountLocked == 1);
    }


    @JsonIgnore
    @JSONField(serialize = false)
    @Override
    public boolean isCredentialsNonExpired() {
        return !(credentialsExpired == null || credentialsExpired == 1);
    }


    @JsonIgnore
    @JSONField(serialize = false)
    @Override
    public boolean isEnabled() {
        return !(accountEnabled == null || accountEnabled == 0);
    }
}
