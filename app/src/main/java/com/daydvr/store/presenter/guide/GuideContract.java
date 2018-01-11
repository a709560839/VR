package com.daydvr.store.presenter.guide;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.daydvr.store.base.IBaseDownloadPresenter;
import com.daydvr.store.base.IBaseView;
import com.daydvr.store.view.adapter.GameListAdapter;

import java.util.List;

/**
 * Created by LoSyc on 2017/12/26.
 */

public class GuideContract {
    public interface View extends IBaseView<Presenter> {
        <T> void showGameRecommend(List<T> beans);

        <T> void showVideoRecommend(List<T> beans);

        <T> void showAD(List<T> beans);

        void showDownload(RecyclerView.ViewHolder holder);

        void jumpGameList(Bundle bundle);

        void jumpVideoList(Bundle bundle);

        void jumpGameDetail(int apkId);

        void jumpVideoDetail(Bundle bundle);

        Context getViewContext();
    }

    public interface Presenter extends IBaseDownloadPresenter {
       void initUtils(Activity activity);

        void loadAD();

        void loadGameRecommend();

        void loadVideoRecommend();

        void intoGameList();

        void intoVideoList();

        void intoGameDetail(int apkId);

        void intoVideoDetail();
    }
}
