package com.yintu.rixing.make;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.system.ISysUserService;
import com.yintu.rixing.system.SysUser;
import com.yintu.rixing.util.ResponseDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 利用中心的借阅申请表 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2021-02-03
 */
@RestController
@RequestMapping("/make/make-borrow")
public class MakeBorrowController {

    @Autowired
    private IMakeBorrowService iMakeBorrowService;
    @Autowired
    private ISysUserService iSysUserService;


    @PostMapping("/add")
    @Log(level = EnumLogLevel.INFO, module = "借阅申请", context = "添加新的借阅申请信息")
    @ApiOperation(value = "添加新的借阅申请信息", notes = "添加新的借阅申请信息")
    public Map<String, Object> add(MakeBorrow makeBorrow) {
        iMakeBorrowService.save(makeBorrow);
        return ResponseDataUtil.ok("新增借阅申请信息成功");
    }

    @DeleteMapping("/{ids}")
    @Log(level = EnumLogLevel.INFO, module = "借阅申请", context = "根据id删除借阅申请信息")
    @ApiOperation(value = "删除借阅申请", notes = "删除借阅申请")
    @ApiImplicitParam(name = "ids", value = "主键id集", required = true, paramType = "path")
    public Map<String, Object> remove(@PathVariable Set<Integer> ids) {
        if (iMakeBorrowService.removeByIds(ids)) {
            return ResponseDataUtil.ok("删除借阅申请信息成功");
        }
        return ResponseDataUtil.error("删除借阅申请信息失败");
    }

    @PutMapping("/{id}")
    @Log(level = EnumLogLevel.INFO, module = "借阅申请", context = "根据id修改借阅申请信息")
    @ApiOperation(value = "修改借阅申请信息", notes = "修改借阅申请信息")
    @ApiImplicitParam(name = "id", value = "主键id", required = true)
    public Map<String, Object> edit(Integer id, MakeBorrow makeBorrow) {
        iMakeBorrowService.updateById(makeBorrow);
        return ResponseDataUtil.ok("修改借阅申请成功");
    }

    @Log(level = EnumLogLevel.DEBUG, module = "借阅申请", context = "查询借阅申请的电子借阅信息列表")
    @GetMapping("/findElectronicBorrowDatas")
    @ApiOperation(value = "查询借阅申请的电子借阅信息列表", notes = " 查询借阅申请的电子借阅信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "10"),
            @ApiImplicitParam(name = "name", value = "姓名")
    })
    public Map<String, Object> findElectronicBorrowDatas(@RequestParam Integer num, @RequestParam Integer size, String name) {
        if (name!=null){
            QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().like(SysUser::getNickname, name == null ? "" : name);
            List<SysUser> sysUserList = iSysUserService.list(queryWrapper);
            if (sysUserList.size()==0){
                return ResponseDataUtil.ok("查询借阅申请的电子借阅信息列表成功", null);
            }else {
                List<Integer>userids=new ArrayList<>();
                for (SysUser sysUser : sysUserList) {
                    Integer id = sysUser.getId();
                    userids.add(id);
                }
                QueryWrapper<MakeBorrow> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.in("user_id",userids);
                queryWrapper1.eq("borrow_type", "1");
                queryWrapper1.orderByDesc("id");
                Page<MakeBorrow> page = iMakeBorrowService.page(new Page<>(num, size),queryWrapper1);
                return ResponseDataUtil.ok("查询借阅申请的电子借阅信息列表成功", page);
            }
        }else {
            QueryWrapper<MakeBorrow> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("borrow_type", "1");
            queryWrapper1.orderByDesc("id");
            Page<MakeBorrow> page = iMakeBorrowService.page(new Page<>(num, size),queryWrapper1);
            for (MakeBorrow record : page.getRecords()) {
                Integer userId = record.getUserId();
                SysUser sysUser = iSysUserService.getById(userId);
                record.setSysUser(sysUser);
            }
            return ResponseDataUtil.ok("查询借阅申请的电子借阅信息列表成功", page);
        }

    }

    @Log(level = EnumLogLevel.DEBUG, module = "借阅申请", context = "查询借阅申请的实体借阅信息列表")
    @GetMapping("/findEntityBorrowDatas")
    @ApiOperation(value = "查询借阅申请的实体借阅信息列表", notes = " 查询借阅申请的实体借阅信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "10"),
            @ApiImplicitParam(name = "name", value = "姓名")
    })
    public Map<String, Object> findEntityBorrowDatas(@RequestParam Integer num, @RequestParam Integer size, String name) {
        if (name!=null){
            QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().like(SysUser::getNickname, name == null ? "" : name);
            List<SysUser> sysUserList = iSysUserService.list(queryWrapper);
            if (sysUserList.size()==0){
                return ResponseDataUtil.ok("查询借阅申请的实体借阅信息列表成功", null);
            }else {
                List<Integer>userids=new ArrayList<>();
                for (SysUser sysUser : sysUserList) {
                    Integer id = sysUser.getId();
                    userids.add(id);
                }
                QueryWrapper<MakeBorrow> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.in("user_id",userids);
                queryWrapper1.eq("borrow_type", "2");
                queryWrapper1.orderByDesc("id");
                Page<MakeBorrow> page = iMakeBorrowService.page(new Page<>(num, size),queryWrapper1);
                return ResponseDataUtil.ok("查询借阅申请的实体借阅信息列表成功", page);
            }
        }else {
            QueryWrapper<MakeBorrow> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("borrow_type", "2");
            queryWrapper1.orderByDesc("id");
            Page<MakeBorrow> page = iMakeBorrowService.page(new Page<>(num, size),queryWrapper1);
            for (MakeBorrow record : page.getRecords()) {
                Integer userId = record.getUserId();
                SysUser sysUser = iSysUserService.getById(userId);
                record.setSysUser(sysUser);
            }
            return ResponseDataUtil.ok("查询借阅申请的实体借阅信息列表成功", page);
        }
    }
}
