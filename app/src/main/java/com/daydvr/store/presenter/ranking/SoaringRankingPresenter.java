package com.daydvr.store.presenter.ranking;

import android.os.Message;

import com.daydvr.store.bean.GameListBean;

import java.util.ArrayList;
import java.util.List;

import static com.daydvr.store.base.BaseConstant.SOARING_RANKING_LOADER_OK;
import static com.daydvr.store.base.BaseConstant.SORAING_RANKING_CANCELED;

/**
 * @author LoSyc
 * @version Created on 2018/1/9. 18:16
 */

public class SoaringRankingPresenter extends BaseGameRankingPresenter {

    private List<GameListBean> mDatas = new ArrayList<>();
    private BaseGameRankingContract.View mView;

    public SoaringRankingPresenter(BaseGameRankingContract.View view) {
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
    public List<GameListBean> getListBean() {
        return mDatas;
    }

    @Override
    public boolean getIsCanceled() {
        return SORAING_RANKING_CANCELED;
    }

    @Override
    public void setIsCanceled(boolean flag) {
        SORAING_RANKING_CANCELED = flag;
    }

    @Override
    public void initUtils() {

    }

    @Override
    void handleUIMessage(Message msg) {
        if (msg != null) {
            if (msg.what == SOARING_RANKING_LOADER_OK) {
                int start = mDatas.size();
                mDatas.addAll((List<GameListBean>) msg.obj);
                if (mView != null) {
                    mView.showRanking(mDatas, start, ((List<GameListBean>) msg.obj).size());
                }
            }
        }
    }
}
