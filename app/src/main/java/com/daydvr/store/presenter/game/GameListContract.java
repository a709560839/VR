package com.daydvr.store.presenter.game;

import android.content.Context;

import com.daydvr.store.base.BaseDownloadPresenter;
import com.daydvr.store.base.IBaseView;

import java.util.List;

/**
 * Created by LoSyc on 2017/12/26.
 */

public class GameListContract {
    public interface View extends IBaseView<Presenter> {
        <T> void showAD(List<T> beans);

        <T> void showGame(List<T> beans, int start, int count);

        <T> void showCategoryGame(List<T> beans);

        void showDownload();

        void jumpGameDetail(int apkId);

        Context getViewContext();
    }

    public static abstract class Presenter extends BaseDownloadPresenter {
        abstract void initUtils();

        abstract void loadAD();

        abstract void loadGame(int page);

        abstract void intoGameDetail(int apkId);

        abstract void gameCategory(int type);
    }
}
