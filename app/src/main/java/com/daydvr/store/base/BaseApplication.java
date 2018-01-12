package com.daydvr.store.base;

import android.app.Application;

import com.daydvr.store.util.ThreadPoolUtil;

import com.daydvr.store.util.UpdateUiHandler;
import com.squareup.leakcanary.LeakCanary;

import java.util.concurrent.ExecutorService;

import static com.daydvr.store.base.LoginConstant.loginTest;

/**
 * @author LoSyc
 * @version Created on 2017/12/25. 18:48
 */

public class BaseApplication extends Application {
    private static BaseApplication mApplication = null;
    public static ExecutorService SingleThreadPool;
    public static ExecutorService MultiThreadPool;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;

        initUtils();

        loginTest.put("123", "123456");
    }

    private void initUtils() {
        LeakCanary.install(this);

        SingleThreadPool = ThreadPoolUtil.getSingleThreadPool(SingleThreadPool);
        MultiThreadPool = ThreadPoolUtil.getMuiltThreadPool(MultiThreadPool);
        UpdateUiHandler.newInstance();
    }

    public static BaseApplication getApplication() {
        return mApplication;
    }

}
