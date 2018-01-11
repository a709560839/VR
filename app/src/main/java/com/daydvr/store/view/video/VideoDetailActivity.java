package com.daydvr.store.view.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.daydvr.store.R;
import com.daydvr.store.base.BaseActivity;
import com.daydvr.store.bean.VideoBean;
import com.daydvr.store.bean.VideoListBean;
import com.daydvr.store.presenter.video.VideoDetailContract;
import com.daydvr.store.presenter.video.VideoDetailPresenter;
import com.daydvr.store.util.DensityUtil;
import com.daydvr.store.view.adapter.VideoListAdapter;
import com.daydvr.store.view.custom.CommonToolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author a79560839
 */
public class VideoDetailActivity extends BaseActivity implements VideoDetailContract.View{

    private RecyclerView mVideoRecyclerView;
    private CommonToolbar mToolbar;
    private VideoDetailPresenter mPresenter;
    private VideoListAdapter mVideoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        mPresenter = new VideoDetailPresenter(this);

        initView();

        initDatas();
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mVideoRecyclerView = findViewById(R.id.rv_video_like);

        configComponent();
    }

    private void configComponent() {
        mToolbar.setPadding(0, DensityUtil.getStatusBarHeight(this),0,0);
        mToolbar.initmToolBar(this);
        mToolbar.setCenterTitle(R.string.video);

        mVideoRecyclerView.setHasFixedSize(true);
        mVideoRecyclerView.setNestedScrollingEnabled(false);
        mVideoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mVideoRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void initDatas() {
        mPresenter.loadVideoRecommend();
    }

    @Override
    public void setPresenter(VideoDetailContract.Presenter presenter) {
        mPresenter = (VideoDetailPresenter) presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.freeView();
        mPresenter = null;
    }

    @Override
    public <T> void showVideoRecommend(List<T> beans) {
        mVideoAdapter = new VideoListAdapter();
        mVideoAdapter.setDatas((ArrayList<VideoListBean>) beans);
        mVideoAdapter.setListener(mVideoItemListener);
        mVideoRecyclerView.setAdapter(mVideoAdapter);
    }

    @Override
    public void showVideoDetail(VideoBean videoBean) {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void jumpVideoDetail(Bundle bundle) {
        Intent intent = new Intent(this, VideoDetailActivity.class);
        startActivity(intent);
    }

    /**
     * Listener 与 Handler
     */
    private VideoListAdapter.ItemOnClickListener mVideoItemListener = new VideoListAdapter.ItemOnClickListener() {

        @Override
        public void onItemClick(View view, VideoListBean bean) {
            mPresenter.intoVideoDetail(bean.getId());
            finish();
        }
    };
}
