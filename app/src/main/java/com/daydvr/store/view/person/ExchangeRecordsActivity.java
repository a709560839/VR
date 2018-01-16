package com.daydvr.store.view.person;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import com.daydvr.store.R;
import com.daydvr.store.base.BaseActivity;
import com.daydvr.store.bean.GameListBean;
import com.daydvr.store.presenter.person.ExchangeContract;
import com.daydvr.store.presenter.person.ExchangeContract.Presenter;
import com.daydvr.store.presenter.person.ExchangePresenter;
import com.daydvr.store.view.adapter.ExchangeListAdapter;
import com.daydvr.store.view.custom.CommonToolbar;
import com.daydvr.store.view.game.GameDetailActivity;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRecordsActivity extends BaseActivity implements ExchangeContract.View {

    private CommonToolbar mToolBar;
    private RecyclerView mRecyclerView;
    private ExchangeListAdapter mAppListAdapter;

    private ExchangePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_records);
        mPresenter = new ExchangePresenter(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter.loadExchangeAppList();
    }

    private void initView() {
        mToolBar = findViewById(R.id.toolbar);
        mRecyclerView = findViewById(R.id.rv_exchange_records);

        configComponent();
    }

    private void configComponent() {
        mToolBar.setCenterTitle(getResources().getString(R.string.exchange_record));
        mToolBar.initmToolBar(this,false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL,false));
    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = (ExchangePresenter) presenter;
    }

    @Override
    public <T> void showExchangeList(List<T> beans) {
        if (mAppListAdapter == null) {
            mAppListAdapter = new ExchangeListAdapter();
            mAppListAdapter.setListener(mItemListener);
            mRecyclerView.setAdapter(mAppListAdapter);
            mAppListAdapter.setDatas((ArrayList<GameListBean>) beans);
        }
        mAppListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.freeView();
        mPresenter = null;
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void jumpGameDetail() {
        Intent i = new Intent(this, GameDetailActivity.class);
        startActivity(i);
    }

    private ExchangeListAdapter.ItemOnClickListener mItemListener = new ExchangeListAdapter.ItemOnClickListener() {

        @Override
        public void onItemClick(View view, int position) {
          mPresenter.intoAppDetail(position);
        }

        @Override
        public void onButtonClick(View view, int position) {

        }
    };
}
