package com.daydvr.store.presenter.game;

import android.content.Context;

import com.daydvr.store.base.IBaseDownloadPresenter;
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

    public interface Presenter extends IBaseDownloadPresenter {
        void initUtils();

        void loadAD();

        void loadGame(int page);

        void intoGameDetail(int apkId);

        void gameCategory(int type);
    }
}
