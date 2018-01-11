package com.daydvr.store.view.video;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.daydvr.store.R;
import com.daydvr.store.base.BaseActivity;
import com.daydvr.store.bean.VideoListBean;
import com.daydvr.store.presenter.video.VideoListContract;
import com.daydvr.store.presenter.video.VideoListContract.Presenter;
import com.daydvr.store.presenter.video.VideoListPresenter;
import com.daydvr.store.view.adapter.VideoListAdapter;
import com.daydvr.store.view.custom.AppNestedScrollView;
import com.daydvr.store.view.custom.BannerLayout;
import com.daydvr.store.util.GlideImageLoader;
import com.daydvr.store.view.custom.CommonToolbar;

import java.util.ArrayList;
import java.util.List;


/**
 * @author a79560839
 */
public class VideoListActivity extends BaseActivity implements VideoListContract.View {

    private int mCurrentPage = 1;
    private VideoListPresenter mPresenter;
    private CommonToolbar mToolbar;
    private RecyclerView mVideoRecyclerView;
    private VideoListAdapter mVideoAdapter;
    private BannerLayout mBannerLayout;
    private AppNestedScrollView mAppNestedScrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        mPresenter = new VideoListPresenter(this);

        initView();

        initDatas();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.freeView();
        mPresenter = null;
    }

    private void initView() {
        mAppNestedScrollView = findViewById(R.id.ansv_video_scroll);
        mToolbar = findViewById(R.id.toolbar);
        mBannerLayout = findViewById(R.id.bl_video_list_ad);
        mVideoRecyclerView = findViewById(R.id.rv_video_list);

        configComponent();
    }

    private void configComponent() {
        mToolbar.initmToolBar(this);
        mToolbar.setCenterTitle(R.string.video);
        mBannerLayout.setImageLoader(new GlideImageLoader());
        mAppNestedScrollView.setNestedScrollViewListener(mScrollViewLisenter);

        mVideoRecyclerView.setHasFixedSize(true);
        mVideoRecyclerView.setNestedScrollingEnabled(false);
        mVideoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mVideoRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void initDatas() {
        mPresenter.loadAD();

        mPresenter.loadVideo(mCurrentPage++);
    }

    @Override
    public <T> void showAD(List<T> beans) {
        mBannerLayout.setViewUrls((ArrayList<String>) beans);
    }

    @Override
    public <T> void showVideo(List<T> beans, int start, int end) {
        if (mVideoAdapter == null) {
            mVideoAdapter = new VideoListAdapter();
            mVideoAdapter.setDatas((ArrayList<VideoListBean>) beans);
            mVideoAdapter.setListener(mVideoItemListener);
            mVideoRecyclerView.setAdapter(mVideoAdapter);
        }
        mVideoAdapter.notifyItemRangeChanged(start, end);
    }

    @Override
    public void jumpVideoDetail(int videoId) {
        Intent intent = new Intent(this, VideoDetailActivity.class);
        intent.putExtra("", videoId);
        startActivity(intent);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = (VideoListPresenter) presenter;
    }

    /**
     * Listener 与 Handler
     */
    private VideoListAdapter.ItemOnClickListener mVideoItemListener = new VideoListAdapter.ItemOnClickListener() {

        @Override
        public void onItemClick(View view, VideoListBean bean) {
            mPresenter.intoVideoDetail(1);
        }
    };

    private AppNestedScrollView.NestedScrollViewListener mScrollViewLisenter = new AppNestedScrollView.NestedScrollViewListener() {

        @Override
        public void onNestedScrollChanged(AppNestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            if (scrollY == (nestedScrollView.getChildAt(0).getMeasuredHeight() - nestedScrollView.getMeasuredHeight())) {
                mPresenter.loadVideo(mCurrentPage++);
            }
        }
    };
}
