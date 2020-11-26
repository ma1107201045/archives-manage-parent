package com.yintu.rixing.system;

import com.yintu.rixing.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @Author: mlf
 * @Date: 2020/11/25 14:49
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUser extends BaseEntity implements UserDetails {

    private static final long serialVersionUID = -2025703815733569104L;

    private String username;

    private String password;

    private Short enabled;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
