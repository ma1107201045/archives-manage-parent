package com.yintu.rixing.index;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.make.IMakeBorrowPurposeService;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.vo.index.InxLocalAndRemoteBorrowApplyAndInformVo;
import com.yintu.rixing.vo.index.InxSearchArchivesQuantityStatisticsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mlf
 * @Date: 2021/2/26 10:53:59
 * @Version: 1.0
 */
@RestController
@RequestMapping("/index/inx-local-and-remote-borrow-apply-and-inform")
@Api(tags = "本地借阅申请和远程借阅申请接口")
@ApiSupport(order = 3)
public class InxLocalAndRemoteBorrowApplyAndInformController {

    @Autowired
    private IInxLocalAndRemoteBorrowApplyAndInformService iInxLocalAndRemoteBorrowApplyAndInformService;

    @Log(level = EnumLogLevel.TRACE, module = "首页", context = "查询本地、远程申请借阅、通知公告列表信息")
    @GetMapping
    @ApiOperation(value = "查询本地、远程申请借阅、通知公告列表信息", notes = "查询本地、远程申请借阅、通知公告列表信息")
    @ApiOperationSupport(order = 1)
    public ResultDataUtil<InxLocalAndRemoteBorrowApplyAndInformVo> getInxLocalOrRemoteBorrowApplyAndInformData() {
        InxLocalAndRemoteBorrowApplyAndInformVo inxLocalAndRemoteBorrowApplyAndInformVo = iInxLocalAndRemoteBorrowApplyAndInformService.findInxLocalOrRemoteBorrowApplyAndInformData();
        return ResultDataUtil.ok("查询本地、远程申请借阅、通知公告列表信息成功", inxLocalAndRemoteBorrowApplyAndInformVo);
    }
}
