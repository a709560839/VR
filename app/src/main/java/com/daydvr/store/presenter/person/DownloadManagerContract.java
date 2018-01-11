package com.daydvr.store.presenter.person;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import com.daydvr.store.base.BaseDownloadPresenter;
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

    public static abstract class Presenter extends BaseDownloadPresenter {
        abstract void initUtils(Activity activity);

        abstract void loadDownloadGame();

        abstract void intoGameDetail(int apkId);

        abstract void cancelAll();

        abstract void continueAll();

        abstract int getDatasSize();
    }
}
