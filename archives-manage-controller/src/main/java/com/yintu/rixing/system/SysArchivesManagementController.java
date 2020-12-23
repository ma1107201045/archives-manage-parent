package com.yintu.rixing.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.base.BaseController;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResponseDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/archives")
@Api(tags = "档案库模板接口")
public class SysArchivesManagementController implements BaseController<SysArchives, Integer> {

    @Autowired
    ISysArchivesService iSysArchivesService;


    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "添加字段信息")
    @PostMapping("/add")
    @ApiOperation(value = "添加字段信息", notes = "添加字段信息")
    public Map<String, Object> add(@Validated SysArchives sysArchives) {
        boolean save = iSysArchivesService.save(sysArchives);
        if (save) {
            return ResponseDataUtil.ok("添加字段信息成功");
        }
        return ResponseDataUtil.error("添加字段信息失败");
    }

    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "删除字段信息")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除字段信息", notes = "删除字段信息")
    @ApiImplicitParam(name = "id", value = "主键id", required = true)
    public Map<String, Object> remove(@PathVariable Integer id) {
        iSysArchivesService.removeById(id);
        return ResponseDataUtil.ok("删除档案字段信息成功");
    }


    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "修改信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改信息", notes = "修改信息")
    @ApiImplicitParam(name = "id", value = "主键id", required = true)
    public Map<String, Object> edit(@PathVariable Integer id, @Validated SysArchives entity) {

        iSysArchivesService.updateById(entity);

        return ResponseDataUtil.ok("修改档案字段信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "查询模板信息")
    @GetMapping
    @ApiOperation(value = "查询模板信息", notes = "查询模板信息")
    public Map<String, Object> findById(@PathVariable Integer id) {
        SysArchives byId = iSysArchivesService.getById(id);
        return ResponseDataUtil.ok("查询成功", byId);
    }

    @Log(level = EnumLogLevel.INFO, module = "档案库管理", description = "查询档案库信息列表")
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

    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "查询模板信息")
    @GetMapping("/tablename")
    @ApiOperation(value = "根据表名查询表结构信息", notes = "根据表名查询表结构信息")
    @ApiImplicitParam(name = "tablename")
    public Map<String, Object> findByTableName(@Param("tablename") String tablename) {
        List<SysTableMessge> byTableName = iSysArchivesService.findByTableName(tablename);
        return ResponseDataUtil.ok("查询成功", byTableName);
    }


    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "查询模板信息")
    @PostMapping("/addfleid")
    @ApiOperation(value = "根据表名添加字段信息", notes = "根据表名添加字段信息")
    public Map<String, Object> addfleid(@Param("tablename") String tablename, SysArchives sysArchives) {
        boolean b = iSysArchivesService.innertFleId(tablename, sysArchives);
        if (b) {
            return ResponseDataUtil.ok("添加成功");
        }
        return ResponseDataUtil.error("添加失败");
    }

    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "查询模板信息")
    @DeleteMapping("/delfield")
    @ApiOperation(value = "删除字段信息", notes = "删除字段信息")
    public Map<String, Object> delfield(@Param("tablename") String tablename, @Param("standardfields") String standardfields) {
        boolean b   =iSysArchivesService.deleField(tablename,standardfields);
        if (b) {
            return ResponseDataUtil.ok("删除成功");
        }
        return ResponseDataUtil.error("删除失败");

    }

}

