package com.yintu.rixing.system;


import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.base.BaseController;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.system.impl.SysTemplateServiceImpl;
import com.yintu.rixing.util.ResponseDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2020-12-23
 */
@RestController
@RequestMapping("/system/systemplate")
@Api(tags = "模板库管理接口")
public class SysTemplateController implements BaseController<SysTemplate, Integer> {
    @Autowired
    ISysTemplateService iSysTemplateService;

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "添加模板")
    @ApiOperation(value = "添加模板", notes = "添加模板")
    @PostMapping("/add")
    public Map<String, Object> add(@Validated SysTemplate entity) {
        iSysTemplateService.save(entity);
        return ResponseDataUtil.ok("添加成功");
    }

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "删除模板信息列表")
    @ApiOperation(value = "删除模板", notes = "删除模板")
    @DeleteMapping("/del")
    public Map<String, Object> remove(@Param("ids") Set<Integer> ids) {
        iSysTemplateService.removeByIds(ids);
        return ResponseDataUtil.ok("删除成功");
    }

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "编辑模板信息")
    @PutMapping("/edit")
    @ApiOperation(value = "编辑模板信息", notes = "编辑模板信息")
    public Map<String, Object> edit(@Param("id") Integer id, @Validated SysTemplate entity) {
        iSysTemplateService.updateById(entity);
        return ResponseDataUtil.ok("编辑成功");
    }

    @GetMapping("/findById")
    @ApiOperation(value = "查询模板信息", notes = "查询模板信息")
    public Map<String, Object> findById(@Param("id") Integer id) {
        List<SysTableMessge> list = iSysTemplateService.findById(id);
        return ResponseDataUtil.ok("查询成功", list);
    }


    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "引入模板")
    @GetMapping("/yrmb")
    @ApiOperation(value = "引入模板", notes = "引入模板")
    public Map<String, Object> yrmb(@Param("id") Integer id) {
        List<SysArchives> list = iSysTemplateService.findByIdScrchives(id);
        return ResponseDataUtil.ok("查询成功", list);
    }

    /**
     * 根据名称查询所属的模板
     *
     * @param mantname
     * @return
     */
    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "根据模式名称查询模板")
    @ApiOperation(value = "根据模式名称查询模板", notes = "根据模式名称查询模板")
    @GetMapping("/findByname")
    public Map<String, Object> findBymanageentname(@RequestParam("mantname") String mantname) {

        List<SysTemplate> sysTemplates = iSysTemplateService.findBymanageentname(mantname);
        return ResponseDataUtil.ok("查询成功", sysTemplates);
    }

    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "添加模板字段信息")
    @ApiOperation(value = "添加模板字段信息", notes = "添加模板字段信息")
    @PostMapping("/addfield")
    public Map<String, Object> findBymanageentname(@Param("id") Integer id, @Validated SysTableMessge sysTableMessge) {
        Integer b = iSysTemplateService.innertFleId(id, sysTableMessge);
        if (b == 0) {
            return ResponseDataUtil.ok("添加成功");
        }
        return ResponseDataUtil.ok("添加失败");
    }

    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "编辑模板字段信息")
    @ApiOperation(value = "编辑模板字段信息", notes = "编辑模板字段信息")
    @PostMapping("/editfield")
    public Map<String, Object> editfield(@Param("id") Integer id,
                                         @Param("laofield") String laofield,
                                         @Param("newfield") String newfield,
                                         @Param("type") String type,
                                         @Param("fieldlength") String fieldlength) {

        Integer b = iSysTemplateService.editfield(id, laofield, newfield, type, fieldlength);
        if (b == 0) {
            return ResponseDataUtil.ok("编辑成功");
        }
        return ResponseDataUtil.ok("编辑失败");
    }

    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "删除模板字段信息")
    @ApiOperation(value = "删除模板字段信息", notes = "删除模板字段信息")
    @PostMapping("/delfield")
    public Map<String, Object> delfield(@Param("id") Integer id, @Param("field") String field) {

        boolean b = iSysTemplateService.deleField(id, field);
        if (b) {
            return ResponseDataUtil.ok("编辑成功");
        }
        return ResponseDataUtil.ok("编辑失败");
    }

    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "向上调整字段")
    @ApiOperation(value = "向上调整字段", notes = "向上调整字段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sfieldName", value = "上面的字段", required = true),
            @ApiImplicitParam(name = "mfieldName", value = "当前字段", required = true)
    })
    @GetMapping("/xsfield")
    public Map<String, Object> xsfield(@Param("id") Integer id,
                                       @Param("sfieldName") String sfieldName,
                                       @Param("mfieldName") String mfieldName,
                                       @Param("valuetype") String valuetype,
                                       @Param("fieldlength") String fieldlength
    ) {
        return iSysTemplateService.xsfield(id, sfieldName, mfieldName, valuetype, fieldlength);

    }

    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "向下调整字段")
    @ApiOperation(value = "向下调整字段", notes = "向下调整字段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "xfieldName", value = "下面的字段", required = true),
            @ApiImplicitParam(name = "mfieldName", value = "当前字段", required = true)
    })

    @GetMapping("/xxfield")
    public Map<String, Object> xxfield(@Param("id") Integer id,
                                       @Param("xfieldName") String xfieldName,
                                       @Param("mfieldName") String mfieldName,
                                       @Param("valuetype") String valuetype,
                                       @Param("fieldlength") String fieldlength
    ) {
        return iSysTemplateService.xxfield(id, xfieldName, mfieldName, valuetype, fieldlength);

    }


    @PostMapping("/addjd")
    @ApiOperation(value = "添加子节点", notes = "添加子节点")

    public Map<String, Object> addjd(@Param("id") Integer id, @Param("tepname") String tepname) {
        SysTemplate sysTemplate = new SysTemplate();
        sysTemplate.setPid(id);
        sysTemplate.setTepname(tepname);
        boolean save = iSysTemplateService.save(sysTemplate);
        if (save) {
            return ResponseDataUtil.ok("添加成功");
        }
        return ResponseDataUtil.ok("添加成功");
    }

    @ApiOperation(value = "删除子节点", notes = "删除子节点")
    @DeleteMapping("/deljd")
    public Map<String, Object> deljd(@Param("id") Integer id) {

        boolean b = iSysTemplateService.removeById(id);

        if (b) {
            return ResponseDataUtil.ok("删除成功");
        }
        return ResponseDataUtil.error("删除失败");
    }

    @ApiOperation(value = "编辑子节点", notes = "编辑子节点")
    @PutMapping("/uptejd")
    public Map<String, Object> uptejd(@Param("id") Integer id, @Param("tepname") String tepname) {
        SysTemplate sysTemplate = new SysTemplate();
        sysTemplate.setId(id);
        sysTemplate.setTepname(tepname);
        boolean b = iSysTemplateService.updateById(sysTemplate);
        if (b) {
            return ResponseDataUtil.ok("编辑成功");
        }
        return ResponseDataUtil.error("编辑失败");
    }


    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询单条目录及下级信息")
    @GetMapping("/findAlljd")
    @ApiOperation(value = "查询单条目录及下级信息", notes = "查询单条目录及下级信息")
    @ApiImplicitParam(name = "id")
    public Map<String, Object> findmulu(@Param("id") Integer id) {
        List<Map<String, Object>> byId = iSysTemplateService.findmulu(id);
        return ResponseDataUtil.ok("成功", byId);
    }

}