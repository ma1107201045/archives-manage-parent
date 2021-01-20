package com.yintu.rixing.data;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.base.BaseController;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.base.PageDto;
import com.yintu.rixing.dto.data.DataArchivesLibraryFileFormDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResultDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * <p>
 * 数据档案库文件表 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2021-01-19
 */
@RestController
@RequestMapping("/data/data-archives-library-file")
@Api(tags = "档案文件接口")
@ApiSort(10)
public class DataArchivesLibraryFileController extends Authenticator implements BaseController<DataArchivesLibraryFileFormDto, PageDto, DataArchivesLibraryFile, Integer> {

    @Autowired
    private IDataArchivesLibraryFileService iDataArchivesLibraryFileService;

    @Log(level = EnumLogLevel.DEBUG, module = "数据中心", context = "添加档案文件信息")
    @PostMapping
    @ApiOperation(value = "添加档案文件信息", notes = "添加档案文件信息", position = 1)
    public ResultDataUtil<Object> add(@Validated DataArchivesLibraryFileFormDto formDto) {
        iDataArchivesLibraryFileService.save(formDto);
        return ResultDataUtil.ok("添加档案文件信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "数据中心", context = "删除档案文件信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除档案文件信息", notes = "删除档案文件信息", position = 2)
    @ApiImplicitParam(name = "ids", allowMultiple = true, value = "主键id集", required = true, paramType = "path")
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids) {
        iDataArchivesLibraryFileService.removeByIds(ids);
        return ResultDataUtil.ok("删除档案文件信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "数据中心", context = "修改档案文件信息")
    @PutMapping("/{id}")
    @ApiOperation(value = "修改档案文件信息", notes = "修改档案文件信息", position = 3)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> edit(@PathVariable Integer id, @Validated DataArchivesLibraryFileFormDto formDto) {
        iDataArchivesLibraryFileService.updateById(formDto);
        return ResultDataUtil.ok("修改档案文件信息成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询档案文件单条信息")
    @GetMapping("/{id}")
    @ApiOperation(value = "查询档案文件单条信息", notes = " 查询档案文件单条信息", position = 4)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<DataArchivesLibraryFile> findById(@PathVariable Integer id) {
        DataArchivesLibraryFile dataArchivesLibraryFile = iDataArchivesLibraryFileService.getById(id);
        return ResultDataUtil.ok("查询档案文件单条信息成功", dataArchivesLibraryFile);
    }

    @Log(level = EnumLogLevel.TRACE, module = "数据中心", context = "查询档案文件列表信息")
    @GetMapping
    @ApiOperation(value = "查询档案文件列表信息", notes = " 查询档案文件列表信息", position = 5)
    public ResultDataUtil<Page<DataArchivesLibraryFile>> findPage(@Validated PageDto queryDto) {
        Page<DataArchivesLibraryFile> page = iDataArchivesLibraryFileService.page(queryDto);
        return ResultDataUtil.ok("查询档案文件列表信息成功", page);
    }
}