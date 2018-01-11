package com.daydvr.store.manager;

import android.util.SparseArray;

import static com.daydvr.store.base.GameConstant.APKPATH;
import static com.daydvr.store.base.GameConstant.APKTASKID;
import static com.daydvr.store.base.GameConstant.APKURL;
import static com.daydvr.store.base.GameConstant.OBBPATH;
import static com.daydvr.store.base.GameConstant.OBBTASKID;
import static com.daydvr.store.base.GameConstant.OBBURL;

/**
 * @author LoSyc
 * @version Created on 2017/12/29. 16:59
 */

public class GameUriManager {
    private static SparseArray<SparseArray<String>> gameUrls = new SparseArray<>();

    public static SparseArray<SparseArray<String>> getGameUrls() {
        return gameUrls;
    }

    public static void newGameUrls(int apkId) {
        if (gameUrls.get(apkId) == null) {
            gameUrls.put(apkId, new SparseArray<String>());
        }
    }

    public static String getApkUrl(int apkId) {
        if (gameUrls.get(apkId) != null) {
            return gameUrls.get(apkId).get(APKURL);
        }
        return null;
    }

    public static String getApkPath(int apkId) {
        if (gameUrls.get(apkId) != null) {
            return gameUrls.get(apkId).get(APKPATH);
        }
        return null;
    }

    public static String getObbUrl(int apkId) {
        if (gameUrls.get(apkId) != null) {
            return gameUrls.get(apkId).get(OBBURL);
        }
        return null;
    }

    public static String getObbPath(int apkId) {
        if (gameUrls.get(apkId) != null ) {
            return gameUrls.get(apkId).get(OBBPATH);
        }
        return null;
    }

    public static String getApkTaskId(int apkId) {
        if (gameUrls.get(apkId) != null) {
            return gameUrls.get(apkId).get(APKTASKID);
        }
        return null;
    }

    public static String getObbTaskId(int apkId) {
        if (gameUrls.get(apkId) != null) {
            return gameUrls.get(apkId).get(OBBTASKID);
        }
        return null;
    }

    public static void putApkUrl(int apkId, String url) {
        if (gameUrls.get(apkId) != null) {
            gameUrls.get(apkId).put(APKURL, url);
        }
    }

    public static void putApkPath(int apkId, String path) {
        if (gameUrls.get(apkId) != null) {
            gameUrls.get(apkId).put(APKPATH, path);
        }
    }

    public static void putObbUrl(int apkId, String url) {
        if (gameUrls.get(apkId) != null) {
            gameUrls.get(apkId).put(OBBURL, url);
        }
    }

    public static void putObbPath(int apkId, String path) {
        if (gameUrls.get(apkId) != null ) {
            gameUrls.get(apkId).put(OBBPATH, path);
        }
    }

    public static void putApkTaskId(int apkId, String taskId) {
        if (gameUrls.get(apkId) != null) {
            gameUrls.get(apkId).put(APKTASKID, taskId);
        }
    }

    public static void putObbTaskId(int apkId, String taskId) {
        if (gameUrls.get(apkId) != null) {
            gameUrls.get(apkId).put(OBBTASKID, taskId);
        }
    }

}

