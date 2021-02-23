package com.yintu.rixing.make;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.make.MakeArchivesSearchDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.notification.NotNoticemessage;
import com.yintu.rixing.system.SysDepartment;
import com.yintu.rixing.system.SysUser;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.vo.make.MakeArchivesSearchElectronicVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
public class MakeCompilationController extends Authenticator{

    @Autowired
    private IMakeCompilationService iMakeCompilationService;
    @Autowired
    private IMakeArchivesSearchService iMakeArchivesSearchService;
    @Autowired
    private IMakeCompilationAuditorService iMakeCompilationAuditorService;
    @Autowired
    private IMakeCompilationTreeService iMakeCompilationTreeService;


    //新增编研
    @PostMapping("/addCompilation")
    @Log(level = EnumLogLevel.DEBUG, module = "利用中心", context = "新增档案编研")
    @ApiOperation(value = "新增档案编研", notes = "新增档案编研")
    public ResultDataUtil<Object> addCompilation(MakeCompilation makeCompilation) {
        Integer userId = this.getLoginUserId();
        makeCompilation.setIsnotAudit(0);
        makeCompilation.setEditorid(userId);
        makeCompilation.setAuditStatus(0);
        iMakeCompilationService.save(makeCompilation);
        return ResultDataUtil.ok("新增档案编研成功");
    }

    //根据id  删除档案编研
    @DeleteMapping("/{ids}")
    @Log(level = EnumLogLevel.WARN, module = "利用中心", context = "根据id删除档案编研数据")
    @ApiOperation(value = "根据id删除档案编研数据", notes = "根据id删除档案编研数据")
    public ResultDataUtil<Object> delete(@PathVariable Set<Integer> ids) {
        iMakeCompilationService.removeByIds(ids);
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
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "10"),
            @ApiImplicitParam(name = "compilationTreeId", value = "树id")
    })
    public ResultDataUtil<Object> findAll(@RequestParam Integer num, @RequestParam Integer size,Integer compilationTreeId) {
        QueryWrapper<MakeCompilation> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (compilationTreeId!=null) {
            queryWrapper.eq("make_compilation_tree_id", compilationTreeId);
        }
        Page<MakeCompilation> page = iMakeCompilationService.page(new Page<>(num, size), queryWrapper);
        for (MakeCompilation record : page.getRecords()) {
            List<Integer> auditorIds=new ArrayList<>();
            QueryWrapper<MakeCompilationAuditor> queryWrapper1=new QueryWrapper<>();
            Integer id = record.getId();
            queryWrapper1.eq("make_compilation_id",id);
            queryWrapper1.eq("activate",1);
            List<MakeCompilationAuditor> list = iMakeCompilationAuditorService.list(queryWrapper1);
            for (MakeCompilationAuditor makeCompilationAuditor : list) {
                Integer auditorId = makeCompilationAuditor.getAuditorId();
                auditorIds.add(auditorId);
            }
            record.setAuditorIds(auditorIds);
            List<Integer> integerList = new ArrayList<>();
            Integer makeCompilationTreeId = record.getMakeCompilationTreeId();
            MakeCompilationTree makeCompilationTree = iMakeCompilationTreeService.getById(makeCompilationTreeId);
            Integer parentId = makeCompilationTree.getParentId();
            if (parentId == -1)
                integerList.add(makeCompilationTreeId);
            else
                integerList.add(makeCompilationTreeId);
            for (int i = 0; i < 30; i++) {
                if (parentId != -1) {
                    MakeCompilationTree makeCompilationTree1 = iMakeCompilationTreeService.getById(makeCompilationTreeId);
                    Integer parentId1 = makeCompilationTree1.getParentId();
                    if (parentId1 != -1) {
                        integerList.add(parentId1);
                        parentId = parentId1;
                        makeCompilationTreeId=parentId;
                    } else {
                        break;
                    }
                }
            }
            Arrays.sort(integerList.toArray(), Collections.reverseOrder());
            record.setParentIds(integerList);
        }
        return ResultDataUtil.ok("查询档案编研数据成功", page);
    }

    //根据条件查询电子库数据
    @Log(level = EnumLogLevel.TRACE, module = "利用中心", context = "根据条件查询电子档案数据信息列表")
    @GetMapping("/searchElectronicArchives")
    @ApiOperation(value = "根据条件查询电子档案数据信息列表", notes = "根据条件查询电子档案数据信息列表")
    public ResultDataUtil<Page<MakeArchivesSearchElectronicVo>> searchElectronic(@Validated MakeArchivesSearchDto makeArchivesSearchDto) {
        Page<MakeArchivesSearchElectronicVo> makeArchivesSearchElectronicVoPage = iMakeArchivesSearchService.listElectronicByKeyWord(makeArchivesSearchDto);
        return ResultDataUtil.ok("根据条件查询电子档案数据信息列表成功", makeArchivesSearchElectronicVoPage);
    }


    //查看编研成果
    @Log(level = EnumLogLevel.DEBUG, module = "利用中心", context = "查询档案编研成果数据列表")
    @GetMapping("/findAllResult")
    @ApiOperation(value = "查询档案编研成果数据列表", notes = " 查询档案编研成果数据列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "10"),
            @ApiImplicitParam(name = "compilationTreeId", value = "树id")
    })
    public ResultDataUtil<Object> findAllResult(@RequestParam Integer num, @RequestParam Integer size,Integer compilationTreeId) {
        QueryWrapper<MakeCompilation> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("audit_status",2);
        if (compilationTreeId!=null) {
            queryWrapper.eq("make_compilation_tree_id", compilationTreeId);
        }
        Page<MakeCompilation> page = iMakeCompilationService.page(new Page<>(num, size), queryWrapper);
        return ResultDataUtil.ok("查询档案编研成果数据成功", page);
    }


}
