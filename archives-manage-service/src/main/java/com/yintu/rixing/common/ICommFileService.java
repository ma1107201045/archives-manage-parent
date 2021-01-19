package com.yintu.rixing.common;

import com.yintu.rixing.vo.common.CommFileVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @Author: mlf
 * @Date: 2021/1/19 16:44:20
 * @Version: 1.0
 */
public interface ICommFileService {

    CommFileVo save(MultipartFile multipartFile, HttpServletRequest request) throws IOException;

    List<CommFileVo> saveBatch(MultipartFile[] multipartFiles, HttpServletRequest request) throws IOException;

}
