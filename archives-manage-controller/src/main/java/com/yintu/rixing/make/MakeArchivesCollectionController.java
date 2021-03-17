package com.yintu.rixing.make;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.pojo.MakeArchivesSearchPojo;
import com.yintu.rixing.system.SysApprovalProcess;
import com.yintu.rixing.util.ResponseDataUtil;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.vo.make.MakeArchivesSearchVo;
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
 * 档案收藏表 前端控制器
 * </p>
 *
 * @author Mr.liu
 * @since 2021-03-16
 */
@RestController
@RequestMapping("/Collection/make-archives-collection")
public class MakeArchivesCollectionController extends Authenticator {
    @Autowired
    private IMakeArchivesCollectionService iMakeArchivesCollectionService;
    @Autowired
    private IMakeArchivesSearchService iMakeArchivesSearchService;
    //新增收藏
    @PostMapping("/add")
    @Log(level = EnumLogLevel.INFO, module = "档案检索", context = "收藏档案")
    @ApiOperation(value = "收藏档案", notes = "收藏档案")
    public Map<String, Object> add(MakeArchivesCollection makeArchivesCollection) {
        makeArchivesCollection.setUserid(this.getLoginUserId());
        iMakeArchivesCollectionService.save(makeArchivesCollection);
        return ResponseDataUtil.ok("收藏档案成功");
    }

    //取消收藏
    @DeleteMapping("/{ids}")
    @Log(level = EnumLogLevel.INFO, module = "档案检索", context = "取消收藏")
    @ApiOperation(value = "取消收藏", notes = "取消收藏")
    @ApiImplicitParam(name = "ids", value = "主键id集", required = true, paramType = "path")
    public Map<String, Object> remove(@PathVariable Set<Integer> ids) {
        if (iMakeArchivesCollectionService.removeByIds(ids)) {
            return ResponseDataUtil.ok("取消收藏成功");
        }
        return ResponseDataUtil.error("取消收藏失败");
    }

    //初始化我的收藏
    @Log(level = EnumLogLevel.TRACE, module = "档案检索", context = "查询我的收藏")
    @GetMapping
    @ApiOperation(value = "查询我的收藏", notes = "查询我的收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "10")
    })
    public ResultDataUtil<Page<MakeArchivesSearchVo>> findPage(Integer num, Integer size) {
        Integer userId = this.getLoginUserId();
        Page<MakeArchivesSearchVo> makeArchivesSearchVos = iMakeArchivesCollectionService.findMyCollection(userId,num,size);
        return ResultDataUtil.ok("查询我的收藏成功", makeArchivesSearchVos);
    }

    //根据库id 表id 搜索内容 查询对应的文件
    @Log(level = EnumLogLevel.TRACE, module = "档案检索", context = "查询文件信息列表")
    @GetMapping("/searchArchivesFileByIds")
    @ApiOperation(value = "查询文件信息列表", notes = "查询文件信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "页码", required = true, defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "页数", required = true, defaultValue = "10"),
            @ApiImplicitParam(name = "archivesLibId", value = "库id"),
            @ApiImplicitParam(name = "archivesDirectoryId", value = "表id")
    })
    public ResultDataUtil<Page<MakeArchivesSearchPojo>> searchArchivesFileByIds(@RequestParam Integer num, @RequestParam Integer size,
                                                                                Integer archivesLibId, Integer archivesDirectoryId) {
        Page<MakeArchivesSearchPojo> makeArchivesSearchElectronicVoPage = iMakeArchivesSearchService.searchArchivesFileByIds(num, size, archivesDirectoryId,archivesLibId);
        return ResultDataUtil.ok("查询文件信息列表成功", makeArchivesSearchElectronicVoPage);
    }






}
