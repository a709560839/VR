package com.daydvr.store.recevice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.daydvr.store.util.BroadCallBack;
import com.daydvr.store.util.Logger;


/**
 * @author LoSyc
 * @version Created on 2017/12/27. 23:56
 */
public class ApkStautsBroadCast extends BroadcastReceiver {
    private static String TAG = "daydvr.ApkStautsBroadCast";

    private BroadCallBack mCallBack;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
//            Logger.d(TAG, "onReceive : ACTION_PACKAGE_ADDED");
//            int apkId;
            if (mCallBack != null) {
//                apkId = mCallBack.deteledApkFile(intent.getDataString());
//                Logger.d(TAG, "apkId:   " + apkId);
//                if (FileUriUtil.getObbPath(apkId) != null) {
//                    String obbPath = FileUriUtil.getObbPath(apkId);
//                    if ((new File(obbPath)).exists()) {
//                        mCallBack.unObbZip(obbPath);
//                    }
//                }
            }
        }

        if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
            if (mCallBack != null) {
                mCallBack.refreshAppList(intent.getDataString());
            }
        }

        if (Intent.ACTION_PACKAGE_REPLACED.equals(intent.getAction())) {
        }
    }

    public void setCallBack(BroadCallBack callBack) {
        mCallBack = callBack;
    }
}