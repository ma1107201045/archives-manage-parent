package com.yintu.rixing.make;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.notification.NotNoticemessage;
import com.yintu.rixing.util.ResponseDataUtil;
import com.yintu.rixing.util.ResultDataUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author Mr.liu
 * @Date 2021/2/5 18:09
 * @Version 1.0
 * 需求:利用目的
 */
@RestController
@RequestMapping("/make/make-purpose")
public class MakeBorrowPurposeController {
    @Autowired
    private IMakeBorrowPurposeService iMakeBorrowPurposeService;

    @PostMapping("/add")
    @Log(level = EnumLogLevel.INFO, module = "利用目的", context = "添加新的利用目的信息")
    @ApiOperation(value = "添加新的利用目的信息", notes = "添加新的利用目的信息")
    public Map<String, Object> add(MakeBorrowPurpose makeBorrowPurpose) {
        iMakeBorrowPurposeService.save(makeBorrowPurpose);
        return ResponseDataUtil.ok("新增利用目的信息成功");
    }

    @DeleteMapping("/{ids}")
    @Log(level = EnumLogLevel.INFO, module = "利用目的", context = "根据id删除利用目的信息")
    @ApiOperation(value = "删除利用目的", notes = "删除利用目的")
    @ApiImplicitParam(name = "ids", value = "主键id集", required = true, paramType = "path")
    public Map<String, Object> remove(@PathVariable Set<Integer> ids) {
        if (iMakeBorrowPurposeService.removeByIds(ids)) {
            return ResponseDataUtil.ok("删除利用目的信息成功");
        }
        return ResponseDataUtil.error("删除利用目的信息失败");
    }

    @PutMapping("/{id}")
    @Log(level = EnumLogLevel.INFO, module = "利用目的", context = "根据id修改利用目的信息")
    @ApiOperation(value = "修改利用目的信息", notes = "修改利用目的信息")
    @ApiImplicitParam(name = "id", value = "主键id", required = true)
    public Map<String, Object> edit(Integer id, MakeBorrowPurpose makeBorrowPurpose) {
        iMakeBorrowPurposeService.updateById(makeBorrowPurpose);
        return ResponseDataUtil.ok("修改利用目的成功");
    }

    @Log(level = EnumLogLevel.DEBUG, module = "利用目的", context = "分页查询借阅申请的利用目的信息列表")
    @GetMapping("/find")
    @ApiOperation(value = "分页查询借阅申请的利用目的信息列表", notes = " 分页查询借阅申请的利用目的信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "10")
    })
    public ResultDataUtil<Object>find(@RequestParam Integer num, @RequestParam Integer size){
        QueryWrapper<MakeBorrowPurpose> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        Page<MakeBorrowPurpose> page = iMakeBorrowPurposeService.page(new Page<>(num, size), queryWrapper);
        return ResultDataUtil.ok("分页查询借阅申请的利用目的信息列表成功", page);
    }

    @Log(level = EnumLogLevel.DEBUG, module = "利用目的", context = "查询借阅申请的利用目的信息列表")
    @GetMapping("/findAll")
    @ApiOperation(value = "查询借阅申请的利用目的信息列表", notes = " 查询借阅申请的利用目的信息列表")
    public ResultDataUtil<Object>findAll(){
        List<MakeBorrowPurpose> makeBorrowPurposes = iMakeBorrowPurposeService.list();
        return ResultDataUtil.ok("查询借阅申请的利用目的信息列表成功", makeBorrowPurposes);
    }

}
