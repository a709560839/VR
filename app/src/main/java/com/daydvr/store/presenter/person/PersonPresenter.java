package com.daydvr.store.presenter.person;

import android.app.Activity;

/**
 * @author LoSyc
 * @version Created on 2018/1/5. 20:24
 */

public class PersonPresenter implements PersonContract.Presenter {

    private PersonContract.View mView;

    public PersonPresenter(PersonContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void freeView() {
        mView = null;
    }

    @Override
    public void initUtils(Activity activity) {

    }

    @Override
    public void intoLogin() {
        if (mView != null) {
            mView.jumpLogin();
        }
    }

    @Override
    public void intoRecommend() {
        if (mView != null) {
            mView.jumpRecommend();
        }
    }

    @Override
    public void intoGameList() {
        if (mView != null) {
            mView.jumpGameList();
        }
    }

    @Override
    public void intoVideoList() {
        if (mView != null) {
            mView.jumpVideoList();
        }
    }

    @Override
    public void intoSetting() {
        if (mView != null) {
            mView.jumpSetting();
        }
    }

    @Override
    public void intoAppList() {
        if (mView != null) {
            mView.jumpAppList();
        }
    }

    @Override
    public void intoDownloadManager() {
        if (mView != null) {
            mView.jumpDownloadManager();
        }
    }
}
