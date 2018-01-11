package com.daydvr.store.manager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.daydvr.store.util.Logger;

import java.io.File;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;

import static com.daydvr.store.base.GameConstant.FILE_SAVE_DIR;

public class ApkControllerManager {
    private final String TAG = "ApkControllerManager";

    private static WeakReference<Context> mContext;


    private ApkControllerManager() {
    }

    public ApkControllerManager(Context context) {
        if (mContext == null) {
            mContext = new WeakReference<Context>(context);
        }
    }

    /**
     * 根据包名启动另一个程序
     *
     * @param packageName 程序包名
     */
    public void startApp(String packageName) {
        if (mContext.get() == null) {
            return;
        }
        Intent intent = mContext.get().getPackageManager().getLaunchIntentForPackage(packageName);
        mContext.get().startActivity(intent);
    }

    /**
     * 安装应用
     *
     * @param apkPath 安装路径
     */
    public void install(String apkPath) {
        if (mContext.get() == null) {
            return;
        }
//        AuxiliaryService.INVOKE_TYPE = TYPE_APP;
        // 先判断手机是否有root权限
        if (hasRootPerssion()) {
            // 有root权限，利用静默安装实现
            clientInstall(apkPath);
        } else {
            // 没有root权限，利用意图进行安装
            File apkFile = new File(apkPath);
            if (!apkFile.exists()) {
                Logger.d(TAG, "file not exist.");
            }
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri data;
            // 判断版本大于等于7.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                data = FileProvider.getUriForFile(mContext.get(), getFileProviderAuthority(), apkFile);
            } else {
                data = Uri.fromFile(apkFile);
            }
            intent.setDataAndType(data, "application/vnd.android.package-archive");
            mContext.get().startActivity(intent);
        }
    }

    private String getFileProviderAuthority() {
        try {
            for (ProviderInfo provider : mContext.get().getPackageManager()
                    .getPackageInfo(mContext.get().getPackageName(), PackageManager.GET_PROVIDERS).providers) {
                if (FileProvider.class.getName().equals(provider.name) && provider.authority.endsWith(".file_provider")) {
                    return provider.authority;
                }
            }
        } catch (PackageManager.NameNotFoundException ignore) {
        }
        return null;
    }


    /**
     * 根据包名卸载应用
     *
     * @param packageName 包名
     */
    public void uninstall(String packageName) {
        if (mContext.get() == null) {
            return;
        }
//        AuxiliaryService.INVOKE_TYPE = TYPE_APP;
        if (hasRootPerssion()) {
            // 有root权限，利用静默卸载实现
            clientUninstall(packageName);
        } else {
            Uri packageURI = Uri.parse("package:" + packageName);
            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
            uninstallIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.get().startActivity(uninstallIntent);
        }
    }

    /**
     * 判断手机是否有root权限
     */
    public boolean hasRootPerssion() {
        PrintWriter printWriter = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            printWriter = new PrintWriter(process.getOutputStream());
            printWriter.flush();
            printWriter.close();
            int value = process.waitFor();
            return returnResult(value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return false;
    }

    private boolean returnResult(int value) {
        // 代表成功
        if (value == 0) {
            return true;
        } else if (value == 1) {
            return false;
        } else {
            return false;
        }
    }

    /**
     * 静默卸载
     *
     * @param packageName 卸载的程序包名
     */
    public void clientUninstall(String packageName) {
        PrintWriter printWriter = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            printWriter = new PrintWriter(process.getOutputStream());
            printWriter.println("pm uninstall " + packageName);
            printWriter.flush();
            printWriter.close();
            int value = process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }

    /**
     * 静默安装
     *
     * @param apkPath apk文件路径
     */
    public void clientInstall(String apkPath) {
        PrintWriter printWriter = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            printWriter = new PrintWriter(process.getOutputStream());
            printWriter.println("chmod 777 " + apkPath);
            printWriter.println("pm install " + apkPath);
            printWriter.flush();
            printWriter.close();
            int value = process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }

    private void initDownloadDir() {
        /**
         * 初始化SD卡路径
         */
        File[] files = mContext.get().getExternalFilesDirs(null);
        try {
            if (files[1] != null) {
                FILE_SAVE_DIR = files[1].getAbsolutePath();
            } else {
                FILE_SAVE_DIR = files[0].getAbsolutePath();
            }
        } catch (IndexOutOfBoundsException e) {
            FILE_SAVE_DIR = files[0].getAbsolutePath();
        }
    }
}
