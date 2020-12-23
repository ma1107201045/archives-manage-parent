package com.yintu.rixing.system;


import com.baomidou.mybatisplus.extension.api.R;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.base.BaseController;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResponseDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2020-12-18
 */
@RestController
@RequestMapping("/system/syslibrary")
@Api(tags = "档案库管理接口")
public class SysLibraryController implements BaseController<SysLibrary, Integer> {

    @Autowired
    ISysLibraryService iSysLibraryService;

    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "新增下级信息")
    @PostMapping("/addxj")
    @ApiOperation(value = "新增下级信息", notes = "新增下级信息")
    public Map<String, Object> addXJ(@Param("id") Integer id, @Validated SysLibrary entity) {
        SysLibrary sl = iSysLibraryService.getById(id);
        entity.setPid(id);
        entity.setLibraryclasses(sl.getLibraryclasses());
        entity.setLibraryname(entity.getLibraryname());
        entity.setOrdernumber(sl.getOrdernumber());
        entity.setDescribee(entity.getDescribee());
        boolean save = iSysLibraryService.save(entity);
        if (save) {
            return ResponseDataUtil.ok("添加成功");
        }
        return ResponseDataUtil.error("添加失败");
    }

    @Override
    public Map<String, Object> add(@Validated SysLibrary entity) {
        iSysLibraryService.save(entity);
        return ResponseDataUtil.ok("添加成功");
    }

    @Log(level = EnumLogLevel.ERROR, module = "系统管理", description = "删除档案信息")
    @DeleteMapping("/del/{id}")
    @ApiOperation(value = "删除档案信息", notes = "删除档案信息")
    @Override
    public Map<String, Object> remove(@PathVariable("id") Integer id) {

        iSysLibraryService.removeById(id);

        return ResponseDataUtil.ok("删除成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "系统管理", description = "编辑档案信息")
    @PostMapping("/edit")
    @ApiOperation(value = "编辑档案信息", notes = "编辑档案信息")
    @Override
    public Map<String, Object> edit(@Param("id") Integer id, SysLibrary entity) {

        System.out.println("dlslkjl"+entity);

        iSysLibraryService.updateById(entity);
        return ResponseDataUtil.ok("修改成功");


    }

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询单条档案及下级信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询单条档案及下级信息", notes = "查询单条档案及下级信息")
    @Override
    @ApiImplicitParam(name = "id")
    public Map<String, Object> findById(@Param("id") Integer id) {
        List<Map<String, Object>> byId = iSysLibraryService.findById(id);
        return ResponseDataUtil.ok("成功", byId);
    }
}
