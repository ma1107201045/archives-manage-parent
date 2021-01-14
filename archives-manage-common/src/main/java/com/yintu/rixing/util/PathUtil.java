package com.yintu.rixing.util;

import cn.hutool.system.SystemUtil;

/**
 * @Author: mlf
 * @Date: 2021/1/13 15:39:18
 * @Version: 1.0
 */
public class PathUtil {

    public final static String GLOBBING_FILE_MAPPING = "/files/**";

    public final static String FILE_MAPPING = "/files/";

    public final static String FILE_WINDOW_PATH = "C:\\data\\archives-manage\\files\\";

    public final static String FILE_LINUX_PATH = "/data/archives-manage/files/";


    public final static String GLOBBING_BACKUP_MAPPING = "/backups/**";

    public final static String BACKUP_MAPPING = "/backups/";

    public final static String BACKUP_WINDOW_PATH = "C:\\data\\archives-manage\\backups\\";

    public final static String BACKUP_LINUX_PATH = "/data/archives-manage/backups/";


    public final static String GLOBBING_LOG_MAPPING = "/logs/**";

    public final static String LOG_MAPPING = "/logs/";

    public final static String LOG_WINDOW_PATH = "C:\\data\\archives-manage\\logs\\";

    public final static String LOG_LINUX_PATH = "/data/archives-manage/logs/";


    public static String getGlobbingFileMapping() {
        return GLOBBING_FILE_MAPPING;
    }

    public static String getFileMapping() {
        return FILE_MAPPING;
    }

    public static String getFilePath() {
        return SystemUtil.getOsInfo().isWindows() ? FILE_WINDOW_PATH : FILE_LINUX_PATH;
    }

    public static String getGlobbingBackupMapping() {
        return GLOBBING_BACKUP_MAPPING;
    }

    public static String getBackupMapping() {
        return BACKUP_MAPPING;
    }

    public static String getBackupPath() {
        return SystemUtil.getOsInfo().isWindows() ? BACKUP_WINDOW_PATH : BACKUP_LINUX_PATH;
    }

    public static String getGlobbingLogMapping() {
        return GLOBBING_LOG_MAPPING;
    }

    public static String getLogMapping() {
        return LOG_MAPPING;
    }

    public static String getLogPath() {
        return SystemUtil.getOsInfo().isWindows() ? LOG_WINDOW_PATH : LOG_LINUX_PATH;
    }
}
