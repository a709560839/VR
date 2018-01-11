package com.daydvr.store.presenter.game;
/*
 * Copyright (C) 2017 3ivr. All rights reserved.
 *
 * @author: Jason(Liu ZhiCheng)
 * @mail  : jason@3ivr.cn
 * @date  : 2017/12/29 11:01
 */

import static com.daydvr.store.base.BaseApplication.MultiThreadPool;
import static com.daydvr.store.base.BaseConstant.GAME_DETAIL_OK;
import static com.daydvr.store.base.BaseConstant.GAME_DETAIL_PIC_OK;
import static com.daydvr.store.base.BaseConstant.GAME_LOADER_OK;

import android.os.Message;

import com.daydvr.store.bean.GameBean;
import com.daydvr.store.manager.ApkControllerManager;
import com.daydvr.store.manager.GameUriManager;
import com.daydvr.store.model.game.GameModel;
import com.daydvr.store.presenter.game.GameDetailContract.View;
import com.daydvr.store.util.LoaderHandler;

import java.util.ArrayList;
import java.util.List;

public class GameDetailPresenter implements GameDetailContract.Presenter {

    private GameDetailContract.View mView;
    private List<String> mPicUrls = new ArrayList<>();
    private GameBean mGameBean;
    private GameModel mGameModel;
    private LoaderHandler mHandler;

    private ApkControllerManager mApkManager;

    public GameDetailPresenter(View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);

        mHandler = new LoaderHandler();
        mHandler.setListener(mHandleListener);

        mGameModel = new GameModel();
        mGameModel.setHandler(mHandler);

        mApkManager = new ApkControllerManager(mView.getContext());
    }

    @Override
    public void freeView() {
        mView = null;
    }

    @Override
    public void initUtils() {

    }

    @Override
    public void loadGameDetail(int apkId) {
        MultiThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                mGameModel.getGameDetailData();
            }
        });
    }

    @Override
    public void loadGameDetailPic() {
        MultiThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                mGameModel.getGamePicDatas();
            }
        });
    }

    @Override
    public void startDownload() {

    }

    @Override
    public void pauseDownload() {

    }

    @Override
    public void installGame() {
        int apkId = mGameBean.getId();
        mApkManager.install(GameUriManager.getApkPath(apkId));
    }

    @Override
    public void openGame() {
        mApkManager.startApp(mGameBean.getPackageName());
    }

    private LoaderHandler.LoaderHandlerListener mHandleListener = new LoaderHandler.LoaderHandlerListener() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GAME_LOADER_OK:
                    if (msg.arg1 == GAME_DETAIL_OK) {
                        mGameBean = (GameBean) msg.obj;
                        if (mGameBean != null && mView != null) {
                            mView.showGameDetail(mGameBean);
                        }
                    }

                    if (msg.arg1 == GAME_DETAIL_PIC_OK) {
                        mPicUrls = (ArrayList<String>) msg.obj;
                        if (mPicUrls != null && mView != null) {
                            mView.showGameDetailPic(mPicUrls);
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    };
}
