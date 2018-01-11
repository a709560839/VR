package com.daydvr.store.presenter.ranking;

import android.os.Message;

import com.daydvr.store.bean.GameListBean;

import java.util.ArrayList;
import java.util.List;

import static com.daydvr.store.base.BaseConstant.NEWS_RANKING_LOADER_OK;

/**
 * @author LoSyc
 * @version Created on 2018/1/9. 18:12
 */

public class NewsRankingPresenter extends BaseGameRankingPresenter {

    private List<GameListBean> mDatas = new ArrayList<>();
    private BaseGameRankingContract.View mView;

    public NewsRankingPresenter(BaseGameRankingContract.View view) {
        super(view);
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void startDownload(int apkId) {

    }

    @Override
    public void pauseDownload(int apkId) {

    }

    @Override
    public void installGame(int apkId) {

    }

    @Override
    public void openGame(String packageName) {

    }

    @Override
    public void initUtils() {

    }

    @Override
    void handleUIMessage(Message msg) {
        if (msg != null) {
            if (msg.what == NEWS_RANKING_LOADER_OK) {
                mDatas = (List<GameListBean>) msg.obj;
                if (mView != null) {
                    mView.showRanking(mDatas);
                }
            }
        }
    }
}
