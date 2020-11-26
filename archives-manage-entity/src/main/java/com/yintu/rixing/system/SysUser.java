package com.yintu.rixing.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yintu.rixing.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysUser对象", description = "系统日志表")
public class SysUser extends BaseEntity implements UserDetails {


    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    @JsonIgnore
    @JSONField(serialize = false)
    private String password;

    @ApiModelProperty(value = "昵称")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "电话号码")
    @TableField("phone_number")
    private String phoneNumber;

    @ApiModelProperty(value = "手机号")
    @TableField("mobile_number")
    private String mobileNumber;

    @ApiModelProperty(value = "账户过期")
    @TableField("account_expired")
    private Short accountExpired;

    @ApiModelProperty(value = "账户锁定")
    @TableField("account_locked")
    private Short accountLocked;

    @ApiModelProperty(value = "密码过期")
    @TableField("credentials_expired")
    private Short credentialsExpired;

    @ApiModelProperty(value = "账户禁用")
    @TableField("account_enabled")
    private Short accountEnabled;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !(accountExpired == null || accountExpired == 1);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !(accountLocked == null || accountLocked == 1);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !(credentialsExpired == null || credentialsExpired == 1);
    }

    @Override
    public boolean isEnabled() {
        return !(accountEnabled == null || accountEnabled == 0);
    }
}
