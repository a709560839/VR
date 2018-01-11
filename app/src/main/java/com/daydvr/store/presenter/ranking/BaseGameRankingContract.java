package com.daydvr.store.presenter.ranking;

import android.content.Context;
import com.daydvr.store.base.BaseDownloadPresenter;
import com.daydvr.store.base.IBaseView;

import java.util.List;

/**
 * @author a79560839
 * @version Created on 2018/1/9. 16:37
 */

public abstract class BaseGameRankingContract {
    public interface View extends IBaseView<Presenter> {
        <T> void showRanking(List<T> beans);

        void jumpGameDetail(int apkId);

        Context getViewContext();
    }

    public static abstract class Presenter extends BaseDownloadPresenter {
        abstract void initUtils();

        abstract void loadRanking(int type, int page);

        abstract void intoGameDetail(int apkId);
    }
}
