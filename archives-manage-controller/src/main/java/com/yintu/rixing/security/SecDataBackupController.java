package com.yintu.rixing.security;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.dto.base.PageDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResultDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * <p>
 * 安全数据备份表 前端控制器
 * </p>
 *
 * @author mlf
 * @since 2021-01-13
 */
@RestController
@RequestMapping("/security/sec-data-backup")
@Api(tags = "数据备份接口")
@ApiSort(2)
public class SecDataBackupController extends Authenticator {

    @Autowired
    private ISecDataBackupService iSecDataBackupService;


    @Log(level = EnumLogLevel.DEBUG, module = "安全中心", context = "数据备份")
    @PostMapping
    @ApiOperation(value = "数据备份", notes = "数据备份", position = 1)
    public ResultDataUtil<Object> backup(HttpServletRequest request) {
        iSecDataBackupService.backup(request, this.getLoginUserId(), this.getLoginUserName(), this.getLoginTrueName());
        return ResultDataUtil.ok("数据备份成功");
    }

    @Log(level = EnumLogLevel.WARN, module = "安全中心", context = "删除数据备份信息")
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除数据备份信息", notes = "删除数据备份信息", position = 2)
    @ApiImplicitParam(name = "ids", allowMultiple = true, value = "主键id集", required = true, paramType = "path")
    public ResultDataUtil<Object> remove(@PathVariable Set<Integer> ids) {
        iSecDataBackupService.remove(ids);
        return ResultDataUtil.ok("删除数据备份信息成功");
    }

    @Log(level = EnumLogLevel.INFO, module = "安全中心", context = " 数据还原")
    @PutMapping("/{id}")
    @ApiOperation(value = "数据还原", notes = "数据还原", position = 3)
    @ApiImplicitParam(name = "id", dataType = "int", value = "主键id", required = true, paramType = "path")
    public ResultDataUtil<Object> restore(@PathVariable Integer id) {
        iSecDataBackupService.restore(id);
        return ResultDataUtil.ok("数据还原成功");
    }

    @Log(level = EnumLogLevel.TRACE, module = "安全中心", context = "查询数据备份列表信息")
    @GetMapping
    @ApiOperation(value = "查询数据备份列表信息", notes = " 查询数据备份列表信息", position = 4)
    public ResultDataUtil<Page<SecDataBackup>> findPage(@Validated PageDto pageDto) {
        Page<SecDataBackup> page = iSecDataBackupService.page(pageDto);
        return ResultDataUtil.ok("查询数据备份列表信息成功", page);
    }

}
