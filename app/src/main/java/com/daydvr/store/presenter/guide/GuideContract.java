package com.daydvr.store.presenter.guide;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.daydvr.store.base.BaseDownloadPresenter;
import com.daydvr.store.base.IBaseView;

import java.util.List;

/**
 * Created by LoSyc on 2017/12/26.
 */

public class GuideContract {
    public interface View extends IBaseView<Presenter> {
        <T> void showGameRecommend(List<T> beans, int start, int count);

        <T> void showVideoRecommend(List<T> beans, int start, int count);

        <T> void showAD(List<T> beans);

        void jumpGameList(Bundle bundle);

        void jumpVideoList(Bundle bundle);

        void jumpGameDetail(int apkId);

        void jumpVideoDetail(Bundle bundle);

        Context getViewContext();
    }

    public static abstract class Presenter extends BaseDownloadPresenter {
        abstract void initUtils(Activity activity);

        abstract void loadAD();

        abstract void loadGameRecommend();

        abstract void loadVideoRecommend();

        abstract void intoGameList();

        abstract void intoVideoList();

        abstract void intoGameDetail(int apkId);

        abstract void intoVideoDetail();
    }
}
