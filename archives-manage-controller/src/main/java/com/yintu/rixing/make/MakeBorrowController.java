package com.yintu.rixing.make;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.notification.NotNoticemessage;
import com.yintu.rixing.util.ResponseDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 利用中心的借阅申请表 前端控制器
 * </p>
 *
 * @author Mr.liu
 * @since 2021-01-11
 */
@RestController
@RequestMapping("/make/BorrowAll")
@Api("通知公告")
public class MakeBorrowController {
    @Autowired
    private IMakeBorrowService iMakeBorrowService;


    @PostMapping("/add")
    @Log(level = EnumLogLevel.INFO, module = "借阅申请", context = "添加新的借阅申请信息")
    @ApiOperation(value = "添加新的借阅申请信息", notes = "添加新的借阅申请信息")
    public Map<String,Object>add(MakeBorrow makeBorrow){
        iMakeBorrowService.save(makeBorrow);
        return ResponseDataUtil.ok("新增借阅申请信息成功");
    }

    @DeleteMapping("/{ids}")
    @Log(level = EnumLogLevel.INFO, module = "借阅申请", context = "根据id删除借阅申请信息")
    @ApiOperation(value = "删除借阅申请",  notes = "删除借阅申请")
    @ApiImplicitParam(name = "ids", value = "主键id集", required = true ,paramType="path")
    public Map<String, Object> remove(@PathVariable Set<Integer> ids) {
        if (iMakeBorrowService.removeByIds(ids)){
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
            @ApiImplicitParam(name = "name", value = "姓名"),
            @ApiImplicitParam(name = "certificateNumber", value = "证件号码")
    })
    public Map<String, Object> findElectronicBorrowDatas(@RequestParam Integer num, @RequestParam Integer size, String name,String certificateNumber) {
        QueryWrapper<MakeBorrow> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(MakeBorrow::getName,name==null? "":name);
        queryWrapper.lambda().like(MakeBorrow::getCertificateNumber,certificateNumber==null? "":certificateNumber);
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("borrow_type","1");
        Page<MakeBorrow> page = iMakeBorrowService.page(new Page<>(num, size), queryWrapper);
        return ResponseDataUtil.ok("查询借阅申请的电子借阅信息列表成功", page);
    }

    @Log(level = EnumLogLevel.DEBUG, module = "借阅申请", context = "查询借阅申请的实体借阅信息列表")
    @GetMapping("/findEntityBorrowDatas")
    @ApiOperation(value = "查询借阅申请的电子借阅信息列表", notes = " 查询借阅申请的实体借阅信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "10"),
            @ApiImplicitParam(name = "name", value = "姓名"),
            @ApiImplicitParam(name = "certificateNumber", value = "证件号码")
    })
    public Map<String, Object> findEntityBorrowDatas(@RequestParam Integer num, @RequestParam Integer size, String name,String certificateNumber) {
        QueryWrapper<MakeBorrow> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(MakeBorrow::getName,name==null? "":name);
        queryWrapper.lambda().like(MakeBorrow::getCertificateNumber,certificateNumber==null? "":certificateNumber);
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("borrow_type","1");
        Page<MakeBorrow> page = iMakeBorrowService.page(new Page<>(num, size), queryWrapper);
        return ResponseDataUtil.ok("查询借阅申请的实体借阅信息列表成功", page);
    }


}
