package com.yintu.rixing.system.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yintu.rixing.system.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author mlf
 * @since 2020-12-25
 */
@Service
public class SysBorrowauditServiceImpl extends ServiceImpl<SysBorrowauditMapper, SysBorrowaudit> implements ISysBorrowauditService {

    @Autowired
    SysBorrowauditMapper sysBorrowauditMapper;


    @Autowired
    SysUserMapper sysUserMapper;


    @Autowired
    SysDepartmentMapper departmentMapper;

    /**
     * 待审批 已审批
     *
     * @param id
     * @return
     */
    @Override
    public List<SysBorrowaudit> findById(Integer id) {
        QueryWrapper qw = new QueryWrapper();
        if (id == 0) {
            qw.eq("status", "待审批");
        } else {
            qw.eq("status", "已审批");
        }
        List list = sysBorrowauditMapper.selectList(qw);

        return list;
    }

    /**
     * 查看个人信息
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> chakan(String borrower) {
        return getBorrower(borrower);
    }

    /**
     * 点击借阅人
     *
     * @param borrower
     * @return
     */
    @Override
    public Map<String, Object> djborrower(String borrower) {

        SysBorrowaudit sysBorrowaudit = sysBorrowauditMapper.findByName(borrower);
        Date applicationdate = sysBorrowaudit.getApplicationdate();//申请日期
        Date returndate = sysBorrowaudit.getReturndate();//归还日期
        //查询相差多少太难
        int i = daysBetween(applicationdate, returndate);

        Map<String, Object> map = getBorrower(borrower);
        map.put("purpose",sysBorrowaudit.getPurpose());//归还日期
        map.put("returndate",sysBorrowaudit.getReturndate());//归还日期
        map.put("date",i);//天数
        map.put("biezhu","备注");//备注


        return map;
    }


    //公共方法

    public Map<String, Object> getBorrower(String borrower) {
        //根据用户名查询id
        SysUser sysUser = sysUserMapper.findById(borrower);
        Map<String, Object> map = new HashMap<>();

        //查询所在部门的单位
        SysDepartment sysDepartment = departmentMapper.findById(sysUser.getId());
        if (sysDepartment == null) {
            map.put("bumendanwen", "");//部门单位

        } else {
            String name = sysDepartment.getName();
            map.put("bumendanwen", name);//部门单位
        }
        //借阅人
        map.put("borrower", borrower); //借阅人
        map.put("certificateType", sysUser.getCertificateType());//证件类型;
        map.put("certificateNumber", sysUser.getCertificateNumber());////证件号码
        map.put("phone", sysUser.getPhone());//联系电话
        map.put("create_time", sysUser.getCreateTime());
//        map.put("phone", phone); //档案类型、暂定


        return map;
    }
    /**
     * 计算两个日期之间相差的天数
     */
    public static int daysBetween(Date sendDate, Date startDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sendDate = sdf.parse(sdf.format(sendDate));
            startDate = sdf.parse(sdf.format(startDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(sendDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(startDate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }


}
