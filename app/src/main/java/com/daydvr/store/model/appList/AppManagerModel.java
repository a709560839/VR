package com.daydvr.store.model.appList;

import static com.daydvr.store.base.BaseConstant.APPLIST_LOADED_OK;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Message;

import com.daydvr.store.bean.AppListBean;
import com.daydvr.store.util.AppInfoUtil;
import com.daydvr.store.util.LoaderHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author a79560839
 * @version Created on 2018/1/8. 17:58
 */

public class AppManagerModel {
    private static final int SYSTEM_APP = 0x01;
    private static final int USER_APP = 0x00;

    private LoaderHandler mHandler;
    private AppInfoUtil mAppInfoUtil;
    private List<ApplicationInfo> mApplicationInfoList;
    private List<AppListBean.ApkInfo> mAppDatas = new ArrayList<>();

    public AppManagerModel(Context context) {
        mAppInfoUtil = new AppInfoUtil(context);
    }

    public void getAppList() {
        mApplicationInfoList = mAppInfoUtil.getApps(USER_APP);
        for (ApplicationInfo info : mApplicationInfoList) {
            AppListBean.ApkInfo bean = new AppListBean.ApkInfo();
            bean.setGame_icon_drawable(mAppInfoUtil.getAppIcon(info));
            bean.setName(mAppInfoUtil.getAppName(info));
            bean.setSize(mAppInfoUtil.getAppSizes(info));
            bean.setPackage_name(mAppInfoUtil.getApkPackageName(info));
            mAppDatas.add(bean);
        }
        if (mHandler != null) {
            Message msg = mHandler.createMessage(APPLIST_LOADED_OK, 0, 0, mAppDatas);
            mHandler.sendMessage(msg);
        }
    }

    public void setHandler(LoaderHandler handler) {
        mHandler = handler;
    }
}
