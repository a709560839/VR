package com.daydvr.store.presenter.home;

import android.app.Activity;
import android.content.Context;

import com.daydvr.store.base.BaseFragment;
import com.daydvr.store.base.IBasePresenter;
import com.daydvr.store.base.IBaseView;
import com.daydvr.store.bean.AppTabItem;

import java.util.List;

/**
 * @author LoSyc
 * @version Created on 2018/1/5. 11:55
 */

public class MainContract {
    public interface View extends IBaseView<Presenter> {
        <T> void firstShowFragment(List<T> beans);

        void jumpSearch();

        Context getViewContext();

        void setShowFragment(int postion);

        void replaceFragment(int position);
    }

    public interface Presenter extends IBasePresenter {
        void initUtils(Activity activity);

        void loadFragment();

        BaseFragment getFragment(int postion);

        int getIndexOf(AppTabItem tabItem);

        void intoSearch();
    }
}
