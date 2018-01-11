package com.daydvr.store.presenter.video;

import com.daydvr.store.base.IBasePresenter;
import com.daydvr.store.base.IBaseView;
import java.util.List;

/**
 * Created by LoSyc on 2017/12/26.
 */

public class VideoListContract {
    public interface View extends IBaseView<Presenter> {
        <T> void showVideo(List<T> beans, int start, int end);

        <T> void showAD(List<T> beans);

        void jumpVideoDetail(int apkId);
    }

    public interface Presenter extends IBasePresenter {
        void loadAD();

        void loadVideo(int page);

        void intoVideoDetail(int videoId);
    }
}
