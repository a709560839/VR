package com.daydvr.store.presenter.search;

import android.app.Activity;
import android.os.Bundle;

import com.daydvr.store.base.IBasePresenter;
import com.daydvr.store.base.IBaseView;

import java.util.List;

/**
 * @author LoSyc
 * @version Created on 2017/12/28. 19:09
 */

public class SearchContract {
    public interface View extends IBaseView<Presenter> {
        <T> void showSearchDatas(List<T> beans);

        <T> void showCategorySearch(List<T> beans);

        void jumpGameDetail(int apkId);

        void jumpVideoDetail(Bundle bundle);

        void showSoftInput();
    }

    public interface Presenter extends IBasePresenter {
        void initUtils(Activity activity);

        void loadSearchDatas();

        void searchCategory(int type);

        void intoGameDetail(int apkId);

        void intoVideoDetail();

        void showSoftInput();
    }
}
