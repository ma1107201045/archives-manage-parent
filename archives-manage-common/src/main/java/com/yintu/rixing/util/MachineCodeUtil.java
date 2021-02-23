package com.yintu.rixing.util;

import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.system.SystemUtil;

import java.io.*;

/**
 * @Author: mlf
 * @Date: 2021/2/23 16:20:40
 * @Version: 1.0
 */
public class MachineCodeUtil {


    public static void main(String[] args) {
        System.out.println(getMachineCode());
    }

    /**
     * 获取机器码
     *
     * @return 返回机器码
     */
    public static String getMachineCode() {
        String cpuSerial = SystemUtil.getOsInfo().isWindows() ? getCPUSerialFormWindow() : getCPUSerialFormLinux();
        if (!"".equals(cpuSerial)) {
            return DigestUtil.md5Hex(cpuSerial);
        }
        return "";
    }

    /**
     * 获取WindowCPU信息
     */
    private static String getCPUSerialFormWindow() {
        StringBuilder result = new StringBuilder();
        try {
            //创建临时文件，路径为C:\Documents and Settings\Administrator\Local Settings\Temp
            File file = File.createTempFile("tmp", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);
            //是有vbs脚本语言，获取CPU唯一ID
            //表示程序出现运行时错误时，会继续运行，不中断
            //表示本机
            String sb = "On Error Resume Next \r\n\r\n" + "strComputer = \".\"  \r\n" +
                    //使用GetObject函数获取本机信息赋值给objWMIService
                    "Set objWMIService = GetObject(\"winmgmts:\" _ \r\n" +
                    "    & \"{impersonationLevel=impersonate}!\\\\\" & strComputer & \"\\root\\cimv2\") \r\n" +
                    "Set colItems = objWMIService.ExecQuery(\"Select * from Win32_Processor\")  \r\n " +
                    //使用for循环取出CPU信息
                    "For Each objItem in colItems\r\n " + "    Wscript.Echo objItem.ProcessorId  \r\n " +
                    "    exit for  ' do the first cpu only! \r\n" +
                    "Next";
            fw.write(sb);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo //T:10 " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result.append(line);
            }
            input.close();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return result.toString();
    }

    /**
     * 获取 Linux CPU信息
     */
    private static String getCPUSerialFormLinux() {
        String result = "";
        try {
            Process process = RuntimeUtil.exec("sudo dmidecode -s system-uuid");
            InputStream in;
            BufferedReader br;
            in = process.getInputStream();
            br = new BufferedReader(new InputStreamReader(in));
            while (in.read() != -1) {
                result = br.readLine();
            }
            br.close();
            in.close();
            process.destroy();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
    }

}
