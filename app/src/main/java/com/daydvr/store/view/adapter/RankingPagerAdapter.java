package com.daydvr.store.view.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.daydvr.store.base.BaseFragment;
import com.daydvr.store.view.ranking.BaseRankingNotifyDatasFragment;

import java.util.List;

/**
 * @author a79560839
 * @version Created on 2018/1/9. 15:16
 */

public class RankingPagerAdapter extends FragmentPagerAdapter {

    private List<BaseRankingNotifyDatasFragment> mFragments;
    private String[] mTitle;

    public RankingPagerAdapter(FragmentManager fm,String[] title, List<BaseRankingNotifyDatasFragment> fragments) {
        super(fm);
        this.mTitle = title;
        this.mFragments = fragments;
    }

    @Override
    public BaseFragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }
}
