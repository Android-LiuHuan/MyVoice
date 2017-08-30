package com.jason.voicelibrary.utils;

import android.content.Context;
import android.os.Environment;
import java.io.File;

/**
 * Created by Admin on 2017-08-08.
 */

public class FileUtil {
    private static String Folder = "Recorder";

    public static File getCacheRootFile(Context context) {
        File cacheRootDir = null;
        // 判断sd卡是否存在
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheRootDir = new File(Environment.getExternalStorageDirectory() + "/" + context.getPackageName(), Folder);// 获取根目录
        } else {
            cacheRootDir = new File(context.getPackageName(), Folder);
        }
        if (!cacheRootDir.exists()) {
            cacheRootDir.mkdirs();// 如果路径不存在就先创建路径
        }
        return cacheRootDir;
    }

    public static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWihtFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }
}
