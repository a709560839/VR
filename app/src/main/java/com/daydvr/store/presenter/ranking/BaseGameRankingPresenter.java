package com.daydvr.store.presenter.ranking;

import static com.daydvr.store.base.BaseApplication.MultiThreadPool;
import static com.daydvr.store.base.BaseConstant.CATEGORY_DOWNLOAD;
import static com.daydvr.store.base.BaseConstant.CATEGORY_NEWS;
import static com.daydvr.store.base.BaseConstant.CATEGORY_SOARING;

import android.os.Message;

import com.daydvr.store.bean.GameListBean;
import com.daydvr.store.manager.GameManager;
import com.daydvr.store.model.game.GameModel;
import com.daydvr.store.util.LoaderHandler;
import com.daydvr.store.view.adapter.GameListAdapter.ViewHolder;

/**
 * @author a79560839
 * @version Created on 2018/1/9. 16:42
 */

public abstract class BaseGameRankingPresenter extends BaseGameRankingContract.Presenter {

    abstract void handleUIMessage(Message msg);

    private BaseGameRankingContract.View mView;
    private LoaderHandler mHandler;

    private GameManager mGameManager;
    private GameModel mGameModel;

    public BaseGameRankingPresenter(BaseGameRankingContract.View view) {
        mView = view;

        mHandler = new LoaderHandler();
        mHandler.setListener(mHandleListener);

        mGameManager = new GameManager();
        mGameModel = new GameModel();
        mGameModel.setHandler(mHandler);
    }

    @Override
    public void freeView() {
        mView = null;
    }

    @Override
    public void downloadManager(ViewHolder holder, GameListBean bean) {
        mGameManager.downloadManager(this, holder, bean);
    }

    @Override
    public void loadRanking(int type, final int page) {
        switch (type) {
            case CATEGORY_SOARING:
                MultiThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        mGameModel.getSoaringRankingDatas(page);
                    }
                });
                break;

            case CATEGORY_NEWS:
                MultiThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        mGameModel.getNewsRankingDatas(page);
                    }
                });
                break;

            case CATEGORY_DOWNLOAD:
                MultiThreadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        mGameModel.getDownloadRankingDatas(page);
                    }
                });
                break;

            default:
                break;
        }
    }

    @Override
    public void intoGameDetail(int apkId) {
        if (mView != null) {
            mView.jumpGameDetail(apkId);
        }
    }

    private LoaderHandler.LoaderHandlerListener mHandleListener = new LoaderHandler.LoaderHandlerListener() {

        @Override
        public void handleMessage(Message msg) {
            handleUIMessage(msg);
        }
    };
}
