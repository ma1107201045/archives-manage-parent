package com.yintu.rixing.common;

import com.yintu.rixing.annotation.Log;
import com.yintu.rixing.config.other.Authenticator;
import com.yintu.rixing.enumobject.EnumLogLevel;
import com.yintu.rixing.util.ResultDataUtil;
import com.yintu.rixing.vo.common.CommFileVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/19 16:33:15
 * @Version: 1.0
 */
@RestController
@RequestMapping("/common/comm-file-upload")
@Api(tags = "文件上传接口")
@ApiSort(2)
public class CommFileController extends Authenticator {

    @Autowired
    private ICommFileService iCommFileService;


    /**
     * 上传文件
     *
     * @param multipartFile 单个文件
     * @return 返回信息
     */
    @Log(level = EnumLogLevel.DEBUG, module = "公共模块", context = "上传文件")
    @PostMapping
    @ApiOperation(value = "上传文件", notes = "上传文件")
    @ApiImplicitParam(name = "file", value = "文件对象", required = true, dataType = "__file", paramType = "form")
    @ApiOperationSupport(order = 1)
    public ResultDataUtil<CommFileVo> fileUpload(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        CommFileVo CommFileVo = iCommFileService.save(multipartFile, request);
        return ResultDataUtil.ok("上传文件成功", CommFileVo);
    }

    /**
     * 上传文件集
     *
     * @param multipartFiles 文件集
     * @return 返回信息
     */
    @Log(level = EnumLogLevel.DEBUG, module = "公共模块", context = "上传文件集")
    @PostMapping(value = "/batch", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "上传文件集", notes = "上传文件集")
    @ApiImplicitParam(name = "files", allowMultiple = true, value = "文件对象", required = true, dataType = "__file", paramType = "form")
    @ApiOperationSupport(order = 2)
    public ResultDataUtil<List<CommFileVo>> fileBatchUpload(@RequestParam("files") MultipartFile[] multipartFiles, HttpServletRequest request) throws IOException {
        List<CommFileVo> commFileVos = iCommFileService.saveBatch(multipartFiles, request);
        return ResultDataUtil.ok("上传文件集成功", commFileVos);
    }

}
