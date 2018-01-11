package com.daydvr.store.presenter.appmanager;

import android.content.Context;

import com.daydvr.store.base.IBasePresenter;
import com.daydvr.store.base.IBaseView;
import java.util.List;

/**
 * @author a79560839
 * @version Created on 2018/1/8. 17:58
 */

public interface AppManagerContract {
    interface View extends IBaseView<Presenter> {
        <T> void showAppList(List<T> beans);

        Context getViewContext();
    }

    interface Presenter extends IBasePresenter {
        void unInstallApp(int position);

        void refreshAppList(String packageName);

        void openApp(int position);

        void loadApp();
    }
}
