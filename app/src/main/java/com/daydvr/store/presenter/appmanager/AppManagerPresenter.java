package com.daydvr.store.presenter.appmanager;

import static com.daydvr.store.base.BaseApplication.MultiThreadPool;
import static com.daydvr.store.base.BaseConstant.APPLIST_LOADED_OK;

import android.os.Message;

import com.daydvr.store.bean.AppListBean;
import com.daydvr.store.manager.ApkControllerManager;
import com.daydvr.store.model.appList.AppManagerModel;
import com.daydvr.store.util.LoaderHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author a79560839
 * @version Created on 2018/1/8. 17:58
 */
public class AppManagerPresenter implements AppManagerContract.Presenter {

    private AppManagerContract.View mView;

    private AppManagerModel mAppListModel;
    private List<AppListBean.ApkInfo> mAppDatas = new ArrayList<>();

    private ApkControllerManager mApkControllerManager;

    private LoaderHandler mHandler;

    public AppManagerPresenter(AppManagerContract.View view){
        mView = view;

        mHandler = new LoaderHandler();
        mHandler.setListener(mHandleListener);

        mView.setPresenter(this);

        mApkControllerManager = new ApkControllerManager(mView.getViewContext());
        mAppListModel = new AppManagerModel(mView.getViewContext());
        mAppListModel.setHandler(mHandler);
    }

    @Override
    public void loadApp() {
        MultiThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                mAppListModel.getAppList();
            }
        });
    }

    @Override
    public void unInstallApp(int position) {
        String packageName = mAppDatas.get(position).getPackage_name();
        mApkControllerManager.uninstall(packageName);
    }

    @Override
    public void refreshAppList(String packageName) {
        for (AppListBean.ApkInfo apkInfo : mAppDatas) {
            if (packageName.equals(apkInfo.getPackage_name())) {
                mAppDatas.remove(apkInfo);
                break;
            }
        }
    }

    @Override
    public void openApp(int position) {
        String packageName = mAppDatas.get(position).getPackage_name();
        mApkControllerManager.startApp(packageName);
    }

    @Override
    public void freeView() {
        mView = null;
    }

    private LoaderHandler.LoaderHandlerListener mHandleListener = new LoaderHandler.LoaderHandlerListener() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case APPLIST_LOADED_OK:
                    mAppDatas = (List<AppListBean.ApkInfo>) msg.obj;
                    if (mView != null) {
                        mView.showAppList(mAppDatas);
                    }
                    break;

                default:
                    break;
            }
        }
    };
}
