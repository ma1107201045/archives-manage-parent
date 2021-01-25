package com.yintu.rixing.data;

import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResultDataUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * <p>
 * 数据回收站 前端控制器
 * </p>
 *
 * @Author: mlf
 * @Date: 2021/1/18 10:18:27
 * @Version: 1.0
 */
@RestController
@RequestMapping("/data/data-recycle-bin")
@Api(tags = "回收站接口")
@ApiSort(4)
public class Data04RecycleBinController extends Authenticator {

    @Autowired
    private IDataRecycleBinService iDataRecycleBinService;

    @Log(level = EnumLogLevel.WARN, module = "数据中心", context = "恢复回收站信息")
    @PatchMapping("/{ids}")
    @ApiOperation(value = "恢复回收站信息", notes = "恢复回收站信息")
    @ApiOperationSupport(order = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", allowMultiple = true, dataType = "int", value = "主键id集", required = true, paramType = "path"),
            @ApiImplicitParam(name = "archivesLibraryId", dataType = "int", value = "档案库id", required = true, paramType = "query")
    })
    public ResultDataUtil<Object> regain(@PathVariable Set<Integer> ids, @RequestParam Integer archivesLibraryId) {
        iDataRecycleBinService.regainByIds(ids, archivesLibraryId);
        return ResultDataUtil.ok("恢复回收站信息成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "数据中心", context = "彻底删除回收站信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "彻底删除回收站信息", notes = "彻底删除回收站信息")
    @ApiOperationSupport(order = 2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", allowMultiple = true, dataType = "int", value = "主键id集", required = true, paramType = "path"),
            @ApiImplicitParam(name = "archivesLibraryId", dataType = "int", value = "档案库id", required = true, paramType = "query")
    })
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids, @RequestParam Integer archivesLibraryId) {
        iDataRecycleBinService.removeByIds(ids, archivesLibraryId);
        return ResultDataUtil.ok("彻底删除回收站信息成功");
    }

}
