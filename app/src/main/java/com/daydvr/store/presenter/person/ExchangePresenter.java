package com.daydvr.store.presenter.person;
/*
 * Copyright (C) 2018 3ivr. All rights reserved.
 *
 * @author: Jason(Liu ZhiCheng)
 * @mail  : jason@3ivr.cn
 * @date  : 2018/01/16 20:38
 */

import static com.daydvr.store.base.BaseApplication.MultiThreadPool;
import static com.daydvr.store.base.BaseConstant.EXCHANGE_DATA_LOADER_OK;

import android.os.Message;
import com.daydvr.store.bean.AppListBean;
import com.daydvr.store.bean.AppListBean.ApkInfo;
import com.daydvr.store.bean.GameBean;
import com.daydvr.store.bean.GameListBean;
import com.daydvr.store.model.appList.AppManagerModel;
import com.daydvr.store.model.game.GameModel;
import com.daydvr.store.presenter.person.ExchangeContract.View;
import com.daydvr.store.util.LoaderHandler;
import com.daydvr.store.util.LoaderHandler.LoaderHandlerListener;
import java.util.ArrayList;
import java.util.List;

public class ExchangePresenter implements ExchangeContract.Presenter {

    private ExchangeContract.View mView;
    private GameModel mAppListModel;
    private List<GameListBean> mAppDatas = new ArrayList<>();
    private LoaderHandler mHandler;

    public ExchangePresenter(View mView) {
        this.mView = mView;

        mHandler = new LoaderHandler();
        mHandler.setListener(mHandleListener);

        mView.setPresenter(this);

        mAppListModel = new GameModel();
        mAppListModel.setHandler(mHandler);
    }

    @Override
    public void freeView() {
       mView = null;
    }

    @Override
    public void loadExchangeAppList() {
        MultiThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                mAppListModel.getExchangeGameListDatas();
            }
        });
    }

    @Override
    public void intoAppDetail(int position) {
       if(mView!=null){
           mView.jumpGameDetail();
       }
    }

    private LoaderHandler.LoaderHandlerListener mHandleListener = new LoaderHandlerListener() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case EXCHANGE_DATA_LOADER_OK:
                    mAppDatas = (List<GameListBean>) msg.obj;
                    if(mView!=null){
                        mView.showExchangeList(mAppDatas);
                    }
                    break;
                default:
                    break;
            }
        }
    };
}
