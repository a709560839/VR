package com.daydvr.store.util;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Created by LoSyc on 2017/11/29.
 */

public class MemoryManagerUtil {

    private static long FiftyMBToByte = 52428800;

    /**
     * 获得内置存储的总空间
     * @return
     */
    public static long getTotalMemoryByte() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        return totalBlocks * blockSize;
    }

    /**
     * 获得内置存储的可用的内存
     * @return
     */
    public static long getAvailableMemoryByte() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long availableBlocks = stat.getAvailableBlocksLong();
        return availableBlocks * blockSize;
    }

    /**
     * 判断内置存储是否满足内存
     * 预留50M 防止磁盘碎片
     * @param total 目标内存
     * @return
     */
    public static boolean isMemoryLack(long total) {
        if (getAvailableMemoryByte() < (total + FiftyMBToByte)) {
            return true;
        }
        return false;
    }
}
