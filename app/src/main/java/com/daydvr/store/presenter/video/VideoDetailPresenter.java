package com.daydvr.store.presenter.video;

import android.app.Activity;
import android.os.Message;

import com.daydvr.store.bean.VideoListBean;
import com.daydvr.store.model.video.VideoModel;
import com.daydvr.store.util.LoaderHandler;

import java.util.ArrayList;
import java.util.List;

import static com.daydvr.store.base.BaseApplication.MultiThreadPool;
import static com.daydvr.store.base.BaseConstant.VIDEO_LOADER_OK;

/**
 * @author LoSyc
 * @version Created on 2017/12/28. 15:14
 */

public class VideoDetailPresenter implements VideoDetailContract.Presenter {
    private VideoDetailContract.View mView;

    private LoaderHandler mHandler;
    private VideoModel mVideoModel;
    private List<String> mAdUrls = new ArrayList<>();
    private List<VideoListBean> mVideoDatas = new ArrayList<>();

    public VideoDetailPresenter(VideoDetailContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mHandler = new LoaderHandler();
        mHandler.setListener(mHandleListener);

        mVideoModel = new VideoModel();

        mVideoModel.setHandler(mHandler);
    }

    @Override
    public void freeView() {
        mView = null;
    }

    @Override
    public void initUtils(Activity activity) {
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
    public void loadVideoDetail(int videoId) {

    }


    @Override
    public void intoVideoDetail(int videoId) {
        if (mView != null) {
            mView.jumpVideoDetail(null);
        }
    }

    private LoaderHandler.LoaderHandlerListener mHandleListener = new LoaderHandler.LoaderHandlerListener() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
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
