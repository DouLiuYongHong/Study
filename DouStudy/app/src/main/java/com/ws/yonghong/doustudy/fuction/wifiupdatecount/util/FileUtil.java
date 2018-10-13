package com.szshuwei.x.myapplication.wifiupdatecount.util;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

public class FileUtil {
    public static String folderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "shuwei" + File.separator;

    // 判断文件是否存在,不存在就创建
    public static void judeFileExistsCreate(String foleder, String fileName) {
        if (!TextUtils.isEmpty(foleder)) {
            File folderTemp = new File(folderPath);
            if (!folderTemp.exists() || !folderTemp.isDirectory()) {
                folderTemp.mkdir();
            }
            File folder = new File(folderPath + foleder);
            if (folder.exists() && folder.isDirectory()) {
                return;
            } else {
                folder.mkdir();
            }
        }
    }
}