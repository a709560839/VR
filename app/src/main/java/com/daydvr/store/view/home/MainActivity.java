package com.daydvr.store.view.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import com.daydvr.store.R;
import com.daydvr.store.base.BaseActivity;
import com.daydvr.store.base.BaseFragment;
import com.daydvr.store.manager.ActivityManager;
import com.daydvr.store.presenter.home.MainContract;
import com.daydvr.store.presenter.home.MainPresenter;
import com.daydvr.store.bean.AppTabItem;
import com.daydvr.store.util.Logger;
import com.daydvr.store.view.custom.AppTabLayout;
import com.daydvr.store.view.guide.GuideActivity;
import com.daydvr.store.view.guide.SplashActivity;
import com.daydvr.store.view.search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import static com.daydvr.store.base.BaseConstant.GUIDE_FRAGEMNT_ITEM;

/**
 * @author LoSyc
 * @version Created on 2018/1/5. 10:35
 */

public class MainActivity extends BaseActivity implements MainContract.View, AppTabLayout.OnTabClickListener {

    private MainPresenter mPresenter;
    private AppTabLayout mTabLayout;
    private ViewGroup mSearchView;

    /**
     * 再按一次退出时间记录
     */
    private long exittime = 0;

    private int mCurrentPosition = 0;
    private boolean mIsFirstShow = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        checkFirstOpen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDatas();
    }

    private void checkFirstOpen() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(getResources().getString(R.string.config), MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        Editor editor = sharedPreferences.edit();
        if (isFirstRun)
        {
            intoGuide();
            editor.putBoolean("isFirstRun", false);
            editor.apply();
        } else
        {
            intoSplash();
        }
    }

    private void intoGuide() {
        Intent i = new Intent(this, GuideActivity.class);
        startActivity(i);
    }

    private void intoSplash() {
        Intent i = new Intent(this, SplashActivity.class);
        startActivity(i);
    }

    @Override
    protected void onStart() {
        Logger.d("onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Logger.d("onResume");
        super.onResume();
    }

    private void initView() {
        mTabLayout = findViewById(R.id.tl_base);
        configComponent();
    }

    private void configComponent() {
    }

    private void initDatas() {
        mPresenter = new MainPresenter(this);
        mPresenter.loadFragment();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.freeView();
        mPresenter = null;
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = (MainPresenter) presenter;
    }

    @Override
    public <T> void firstShowFragment(List<T> beans) {
        mTabLayout.initData((ArrayList<AppTabItem>) beans, this);

        setShowFragment(GUIDE_FRAGEMNT_ITEM);
    }

    @Override
    public void setShowFragment(int position) {
        replaceFragment(position);
        mTabLayout.setCurrentTab(position);
        mCurrentPosition = position;
    }

    @Override
    public void replaceFragment(int position) {
        if (mIsFirstShow) {
            BaseFragment fragment = mPresenter.getFragment(position);
            getFragmentManager().beginTransaction()
                    .add(R.id.fl_base, fragment)
                    .commit();
            mIsFirstShow = false;
        } else {
            BaseFragment toFragment = mPresenter.getFragment(position);
            BaseFragment fromFragment = mPresenter.getFragment(mCurrentPosition);
            if (!toFragment.isAdded()) {
                getFragmentManager().beginTransaction()
                        .hide(fromFragment)
                        .add(R.id.fl_base, toFragment)
                        .commit();
            } else {
                getFragmentManager().beginTransaction()
                        .hide(fromFragment)
                        .show(toFragment)
                        .commit();
            }
        }
    }

    @Override
    public void jumpSearch() {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void onBackPressed() {
        //退出前返回第一页
        if (mCurrentPosition != 0) {
            setShowFragment(GUIDE_FRAGEMNT_ITEM);
        } else {
            if (System.currentTimeMillis() - exittime < 2000 && exittime != 0) {
                ActivityManager.getManager().finishAllActivity();
            } else {
                Toast.makeText(this, getString(R.string.press_again_exit), Toast.LENGTH_SHORT).show();
                exittime = System.currentTimeMillis();
            }
        }
    }

    /**
     * Listener
     */
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cl_search:
                    mPresenter.intoSearch();
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    public void onTabClick(AppTabItem tabItem) {
        if (mPresenter.getFragment(mCurrentPosition).isVisible()) {
            setShowFragment(mPresenter.getIndexOf(tabItem));
        }
    }
}
