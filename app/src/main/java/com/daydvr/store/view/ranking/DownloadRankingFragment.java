package com.daydvr.store.view.ranking;

import static com.daydvr.store.base.BaseConstant.CATEGORY_DOWNLOAD;
import static com.daydvr.store.base.BaseConstant.DOWNLOAD_RANKING_UI_UPDATE;
import static com.daydvr.store.base.GameConstant.APK_ID;
import static com.daydvr.store.base.GameConstant.INSTALLABLE;
import static com.daydvr.store.base.GameConstant.TEXT_INSTALL;
import static com.daydvr.store.base.LoginConstant.threadTest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.daydvr.store.R;
import com.daydvr.store.bean.GameListBean;
import com.daydvr.store.presenter.ranking.BaseGameRankingContract;
import com.daydvr.store.presenter.ranking.BaseGameRankingPresenter;
import com.daydvr.store.presenter.ranking.DownloadRankingPresenter;
import com.daydvr.store.util.AppInfoUtil;
import com.daydvr.store.util.LoaderHandler;
import com.daydvr.store.util.Logger;
import com.daydvr.store.view.adapter.GameListAdapter;
import com.daydvr.store.view.game.GameDetailActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * @author a79560839
 * @version Created on 2018/1/9. 15:27
 */

public class DownloadRankingFragment extends BaseRankingNotifyDatasFragment {

    private View mRootView;
    private RecyclerView mRecyclerView;
    private BaseGameRankingPresenter mPresenter;
    private GameListAdapter mGameAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_download_ranking, container, false);
        mPresenter = new DownloadRankingPresenter(this);

        initHandler();
        initView();

        return mRootView;
    }

    @Override
    protected void notifyDatasForPresenter() {
        mPresenter.notifyDownloadDatas(this);
    }

    @Override
    public GameListAdapter getListAdapter() {
        return mGameAdapter;
    }

    @Override
    protected int getCurrentUiView() {
        return DOWNLOAD_RANKING_UI_UPDATE;
    }

    @Override
    protected void onFragmentFirstVisible() {
        initDatas();
    }

    private void initHandler() {

    }

    private void initView() {
        mRecyclerView = mRootView.findViewById(R.id.rv_download_ranking);

        configComponent();
    }

    private void configComponent() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(mRootView.getContext(), LinearLayoutManager.VERTICAL,
                        false));
    }

    private void initDatas() {
        mPresenter.loadRanking(CATEGORY_DOWNLOAD, 1);
    }

    @Override
    public <T> void showRanking(List<T> beans) {
        if (mGameAdapter == null) {
            mGameAdapter = new GameListAdapter(mRootView.getContext(), true, 15);
            mGameAdapter.setListener(mItemListener);
            mRecyclerView.setAdapter(mGameAdapter);
            mGameAdapter.setDatas((ArrayList<GameListBean>) beans);
        }
        mGameAdapter.notifyDataSetChanged();
    }

    @Override
    public void jumpGameDetail(int apkId) {
        Intent intent = new Intent(mRootView.getContext(), GameDetailActivity.class);
        intent.putExtra(APK_ID, apkId);
        startActivity(intent);
    }

    @Override
    public Context getViewContext() {
        return mRootView.getContext();
    }

    @Override
    public void setPresenter(BaseGameRankingContract.Presenter presenter) {
        mPresenter = (DownloadRankingPresenter) presenter;
    }

    private GameListAdapter.ItemOnClickListener mItemListener = new GameListAdapter.ItemOnClickListener() {

        @Override
        public void onItemClick(View view, GameListBean bean) {
            mPresenter.intoGameDetail(bean.getId());
        }

        @Override
        public void onButtonClick(final View view, final GameListBean bean) {
            final GameListAdapter.ViewHolder holder = (GameListAdapter.ViewHolder) mRecyclerView.getChildViewHolder(view);
            mPresenter.downloadManager(holder, bean);
            AppInfoUtil.setHolderDownloadProgress(bean, holder);
        }

        @Override
        public void onCancelButtonClick(View view, GameListBean bean) {

        }
    };

    @Override
    protected BaseGameRankingPresenter getCurrentItemPresenter() {
        return mPresenter;
    }
}
