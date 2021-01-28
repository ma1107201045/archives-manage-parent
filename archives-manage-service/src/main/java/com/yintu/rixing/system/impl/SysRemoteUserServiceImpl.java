package com.yintu.rixing.system.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.dto.system.SysRemoteUserFormDto;
import com.yintu.rixing.dto.system.SysRemoteUserPasswordDto;
import com.yintu.rixing.dto.system.SysRemoteUserQueryDto;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.system.*;
import com.yintu.rixing.util.AssertUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统远程用户表 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2021-01-28
 */
@Service
public class SysRemoteUserServiceImpl extends ServiceImpl<SysRemoteUserMapper, SysRemoteUser> implements ISysRemoteUserService {

    @Override
    public void save(SysRemoteUserFormDto sysRemoteUserFormDto) {
        String password = sysRemoteUserFormDto.getPassword();
        String certificateNumber = sysRemoteUserFormDto.getCertificateNumber();
        if (StrUtil.isEmpty(password))
            throw new BaseRuntimeException("密码不能为空");
        //判断证件号码是否重复
        List<Integer> ids = this.listByCertificateNumber(certificateNumber);
        if (!ids.isEmpty())
            throw new BaseRuntimeException("证件号码不能重复");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        sysRemoteUserFormDto.setPassword(passwordEncoder.encode(sysRemoteUserFormDto.getPassword()));
        SysRemoteUser sysRemoteUser = new SysRemoteUser();
        BeanUtil.copyProperties(sysRemoteUserFormDto, sysRemoteUser);
        this.save(sysRemoteUser);
    }

    @Override
    public void updateById(SysRemoteUserFormDto sysRemoteUserFormDto) {
        Integer id = sysRemoteUserFormDto.getId();
        String certificateNumber = sysRemoteUserFormDto.getCertificateNumber();
        SysRemoteUser sysRemoteUser = this.getById(id);
        if (sysRemoteUser != null) {
            //判断证件号码是否重复
            List<Integer> ids = this.listByCertificateNumber(certificateNumber);
            if (!ids.isEmpty() && !ids.get(0).equals(id))
                throw new BaseRuntimeException("证件号码不能重复");
            //判断用户名是否重复
            BeanUtil.copyProperties(sysRemoteUserFormDto, sysRemoteUser);
            this.updateById(sysRemoteUser);
        }
    }

    @Override
    public void resetPassword(SysRemoteUserPasswordDto sysRemoteUserPasswordDto) {
        Integer id = sysRemoteUserPasswordDto.getId();
        String oldPassword = sysRemoteUserPasswordDto.getOldPassword();
        String newPassword = sysRemoteUserPasswordDto.getNewPassword();
        if (oldPassword != null && !oldPassword.isEmpty() && newPassword != null && !newPassword.isEmpty()) {
            SysRemoteUser sysRemoteUser = this.getById(id);
            if (sysRemoteUser == null)
                throw new BaseRuntimeException("当前用户不存在");
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (!passwordEncoder.matches(oldPassword, sysRemoteUser.getPassword()))
                throw new BaseRuntimeException("旧密码错误");
            sysRemoteUser.setPassword(passwordEncoder.encode(newPassword));
            this.updateById(sysRemoteUser);
        }
    }

    @Override
    public List<Integer> listByCertificateNumber(String certificateNumber) {
        if (StrUtil.isEmpty(certificateNumber))
            throw new BaseRuntimeException("证件号码不能为空");
        QueryWrapper<SysRemoteUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .select(SysRemoteUser::getId)
                .eq(SysRemoteUser::getCertificateNumber, certificateNumber);
        return this.listObjs(queryWrapper, id -> (Integer) id);
    }

    @Override
    public Page<SysRemoteUser> page(SysRemoteUserQueryDto sysRemoteUserQueryDto) {
        Integer num = sysRemoteUserQueryDto.getNum();
        Integer size = sysRemoteUserQueryDto.getSize();
        String username = sysRemoteUserQueryDto.getUsername();
        Short searchIdentityId = sysRemoteUserQueryDto.getSearchIdentityId();
        String certificateNumber = sysRemoteUserQueryDto.getCertificateNumber();
        String phoneNum = sysRemoteUserQueryDto.getPhoneNum();
        QueryWrapper<SysRemoteUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(SysRemoteUser::getUsername, username == null ? "" : username);
        if (searchIdentityId != null)
            queryWrapper.lambda().eq(SysRemoteUser::getSearchIdentityId, searchIdentityId);
        if (StrUtil.isNotEmpty(certificateNumber))
            queryWrapper.lambda().eq(SysRemoteUser::getCertificateNumber, certificateNumber);
        if (StrUtil.isNotEmpty(phoneNum))
            queryWrapper.lambda().eq(SysRemoteUser::getPhoneNum, phoneNum);
        queryWrapper.orderByDesc("id");
        return this.page(new Page<>(num, size), queryWrapper);
    }

    @Override
    public SysRemoteUser login(String certificateNumber, String password) {
        AssertUtil.notEmpty(certificateNumber, "证件号码不能为空");
        AssertUtil.notEmpty(password, "密码不能为空");
        SysRemoteUser sysRemoteUser = this.getByCertificateNumber(certificateNumber);
        if (sysRemoteUser != null) {
            String p = sysRemoteUser.getPassword();
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(password, p))
                return sysRemoteUser;
        }
        return null;
    }

    @Override
    public SysRemoteUser getByCertificateNumber(String certificateNumber) {
        AssertUtil.notEmpty(certificateNumber, "证件号码不能为空");
        QueryWrapper<SysRemoteUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysRemoteUser::getCertificateNumber, certificateNumber);
        return this.getOne(queryWrapper);
    }
}
