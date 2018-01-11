package com.daydvr.store.model.video;

import android.os.Message;

import com.daydvr.store.bean.VideoBean;
import com.daydvr.store.bean.VideoListBean;
import com.daydvr.store.util.LoaderHandler;

import java.util.ArrayList;
import java.util.List;

import static com.daydvr.store.base.BaseConstant.VIDEO_LOADER_OK;

/**
 * @author LoSyc
 * @version Created on 2017/12/28. 9:34
 */

public class VideoModel {
    private LoaderHandler mHandler;

    private static List<VideoListBean> mVideoListBeans = new ArrayList<>();

    public void getVideoData() {
        VideoBean bean = new VideoBean();
        if (mHandler != null) {
            Message msg = mHandler.createMessage(VIDEO_LOADER_OK, 0, 0, bean);
            mHandler.sendMessage(msg);
        }
    }

    public void getVideoListDatas(int page) {
        List<VideoListBean> datas = new ArrayList<>();
        for (int start = (page - 1) * 15; start < 15 * page; start++) {
            VideoListBean bean = new VideoListBean();
            bean.setName("视频" + start);
            bean.setViews((int) (Math.random() * 10000 + 10000) + "");
            bean.setVideoUrl("https://i0.hdslb.com/bfs/archive/5524cf8367a0731e77594455b488040a07ebcc46.jpg");


            if (start < 4) {
                datas.add(mVideoListBeans.get(start));
                continue;
            }

            if (mVideoListBeans.contains(bean)) {
                continue;
            }

            mVideoListBeans.add(bean);
            datas.add(bean);
        }

        if (mHandler != null) {
            Message msg = mHandler.createMessage(VIDEO_LOADER_OK, 0, 0, datas);
            mHandler.sendMessage(msg);
        }
    }

    public void getHotVideoListDatas() {
        List<VideoListBean> datas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            VideoListBean bean = new VideoListBean();
            bean.setName("视频" + i);
            bean.setViews((int) (Math.random() * 10000 + 10000) + "");
            bean.setVideoUrl("https://i0.hdslb.com/bfs/archive/5524cf8367a0731e77594455b488040a07ebcc46.jpg");
            datas.add(bean);
        }
        mVideoListBeans.addAll(datas);
        if (mHandler != null) {
            Message msg = mHandler.createMessage(VIDEO_LOADER_OK, 0, 0, datas);
            mHandler.sendMessage(msg);
        }
    }

    public void setHandler(LoaderHandler handler) {
        mHandler = handler;
    }

    private static List<VideoListBean> getVideoListBeans() {
        return mVideoListBeans;
    }
}
