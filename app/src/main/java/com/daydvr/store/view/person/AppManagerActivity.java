package com.daydvr.store.view.person;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import com.daydvr.store.R;
import com.daydvr.store.base.BaseActivity;
import com.daydvr.store.bean.AppListBean;
import com.daydvr.store.presenter.appmanager.AppManagerContract;
import com.daydvr.store.presenter.appmanager.AppManagerContract.Presenter;
import com.daydvr.store.presenter.appmanager.AppManagerPresenter;
import com.daydvr.store.recevice.ApkStautsBroadCast;
import com.daydvr.store.util.BroadCallBack;
import com.daydvr.store.util.DensityUtil;
import com.daydvr.store.util.Logger;
import com.daydvr.store.view.adapter.AppListAdapter;
import com.daydvr.store.view.custom.CommonToolbar;
import java.util.ArrayList;
import java.util.List;

/**
 * @author a79560839
 * @version Created on 2018/1/8. 15:30
 */

public class AppManagerActivity extends BaseActivity implements AppManagerContract.View{
    private static final String TAG = "daydvr.AppManagerActivity";

    private ApkStautsBroadCast mBroadCast;

    private CommonToolbar mCommonToolbar;
    private RecyclerView mRecyclerView;
    private AppListAdapter mAppListAdapter;
    private AppManagerPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_manage);
        mPresenter = new AppManagerPresenter(this);

        initView();

        initData();
    }

    private void initView() {
        mCommonToolbar = findViewById(R.id.toolbar);
        mRecyclerView = findViewById(R.id.rv_app_list);

        configComponent();
    }

    private void configComponent() {
        mCommonToolbar.setCenterTitle(getString(R.string.person_app_uninstall));
        mCommonToolbar.initmToolBar(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL,false));
    }

    private void registerAppReceiver() {
        mBroadCast = new ApkStautsBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addDataScheme("package");
        mBroadCast.setCallBack(mCallBack);
        this.registerReceiver(mBroadCast, filter);
        Logger.d(TAG, "MainActivity:   registerReceiver");
    }

    private void initData() {
        mPresenter.loadApp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerAppReceiver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.freeView();
        mPresenter= null;
        unRegisterAppReceiver();
    }

    private void unRegisterAppReceiver() {
        this.mCallBack = null;
        this.unregisterReceiver(mBroadCast);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter= (AppManagerPresenter) presenter;
    }

    @Override
    public <T> void showAppList(List<T> beans) {
        if (mAppListAdapter == null) {
            mAppListAdapter = new AppListAdapter();
            mAppListAdapter.setListener(mItemListener);
            mRecyclerView.setAdapter(mAppListAdapter);
            mAppListAdapter.setDatas((ArrayList<AppListBean.ApkInfo>) beans);
        }
        mAppListAdapter.notifyDataSetChanged();
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    private AppListAdapter.ItemOnClickListener mItemListener = new AppListAdapter.ItemOnClickListener() {

        @Override
        public void onItemClick(View view, int position) {
            mPresenter.openApp(position);
        }

        @Override
        public void onButtonClick(View view, int position) {
            mPresenter.unInstallApp(position);
        }
    };

    private BroadCallBack mCallBack = new BroadCallBack() {
        @Override
        public void refreshAppList(String appPackageName) {
            appPackageName = appPackageName.replace("package:", "");
            mPresenter.refreshAppList(appPackageName);
            mAppListAdapter.notifyDataSetChanged();
        }
    };
}
