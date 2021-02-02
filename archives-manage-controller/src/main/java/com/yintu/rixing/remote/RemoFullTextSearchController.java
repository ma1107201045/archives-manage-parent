package com.yintu.rixing.remote;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.dto.make.MakeArchivesSearchDto;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.make.IMakeArchivesSearchService;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.vo.make.MakeArchivesSearchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: mlf
 * @Date: 2021/2/2 16:02:48
 * @Version: 1.0
 */
@RestController
@RequestMapping("/remote/remo-full-text-search")
@Api(tags = "远程全文检索接口")
@ApiSupport(order = 2)
public class RemoFullTextSearchController {
    @Autowired
    private IMakeArchivesSearchService iMakeArchivesSearchService;


    @Log(level = EnumLogLevel.TRACE, module = "远程借阅", context = "远程全文检索")
    @GetMapping
    @ApiOperation(value = "远程全文检索", notes = "远程全文检索")
    @ApiOperationSupport(order = 1)
    public ResultDataUtil<IPage<MakeArchivesSearchVo>> login(@Validated MakeArchivesSearchDto makeArchivesSearchDto) {
        IPage<MakeArchivesSearchVo> makeArchivesSearchVoIPage = iMakeArchivesSearchService.listByKeyWord(makeArchivesSearchDto);
        return ResultDataUtil.ok("远程全文检索成功", makeArchivesSearchVoIPage);
    }

}
