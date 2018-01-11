package com.daydvr.store.presenter.home;

import android.app.Activity;
import android.util.SparseArray;

import com.daydvr.store.R;
import com.daydvr.store.base.BaseFragment;
import com.daydvr.store.bean.AppTabItem;
import com.daydvr.store.view.game.GameListFragment;
import com.daydvr.store.view.guide.GuideFragment;
import com.daydvr.store.view.person.PersonFragment;
import com.daydvr.store.view.ranking.RankingFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LoSyc
 * @version Created on 2018/1/5. 15:09
 */

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mView;

    private List<AppTabItem> mTabs;
    private SparseArray<BaseFragment> mFragmentSparseArray;

    public MainPresenter(MainContract.View view) {
        mView = view;
        mView.setPresenter(this);

        mFragmentSparseArray = new SparseArray<>();
    }

    @Override
    public void freeView() {
        mView = null;
    }

    @Override
    public void initUtils(Activity activity) {

    }

    @Override
    public void loadFragment() {
        mTabs = new ArrayList<>();
        mTabs.add(new AppTabItem(R.drawable.selector_tab_home, R.string.home, GuideFragment.class));
        mTabs.add(new AppTabItem(R.drawable.selector_tab_game, R.string.game, GameListFragment.class));
        mTabs.add(new AppTabItem(R.drawable.selector_tab_ranking, R.string.ranking, RankingFragment.class));
        mTabs.add(new AppTabItem(R.drawable.selector_tab_person, R.string.person, PersonFragment.class));

        if (mView != null) {
            mView.firstShowFragment(mTabs);
        }
    }

    @Override
    public BaseFragment getFragment(int postion) {
        BaseFragment fragment = null;
        try {
            if (mFragmentSparseArray.get(postion) == null) {
                fragment = mTabs.get(postion).tagFragmentClz.newInstance();
                mFragmentSparseArray.put(postion, fragment);
            } else {
                fragment = mFragmentSparseArray.get(postion);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return fragment;
    }

    @Override
    public int getIndexOf(AppTabItem tabItem) {
        return mTabs.indexOf(tabItem);
    }

    @Override
    public void intoSearch() {
        if (mView != null) {
            mView.jumpSearch();
        }
    }
}
