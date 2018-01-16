package com.daydvr.store.presenter.person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.daydvr.store.base.IBasePresenter;
import com.daydvr.store.base.IBaseView;

/**
 * @author LoSyc
 * @version Created on 2018/1/5. 20:22
 */

public class PersonContract {
    public interface View extends IBaseView<Presenter> {
        void showPersonalMessage(Intent intent);

        void jumpLogin();

        void jumpPersonMessage();

        void jumpSetting();

        void jumpAppList();

        void jumpDownloadManager();

        void jumpSign();

        Context getViewContext();
    }

    public interface Presenter extends IBasePresenter {
        void initUtils(Activity activity);

        void intoLogin();

        void intoPersonMessage();

        void intoSetting();

        void intoAppList();

        void intoDownloadManager();

        void intoSign();
    }
}

