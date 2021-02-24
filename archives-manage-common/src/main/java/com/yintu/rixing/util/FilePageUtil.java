package com.yintu.rixing.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @Author: mlf
 * @Date: 2021/2/24 10:47:26
 * @Version: 1.0
 */
public class FilePageUtil {

    private static final String[] FILE_EXT_NAME = {"doc", "docx", "ppt", "pptx", "pdf"};

    private static final String SERVER_ADDRESS = "http://127.0.0.1:8012/preview/onlinePreview?url=%s";

    private static final String SERVER_ADDRESS_PDF = "http://127.0.0.1:8012/preview/%s";

    public static void main(String[] args) {
        System.out.println(getPage("http://192.168.2.251:8080/archivesManage/files/f758d43a80ea4dd0b727e383e22a7fa5.docx"));
    }

    public static int getPage(String requestMapping) {
        int page = 0;
        String ext = FileUtil.extName(requestMapping);
        if (StrUtil.containsAny(ext, FILE_EXT_NAME)) {
            //预览生成pdf文件
            String serverAddress = String.format(SERVER_ADDRESS, URLUtil.encode(Base64.encode(requestMapping.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
            HttpUtil.get(serverAddress, StandardCharsets.UTF_8);
            //通过网络获取PDF文件流
            String serverAddressPdf = String.format(SERVER_ADDRESS_PDF, URLUtil.encode(FileUtil.getPrefix(requestMapping) + ".pdf", StandardCharsets.UTF_8));
            HttpResponse response = HttpUtil.createGet(serverAddressPdf).executeAsync();
            try {
                InputStream inputStream = response.bodyStream();
                PDDocument document = PDDocument.load(inputStream);
                page = document.getNumberOfPages();
            } catch (IOException ignored) {

            }
        }
        return page;
    }


}
