package com.daydvr.store.presenter.guide;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;

import com.daydvr.store.bean.GameListBean;
import com.daydvr.store.bean.VideoListBean;
import com.daydvr.store.manager.ApkControllerManager;
import com.daydvr.store.manager.GameManager;
import com.daydvr.store.model.ad.AdModel;
import com.daydvr.store.model.game.GameModel;
import com.daydvr.store.model.video.VideoModel;
import com.daydvr.store.util.LoaderHandler;
import com.daydvr.store.util.PermissionUtil;
import com.daydvr.store.view.adapter.GameListAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.daydvr.store.base.BaseApplication.MultiThreadPool;
import static com.daydvr.store.base.BaseConstant.AD_LOADER_OK;
import static com.daydvr.store.base.BaseConstant.GAME_LOADER_OK;
import static com.daydvr.store.base.BaseConstant.VIDEO_LOADER_OK;

/**
 * @author LoSyc
 * @version Created on 2017/12/26. 9:38
 */

public class GuidePresenter implements GuideContract.Presenter {
    private GuideContract.View mView;

    private GameManager mGameManager;
    private LoaderHandler mHandler;
    private AdModel mAdModel;
    private GameModel mGameModel;
    private VideoModel mVideoModel;
    private List<String> mAdUrls = new ArrayList<>();
    private List<GameListBean> mGameDatas = new ArrayList<>();
    private List<VideoListBean> mVideoDatas = new ArrayList<>();

    private ApkControllerManager mApkManager;

    public GuidePresenter(GuideContract.View view) {
        mView = view;
        mView.setPresenter(this);

        mHandler = new LoaderHandler();
        mHandler.setListener(mHandleListener);

        mGameManager = new GameManager();
        mAdModel = new AdModel();
        mGameModel = new GameModel();
        mVideoModel = new VideoModel();
        mAdModel.setHandler(mHandler);
        mGameModel.setHandler(mHandler);
        mVideoModel.setHandler(mHandler);


        mApkManager = new ApkControllerManager(mView.getViewContext());
    }

    @Override
    public void freeView() {
        mView = null;
    }

    @Override
    public void initUtils(Activity activity) {
        PermissionUtil.getPermission(activity);
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
    public void loadGameRecommend() {
        MultiThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                mGameModel.getHotGameListDatas();
            }
        });
    }

    @Override
    public void loadVideoRecommend() {
        MultiThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                mVideoModel.getHotVideoListDatas();
            }
        });
    }

    @Override
    public void intoGameList() {
        Bundle bundle = new Bundle();
        if (mView != null) {
            mView.jumpGameList(bundle);
        }
    }

    @Override
    public void intoVideoList() {
        Bundle bundle = new Bundle();
        if (mView != null) {
            mView.jumpVideoList(bundle);
        }
    }

    @Override
    public void intoGameDetail(int apkId) {
        if (mView != null) {
            mView.jumpGameDetail(apkId);
        }
    }

    @Override
    public void intoVideoDetail() {
        if (mView != null) {
            mView.jumpVideoDetail(null);
        }
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
                    mGameDatas = (List<GameListBean>) msg.obj;
                    if (mGameDatas != null && mView != null) {
                        mView.showGameRecommend(mGameDatas);
                    }
                    break;

                case VIDEO_LOADER_OK:
                    mVideoDatas = (List<VideoListBean>) msg.obj;
                    if (mVideoDatas != null && mView != null) {
                        mView.showVideoRecommend(mVideoDatas);
                    }
                    break;

                default:
                    break;
            }
        }
    };

}
