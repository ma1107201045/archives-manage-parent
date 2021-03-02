package com.yintu.rixing.util;

import cn.hutool.core.io.FileUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xslf.extractor.XSLFPowerPointExtractor;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xssf.extractor.XSSFExcelExtractor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: mlf
 * @Date: 2021/1/31 14:51:41
 * @Version: 1.0
 */
public class FileParseUtil {

    public static void main(String[] args) {
        System.out.println(FileParseUtil.parse(new File("C:\\Users\\Admin\\Desktop\\日兴软件测试数据2\\日兴软件测试数据2\\国家标准\\中国档案机读目录格式.pdf")));
    }


    public static String parse(File file) {
        return parse(FileUtil.readBytes(file), FileUtil.extName(file.getName()));
    }

    public static String parse(String fileBaseName) {
        return parse(FileUtil.readBytes(fileBaseName), FileUtil.extName(fileBaseName));
    }


    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // 判断文档类型，调用不同的解析方法
    public static String parse(byte[] buffer, String suffix) {
        String text = "";
        switch (suffix) {
            case "doc":
                text = getTextFromWord(buffer);
                break;
            case "docx":
                text = getTextFromWord2007(buffer);
                break;
            case "xls":
                text = getTextFromExcel(buffer);
                break;
            case "xlsx":
                text = getTextFromExcel2007(buffer);
                break;
            case "ppt":
                text = getTextFromPPT(buffer);
                break;
            case "pptx":
                text = getTextFromPPT2007(buffer);
                break;
            case "pdf":
                text = getTextFormPDF(buffer);
                break;
            case "txt":
                text = getTextFormTxt(buffer);
                break;
            default:
                System.out.println("不支持解析的文档类型");
        }

        return text.replaceAll("\\s*", "");
    }

    // 读取Word97-2003的全部内容 doc
    private static String getTextFromWord(byte[] file) {
        String text = "";
        InputStream fis;
        WordExtractor ex;
        try {
            // word 2003： 图片不会被读取
            fis = new ByteArrayInputStream(file);
            ex = new WordExtractor(fis);
            text = ex.getText();
            ex.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return text;
    }

    // 读取Word2007+的全部内容 docx
    private static String getTextFromWord2007(byte[] file) {
        String text = "";
        InputStream fis;
        XWPFDocument doc;
        XWPFWordExtractor workbook = null;
        try {
            fis = new ByteArrayInputStream(file);
            doc = new XWPFDocument(fis);
            workbook = new XWPFWordExtractor(doc);
            text = workbook.getText();
            workbook.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return text;
    }

    // 读取Excel97-2003的全部内容 xls
    private static String getTextFromExcel(byte[] file) {
        InputStream is;
        HSSFWorkbook wb;
        String text = "";
        try {
            is = new ByteArrayInputStream(file);
            wb = new HSSFWorkbook(new POIFSFileSystem(is));
            ExcelExtractor extractor = new ExcelExtractor(wb);
            extractor.setFormulasNotResults(false);
            extractor.setIncludeSheetNames(false);
            text = extractor.getText();
            extractor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    // 读取Excel2007+的全部内容 xlsx
    private static String getTextFromExcel2007(byte[] file) {
        InputStream is;
        XSSFWorkbook workBook;
        String text = "";
        try {
            is = new ByteArrayInputStream(file);
            workBook = new XSSFWorkbook(is);
            XSSFExcelExtractor extractor = new XSSFExcelExtractor(workBook);
            extractor.setIncludeSheetNames(false);
            text = extractor.getText();
            extractor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    // 读取Powerpoint97-2003的全部内容 ppt
    private static String getTextFromPPT(byte[] file) {
        String text = "";
        InputStream fis = null;
        PowerPointExtractor ex = null;
        try {
            // word 2003： 图片不会被读取
            fis = new ByteArrayInputStream(file);
            ex = new PowerPointExtractor(fis);
            text = ex.getText();
            ex.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return text;
    }

    // 抽取幻灯片2007+全部内容 pptx
    private static String getTextFromPPT2007(byte[] file) {
        InputStream is;
        XMLSlideShow slide;
        String text = "";
        try {
            is = new ByteArrayInputStream(file);
            slide = new XMLSlideShow(is);
            XSLFPowerPointExtractor extractor = new XSLFPowerPointExtractor(slide);
            text = extractor.getText();
            extractor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    // 读取pdf文件全部内容 pdf
    private static String getTextFormPDF(byte[] file) {
        String text = "";
        InputStream is = null;
        PDDocument pdfdoc = null;
        try {
            is = new ByteArrayInputStream(file);
            pdfdoc = PDDocument.load(is);
            PDFTextStripper stripper = new PDFTextStripper();
            text = stripper.getText(pdfdoc);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (pdfdoc != null) {
                    pdfdoc.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return text;
    }

    // 读取txt文件全部内容 txt
    private static String getTextFormTxt(byte[] file) {
        String text = "";
        try {
            String encoding = getCharset(file);
            text = new String(file, encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    // 获得txt文件编码方式
    private static String getCharset(byte[] file) throws IOException {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        InputStream bis = null;
        try {
            boolean checked = false;
            bis = new ByteArrayInputStream(file);
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1) {
                return charset;
            }
            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8";
                checked = true;
            }
            bis.reset();
            if (!checked) {
                while ((read = bis.read()) != -1) {
                    if (read >= 0xF0) {
                        break;
                    }
                    // 单独出现BF以下的，也算是GBK
                    if (0x80 <= read && read <= 0xBF) {
                        break;
                    }
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        // 双字节 (0xC0 - 0xDF)
                        // (0x80 - 0xBF),也可能在GB编码内
                        if (0x80 <= read && read <= 0xBF) {
                            break;
                        }
                        // 也有可能出错，但是几率较小
                    } else if (0xE0 <= read) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                            }
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                bis.close();
            }
        }
        return charset;
    }


}

