package com.daydvr.store.view.ranking;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.daydvr.store.R;
import com.daydvr.store.base.BaseFragment;
import com.daydvr.store.view.adapter.GameListAdapter;
import com.daydvr.store.view.adapter.RankingPagerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author a79560839
 * @version Created on 2018/1/9. 14:54
 */

public class RankingFragment extends BaseFragment {

    private View mRootView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ScrollViewListener mScrollViewListener;
    private String[] mTitle = {"飙升榜", "新品榜","下载榜"};
    private List<BaseFragment> fragments = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_ranking_container, container, false);

        mScrollViewListener = new ScrollViewListener();

        initView();

        return mRootView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            GameListAdapter adapter = fragments.get(mViewPager.getCurrentItem()).getListAdapter();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void initView() {
        mTabLayout = mRootView.findViewById(R.id.tl_ranking);
        mViewPager = mRootView.findViewById(R.id.vp_ranking);

        configComponent();
    }

    private void configComponent() {
        initSearchBar(mRootView, mScrollViewListener);
        fragments.add(new SoaringRankingFragment());
        fragments.add(new NewsRankingFragment());
        fragments.add(new DownloadRankingFragment());

        mViewPager.setAdapter(new RankingPagerAdapter(getFragmentManager(), mTitle, fragments));
        mViewPager.setOffscreenPageLimit(1);

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
    }

    class ScrollViewListener extends BaseScrollViewLisenter {

        @Override
        protected int getBannerHeight() {
            return 0;
        }

        @Override
        protected View getSearchBackground() {
            return mRootView.findViewById(R.id.v_search_bg);
        }

        @Override
        protected Activity getHostActivity() {
            return getActivity();
        }

        @Override
        protected View getSearchToolbar() {
            return mRootView.findViewById(R.id.tv_search);
        }
    }
}
