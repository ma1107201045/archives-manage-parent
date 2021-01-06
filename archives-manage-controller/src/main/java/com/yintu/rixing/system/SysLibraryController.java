package com.yintu.rixing.system;


import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResponseDataUtil;
import com.yintu.rixing.util.ResultDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
@ApiIgnore
public class SysLibraryController extends Authenticator {

    @Autowired
    ISysLibraryService iSysLibraryService;


    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "新增下级信息")
    @PostMapping("/addxj")
    @ApiOperation(value = "新增下级信息", notes = "新增下级信息")
    public ResultDataUtil<Object> addXJ(@Param("id") Integer id, @Validated SysLibrary entity) {

        SysLibrary sl = iSysLibraryService.getById(id);
        entity.setPid(id);
        entity.setLibraryclasses(sl.getLibraryclasses());
        entity.setLibraryname(entity.getLibraryname());
        entity.setOrdernumber(sl.getOrdernumber());
        entity.setDescribee(entity.getDescribee());
        boolean save = iSysLibraryService.save(entity);
        if (save) {
            return ResultDataUtil.ok("添加成功");
        }
        return ResultDataUtil.error("添加失败");
    }

    @PostMapping("/addmulu")
    public ResultDataUtil<Object> add(@Validated SysLibrary entity) {
        iSysLibraryService.save(entity);
        return ResultDataUtil.ok("添加成功");
    }

    @Log(level = EnumLogLevel.ERROR, module = "系统管理", description = "删除档案信息")
    @DeleteMapping("/del/{ids}")
    @ApiOperation(value = "删除档案信息", notes = "删除档案信息")
    public ResultDataUtil<Object> remove(@PathVariable("ids") Set<Integer> ids) {
        iSysLibraryService.removeByIds(ids);
        return ResultDataUtil.ok("删除成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "系统管理", description = "编辑档案信息")
    @PostMapping("/edit")
    @ApiOperation(value = "编辑档案信息", notes = "编辑档案信息")
    public ResultDataUtil<Object> edit(@Param("id") Integer id, SysLibrary entity) {

        System.out.println("dlslkjl" + entity);

        iSysLibraryService.updateById(entity);
        return ResultDataUtil.ok("修改成功");


    }

    @Log(level = EnumLogLevel.DEBUG, module = "系统管理", description = "查询单条档案及下级信息")
    @GetMapping("/find")
    @ApiOperation(value = "查询单条档案及下级信息", notes = "查询单条档案及下级信息")
    @ApiImplicitParam(name = "id")
    public ResultDataUtil<Map<String, Object>> findById(@Param("id") Integer id) {
        List<Map<String, Object>> byId = iSysLibraryService.findById(id);
        return ResultDataUtil.ok("成功", byId.get(0));
    }

    /**
     * 可以根据库类别，库名称组合查询
     *
     * @param
     * @return
     */

    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "根据名称，类别查询")
    @GetMapping("/findByname")
    @ApiOperation(value = "根据名称，类别查询", notes = "根据名称，类别查询")
    public Map<String, Object> findBylnameAndlclasses(@Param("libraryname") String libraryname,
                                                      @Param("libraryclasses") String libraryclasses) {
        List<SysLibrary> list = iSysLibraryService.findBylnameAndlclasses(libraryname, libraryclasses);
        return ResponseDataUtil.ok("成功", list);

    }

    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "新增档案库")
    @PostMapping("/xzdak")
    @ApiOperation(value = "新增档案库", notes = "新增档案库")
    public Map<String, Object> yrmb(@Param("tepname") String tepname, @Param("id") Integer id, @Param("librname") String librname) {
        Map<String, Object> bytepname = iSysLibraryService.findBytepname(tepname, id, librname);
        return ResponseDataUtil.ok("成功", bytepname);

    }


    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "编辑模板字段信息")
    @PutMapping("/editField")
    @ApiOperation(value = "编辑模板字段信息", notes = "编辑模板字段信息")
    public Map<String, Object> editField(@Param("id") Integer id,
                                         @Param("laofield") String laofield,
                                         @Param("newfield") String newfield,
                                         @Param("type") String type,
                                         @Param("fieldlength") String fieldlength) {

        Integer b = iSysLibraryService.editfield(id, laofield, newfield, type, fieldlength);
        if (b == 0) {
            return ResponseDataUtil.ok("编辑成功");
        }
        return ResponseDataUtil.error("编辑失败");

    }

    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "添加模板字段信息")
    @ApiOperation(value = "添加模板字段信息", notes = "添加模板字段信息")
    @PostMapping("/addfield")
    public Map<String, Object> innertFleId(@Param("id") Integer id, @Validated SysTableMessge sysTableMessge) {
        return iSysLibraryService.innertFleId(id, sysTableMessge);
    }

    @Log(level = EnumLogLevel.INFO, module = "系统管理", description = "删除模板字段")
    @ApiOperation(value = "删除模板字段", notes = "删除模板字段")
    @DeleteMapping("/delfield")
    public Map<String, Object> delfield(@Param("id") Integer id, @Param("fieldName") String fieldName) {

        return iSysLibraryService.delfield(id, fieldName);

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
        return iSysLibraryService.xsfield(id, sfieldName, mfieldName, valuetype, fieldlength);

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
        return iSysLibraryService.xxfield(id, xfieldName, mfieldName, valuetype, fieldlength);

    }


}
