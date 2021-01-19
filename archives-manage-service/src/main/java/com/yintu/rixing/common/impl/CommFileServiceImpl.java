package com.yintu.rixing.common.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.yintu.rixing.common.ICommFileService;
import com.yintu.rixing.exception.BaseRuntimeException;
import com.yintu.rixing.util.AddressUtil;
import com.yintu.rixing.util.AssertUtil;
import com.yintu.rixing.util.PathUtil;
import com.yintu.rixing.vo.common.CommFileVo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/19 16:45:37
 * @Version: 1.0
 */
@Service
public class CommFileServiceImpl implements ICommFileService {

    @Override
    public CommFileVo save(MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        long size = multipartFile.getSize();
        if (size == 0) {
            throw new BaseRuntimeException("文件不能为空");
        }
        //原始文件名
        String originalName = multipartFile.getOriginalFilename();
        //文件路径
        String path = PathUtil.getFilePath();
        //新的文件名
        String name = UUID.fastUUID().toString(true) + "." + FileUtil.extName(originalName);
        FileUtil.writeFromStream(multipartFile.getInputStream(), path + name);
        //请求映射
        String requestMapping = AddressUtil.getAddress(request) + PathUtil.getFileMapping() + name;
        CommFileVo commFileVo = new CommFileVo();
        commFileVo.setFileOriginalName(originalName);
        commFileVo.setFilePath(path.substring(0, path.length() - 1));
        commFileVo.setFileSize(new BigDecimal(size / 1024.0 / 1024.0).setScale(2, RoundingMode.HALF_UP).doubleValue());
        commFileVo.setFileName(name);
        commFileVo.setFileRequestMapping(requestMapping);
        return commFileVo;
    }

    @Override
    public List<CommFileVo> saveBatch(MultipartFile[] multipartFiles, HttpServletRequest request) throws IOException {
        List<CommFileVo> commFileVos = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            CommFileVo commFileVo = this.save(multipartFile, request);
            commFileVos.add(commFileVo);
        }
        return commFileVos;
    }
}
