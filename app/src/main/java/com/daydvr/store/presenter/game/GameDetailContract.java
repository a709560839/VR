package com.daydvr.store.presenter.game;
/*
 * Copyright (C) 2017 3ivr. All rights reserved.
 *
 * @author: Jason(Liu ZhiCheng)
 * @mail  : jason@3ivr.cn
 * @date  : 2017/12/29 11:00
 */

import android.content.Context;

import com.daydvr.store.base.IBasePresenter;
import com.daydvr.store.base.IBaseView;
import com.daydvr.store.bean.GameBean;

import java.util.ArrayList;
import java.util.List;

public class GameDetailContract {

    public interface View extends IBaseView<Presenter> {
       <T> void showGameDetailPic(List<T> beans);

        void showGameDetail(GameBean gameBean);

        Context getContext();

        void jumpToGameDetail(android.view.View view, ArrayList<CharSequence> data, int position);
    }

    public interface Presenter extends IBasePresenter {
        void initUtils();

        void loadGameDetail(int apkId);

        void loadGameDetailPic();

        void startDownload();

        void pauseDownload();

        void installGame();

        void openGame();
    }
}
