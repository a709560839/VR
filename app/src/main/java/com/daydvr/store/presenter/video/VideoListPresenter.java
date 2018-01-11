package com.daydvr.store.presenter.video;

import android.os.Message;

import com.daydvr.store.bean.VideoListBean;
import com.daydvr.store.model.ad.AdModel;
import com.daydvr.store.model.video.VideoModel;
import com.daydvr.store.util.LoaderHandler;

import java.util.ArrayList;
import java.util.List;

import static com.daydvr.store.base.BaseApplication.MultiThreadPool;
import static com.daydvr.store.base.BaseConstant.AD_LOADER_OK;
import static com.daydvr.store.base.BaseConstant.VIDEO_LOADER_OK;

/**
 * @author SJ
 * @version Created on 2017/12/27. 15:38
 */

public class VideoListPresenter implements VideoListContract.Presenter {
    private VideoListContract.View mView;

    private LoaderHandler mHandler;

    private AdModel mAdModel;
    private VideoModel mVideoModel;
    private List<VideoListBean> mVideoDatas = new ArrayList<>();
    private List<String> mAdUrls = new ArrayList<>();

    public VideoListPresenter(VideoListContract.View view) {
        mView = view;

        mHandler = new LoaderHandler();
        mHandler.setListener(mHandleListener);

        mAdModel = new AdModel();
        mVideoModel = new VideoModel();
        mView.setPresenter(this);

        mAdModel.setHandler(mHandler);
        mVideoModel.setHandler(mHandler);
    }

    @Override
    public void freeView() {
        mView = null;
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
    public void loadVideo(final int page) {
        MultiThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                if (page > 5) {
                    return;
                }
                mVideoModel.getVideoListDatas(page);
            }
        });
    }

    @Override
    public void intoVideoDetail(int videoId) {
        mView.jumpVideoDetail(videoId);
    }

    private LoaderHandler.LoaderHandlerListener mHandleListener = new LoaderHandler.LoaderHandlerListener() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AD_LOADER_OK:
                    mAdUrls = (List<String>) msg.obj;
                    if (null != mAdUrls && mView != null) {
                        mView.showAD(mAdUrls);
                    }
                    break;

                case VIDEO_LOADER_OK:
                    int start = mVideoDatas.size();
                    mVideoDatas.addAll((ArrayList<VideoListBean>) msg.obj);
                    if (mVideoDatas != null && mView != null) {
                        mView.showVideo(mVideoDatas, start, ((ArrayList<VideoListBean>) msg.obj).size());
                    }
                    break;
                default:
        }
        }
    };
}
