package com.yintu.rixing.make;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.dto.make.MakeArchivesSearchDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.vo.make.MakeArchivesSearchElectronicVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 档案编研表 前端控制器
 * </p>
 *
 * @author Mr.liu
 * @since 2021-02-22
 */
@RestController
@RequestMapping("/MakeCompilation/make-compilation")
public class MakeCompilationController {

    @Autowired
    private IMakeCompilationService iMakeCompilationService;
    @Autowired
    private IMakeArchivesSearchService iMakeArchivesSearchService;


    //新增编研
    @PostMapping("/addCompilation")
    @Log(level = EnumLogLevel.DEBUG, module = "利用中心", context = "新增档案编研")
    @ApiOperation(value = "新增档案编研", notes = "新增档案编研")
    public ResultDataUtil<Object> addCompilation(MakeCompilation makeCompilation) {
        makeCompilation.setIsnotAudit(0);
        iMakeCompilationService.save(makeCompilation);
        return ResultDataUtil.ok("新增档案编研成功");
    }

    //根据id  删除档案编研
    @DeleteMapping("/{id}")
    @Log(level = EnumLogLevel.WARN, module = "利用中心", context = "根据id删除档案编研数据")
    @ApiOperation(value = "根据id删除档案编研数据", notes = "根据id删除档案编研数据")
    public ResultDataUtil<Object> delete(MakeCompilation makeCompilation) {
        iMakeCompilationService.removeById(makeCompilation);
        return ResultDataUtil.ok("删除档案编研数据成功");
    }

    //根据id编辑 编辑档案编研数据
    @PutMapping("/{id}")
    @Log(level = EnumLogLevel.INFO, module = "利用中心", context = "根据id编辑档案编研数据")
    @ApiOperation(value = "根据id编辑档案编研数据", notes = "根据id编辑档案编研数据")
    public ResultDataUtil<Object> edit(@PathVariable Integer id, MakeCompilation makeCompilation) {
        iMakeCompilationService.updateById(makeCompilation);
        return ResultDataUtil.ok("根据id编辑档案编研数据成功");
    }

    //初始化档案编研
    @Log(level = EnumLogLevel.DEBUG, module = "利用中心", context = "查询档案编研数据列表")
    @GetMapping("/findAll")
    @ApiOperation(value = "查询档案编研数据列表", notes = " 查询档案编研数据列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "10")
    })
    public ResultDataUtil<Object> findAll(@RequestParam Integer num, @RequestParam Integer size) {
        QueryWrapper<MakeCompilation> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        Page<MakeCompilation> page = iMakeCompilationService.page(new Page<>(num, size), queryWrapper);
        return ResultDataUtil.ok("查询档案编研数据成功", page);
    }

    //根据条件查询实体库数据
    @Log(level = EnumLogLevel.TRACE, module = "利用中心", context = "根据条件查询电子档案数据信息列表")
    @GetMapping("/searchElectronicArchives")
    @ApiOperation(value = "根据条件查询电子档案数据信息列表", notes = "根据条件查询电子档案数据信息列表")
    public ResultDataUtil<Page<MakeArchivesSearchElectronicVo>> searchElectronic(@Validated MakeArchivesSearchDto makeArchivesSearchDto) {
        Page<MakeArchivesSearchElectronicVo> makeArchivesSearchElectronicVoPage = iMakeArchivesSearchService.listElectronicByKeyWord(makeArchivesSearchDto);
        return ResultDataUtil.ok("根据条件查询电子档案数据信息列表成功", makeArchivesSearchElectronicVoPage);
    }


}
