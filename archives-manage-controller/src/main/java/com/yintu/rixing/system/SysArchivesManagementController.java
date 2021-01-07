package com.yintu.rixing.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResponseDataUtil;
import com.yintu.rixing.util.ResultDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/system/archives")
@Api(tags = "模板字段信息接口")
@ApiIgnore
public class SysArchivesManagementController extends Authenticator {

    @Autowired
    ISysArchivesService iSysArchivesService;

    @Log(level = EnumLogLevel.INFO, module = "系统管理", context = "添加字段信息")
    @PostMapping("/add")
    @ApiOperation(value = "添加字段信息", notes = "添加字段信息")
    public ResultDataUtil<Object> add(@Validated SysArchives sysArchives) {
        boolean save = iSysArchivesService.save(sysArchives);
        if (save) {
            return ResultDataUtil.ok("添加字段信息成功");
        }
        return ResultDataUtil.error("添加字段信息失败");
    }

    @Log(level = EnumLogLevel.INFO, module = "系统管理", context = "删除字段信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除字段信息", notes = "删除字段信息")
    @ApiImplicitParam(name = "ids", value = "主键id集", required = true)
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids) {
        iSysArchivesService.removeByIds(ids);
        return ResultDataUtil.ok("删除档案字段信息成功");
    }


    @Log(level = EnumLogLevel.INFO, module = "系统管理", context = "修改信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改信息", notes = "修改信息")
    @ApiImplicitParam(name = "id", value = "主键id", required = true)
    public ResultDataUtil<Object> edit(@PathVariable Integer id, @Validated SysArchives entity) {
        iSysArchivesService.updateById(entity);
        return ResultDataUtil.ok("修改档案字段信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "系统管理", context = "查询模板信息")
    @GetMapping
    @ApiOperation(value = "查询模板信息", notes = "查询模板信息")
    public ResultDataUtil<SysArchives> findById(@PathVariable Integer id) {
        SysArchives byId = iSysArchivesService.getById(id);
        return ResultDataUtil.ok("查询成功", byId);
    }

    @Log(level = EnumLogLevel.INFO, module = "档案库管理", context = "查询档案库信息列表")
    @GetMapping("/findpage")
    @ApiOperation(value = "查询档案库信息列表", notes = "查询档案库信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "10"),
            @ApiImplicitParam(name = "chinesename", value = "中文名称")
    })

    public Map<String, Object> findPage(@RequestParam Integer num, @RequestParam Integer size, String chinesename) {
        QueryWrapper<SysArchives> qw = new QueryWrapper<>();
        qw.lambda()
                .select(SysArchives.class, tableFieldInfo -> !"".equals(tableFieldInfo.getColumn()))
                .like(SysArchives::getChinesename, chinesename == null ? "" : chinesename);
        Page<SysArchives> page = iSysArchivesService.page(new Page<>(num, size), qw);
        qw.orderByDesc("id");
        return ResponseDataUtil.ok("查询档案信息列表成功", page);
    }
}

