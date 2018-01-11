package com.daydvr.store.presenter.video;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.daydvr.store.base.IBasePresenter;
import com.daydvr.store.base.IBaseView;
import com.daydvr.store.bean.VideoBean;

import java.util.List;

/**
 * @author LoSyc
 * @version Created on 2017/12/28. 15:11
 */

public class VideoDetailContract {
    public interface View extends IBaseView<Presenter> {
        <T> void showVideoRecommend(List<T> beans);

        void showVideoDetail(VideoBean videoBean);

        Context getContext();

        void jumpVideoDetail(Bundle bundle);
    }

    public interface Presenter extends IBasePresenter {
        void initUtils(Activity activity);

        void loadVideoRecommend();

        void loadVideoDetail(int videoId);

        void intoVideoDetail(int videoId);
    }
}
