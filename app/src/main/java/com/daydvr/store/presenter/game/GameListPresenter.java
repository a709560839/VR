package com.daydvr.store.presenter.game;

import android.os.Message;

import com.daydvr.store.bean.GameListBean;
import com.daydvr.store.manager.ApkControllerManager;
import com.daydvr.store.manager.GameManager;
import com.daydvr.store.model.ad.AdModel;
import com.daydvr.store.model.game.GameModel;
import com.daydvr.store.util.LoaderHandler;
import com.daydvr.store.view.adapter.GameListAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.daydvr.store.base.BaseApplication.MultiThreadPool;
import static com.daydvr.store.base.BaseConstant.AD_LOADER_OK;
import static com.daydvr.store.base.BaseConstant.CATEGORY;
import static com.daydvr.store.base.BaseConstant.GAME_LIST_CANCELED;
import static com.daydvr.store.base.BaseConstant.GAME_LOADER_OK;
import static com.daydvr.store.base.BaseConstant.NORMAL;

/**
 * @author LoSyc
 * @version Created on 2017/12/26. 9:38
 */

public class GameListPresenter extends GameListContract.Presenter {

    private static final String TAG = "daydvr.GameListPresenter";
    private GameListContract.View mView;

    private LoaderHandler mHandler;

    private GameManager mGameManager;
    private AdModel mAdModel;
    private GameModel mGameModel;
    private List<GameListBean> mGameDatas = new ArrayList<>();
    private List<String> mAdUrls = new ArrayList<>();

    private ApkControllerManager mApkManager;

    public GameListPresenter(GameListContract.View view) {
        mView = view;
        mView.setPresenter(this);

        mHandler = new LoaderHandler();
        mHandler.setListener(mHandleListener);

        mGameManager = new GameManager();
        mAdModel = new AdModel();
        mGameModel = new GameModel();
        mAdModel.setHandler(mHandler);
        mGameModel.setHandler(mHandler);

        mApkManager = new ApkControllerManager(mView.getViewContext());
    }

    @Override
    public void freeView() {
        mView = null;
    }

    @Override
    public void initUtils() {
    }

    @Override
    public void loadAD() {
        MultiThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                mAdModel.getADBannerUrls();
            }
        });
    }

    @Override
    public void loadGame(final int page) {
        MultiThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                if (page > 3) {
                    return;
                }
                mGameModel.getGameListDatas(page);
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
    public void gameCategory(int type) {
        List<GameListBean> datas = new ArrayList<>();
        for (GameListBean bean : mGameDatas) {
            String str = bean.getType().replace("类型：", "");
            if (Integer.valueOf(str) < type) {
                datas.add(bean);
            }
        }
        Message msg = mHandler.createMessage(GAME_LOADER_OK, CATEGORY, 0, datas);
        mHandler.sendMessage(msg);
    }

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
//        mApkManager.install(GameUriManager.getApkPath(apkId));
    }

    @Override
    public void openGame(String packageName) {
//        mApkManager.startApp(packageName);
    }

    @Override
    public List<GameListBean> getListBean() {
        return mGameDatas;
    }

    @Override
    public boolean getIsCanceled() {
        return GAME_LIST_CANCELED;
    }

    @Override
    public void setIsCanceled(boolean flag) {
        GAME_LIST_CANCELED = flag;
    }

    private LoaderHandler.LoaderHandlerListener mHandleListener = new LoaderHandler.LoaderHandlerListener() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AD_LOADER_OK:
                    mAdUrls = (List<String>) msg.obj;
                    if (mAdUrls != null && mView != null) {
                        mView.showAD(mAdUrls);
                    }
                    break;

                case GAME_LOADER_OK:
                    if (msg.arg1 == NORMAL) {
                        int start = mGameDatas.size();
                        mGameDatas.addAll((List<GameListBean>) msg.obj);
                        if (mGameDatas != null && mView != null) {
                            mView.showGame(mGameDatas, start, ((List<GameListBean>) msg.obj).size());
                        }
                    }
                    if (msg.arg1 == CATEGORY) {
                        if (mView != null) {
                            mView.showCategoryGame((List<GameListBean>) msg.obj);
                        }
                    }
                    break;

                default:
                    break;
            }
        }
    };
}
