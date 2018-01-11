package com.daydvr.store.presenter.person;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import com.daydvr.store.base.IBaseDownloadPresenter;
import com.daydvr.store.base.IBaseView;

import java.util.List;

/**
 * @author LoSyc
 * @version Created on 2018/1/8. 14:36
 */

public class DownloadManagerContract {
    public interface View extends IBaseView<Presenter> {
        <T> void showDownloadGame(List<T> beans);

        void jumpGameDetail(int apkId);

        RecyclerView getListView();

        Context getViewContext();
    }

    public interface Presenter extends IBaseDownloadPresenter {
        void initUtils(Activity activity);

        void loadDownloadGame();

        void intoGameDetail(int apkId);

        void cancelAll();

        void continueAll();
    }
}
