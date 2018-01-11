package com.daydvr.store.presenter.person;

import android.app.Activity;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.daydvr.store.bean.GameListBean;
import com.daydvr.store.manager.GameManager;
import com.daydvr.store.util.LoaderHandler;
import com.daydvr.store.view.adapter.GameListAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.daydvr.store.base.BaseApplication.MultiThreadPool;
import static com.daydvr.store.base.BaseConstant.GAME_MANAGER_LOADER_OK;
import static com.daydvr.store.base.BaseConstant.IS_CANCELED_ALL_TASK;
import static com.daydvr.store.base.GameConstant.DOWNLOADABLE;
import static com.daydvr.store.base.GameConstant.INSTALLABLE;
import static com.daydvr.store.base.GameConstant.PAUSED;

/**
 * @author LoSyc
 * @version Created on 2018/1/8. 14:38
 */

public class DownloadManagerPresenter extends DownloadManagerContract.Presenter {
    private DownloadManagerContract.View mView;

    private LoaderHandler mHandler;

    private GameManager mGameManager;
    private List<GameListBean> mDatas = new ArrayList<>();

    public DownloadManagerPresenter(DownloadManagerContract.View view) {
        mView = view;
        mView.setPresenter(this);

        mHandler = new LoaderHandler();
        mHandler.setListener(mHandleListener);

        mGameManager = new GameManager();
    }

    @Override
    public void freeView() {
        mView = null;
    }

    @Override
    public void initUtils(Activity activity) {

    }

    @Override
    public void loadDownloadGame() {
        MultiThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                mDatas = GameManager.getDownloadGameDatas();
                if (mHandler != null) {
                    Message msg = mHandler.createMessage(GAME_MANAGER_LOADER_OK, 0, 0, null);
                    mHandler.sendMessage(msg);
                }
            }
        });
    }

    @Override
    public void intoGameDetail(int apkId) {
        if (mView != null) {
            mView.jumpGameDetail(apkId);
        }
    }

    @Override
    public void cancelAll() {
        setIsCanceled(true);
        RecyclerView view = mView.getListView();
        int size = mDatas.size();
        for (int i = 0; i < size; i++) {
            GameListAdapter.ViewHolder holder =
                    (GameListAdapter.ViewHolder) view.getChildViewHolder(view.getChildAt(i));

            if (holder.getFlag() != INSTALLABLE) {
                holder.setInitViewVisibility();
                holder.setFlag(i, DOWNLOADABLE);
                GameManager.removeGameDownloadStatus(mDatas.get(i));
                i--;
            }
        }
        mView.showDownloadGame(mDatas);
    }

    @Override
    public void continueAll() {
        RecyclerView view = mView.getListView();
        for (GameListBean bean : mDatas) {
            if (bean.getStatus() == PAUSED) {
                GameListAdapter.ViewHolder holder =
                        (GameListAdapter.ViewHolder) view.getChildViewHolder(view.getChildAt(mDatas.indexOf(bean)));

                downloadManager(holder, bean);
            }
        }
    }

    @Override
    public int getDatasSize() {
        return mDatas.size();
    }

    private LoaderHandler.LoaderHandlerListener mHandleListener = new LoaderHandler.LoaderHandlerListener() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GAME_MANAGER_LOADER_OK:
                    if (mView != null) {
                        mView.showDownloadGame(mDatas);
                    }
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    public void downloadManager(GameListAdapter.ViewHolder holder, GameListBean bean) {
        mGameManager.downloadManager(this, holder, bean);
    }

    @Override
    public void startDownload(int apkId) {

    }

    @Override
    public void pauseDownload(int apkId) {

    }

    @Override
    public void installGame(int apkId) {
        for (GameListBean bean : mDatas) {
            if (bean.getId() == apkId) {
                GameManager.removeGameDownloadStatus(bean);
                mView.showDownloadGame(mDatas);
                break;
            }
        }
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
        return IS_CANCELED_ALL_TASK;
    }

    @Override
    public void setIsCanceled(boolean flag) {
        GameManager.setIsCanceled(flag);
    }
}
