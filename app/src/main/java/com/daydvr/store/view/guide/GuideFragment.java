package com.daydvr.store.view.guide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daydvr.store.R;
import com.daydvr.store.base.BaseNotifyDatasFragment;
import com.daydvr.store.bean.GameListBean;
import com.daydvr.store.bean.VideoListBean;
import com.daydvr.store.presenter.guide.GuideContract;
import com.daydvr.store.presenter.guide.GuidePresenter;
import com.daydvr.store.util.AppInfoUtil;
import com.daydvr.store.view.adapter.GameListAdapter;

import com.daydvr.store.view.adapter.VideoListAdapter;

import com.daydvr.store.view.custom.AppNestedScrollView;
import com.daydvr.store.view.custom.BannerLayout;
import com.daydvr.store.util.GlideImageLoader;
import com.daydvr.store.view.game.GameDetailActivity;
import com.daydvr.store.view.home.MainActivity;
import com.daydvr.store.view.video.VideoDetailActivity;
import com.daydvr.store.view.video.VideoListActivity;


import java.util.ArrayList;
import java.util.List;

import static com.daydvr.store.base.BaseConstant.GAME_LIST_FRAGEMNT_ITEM;
import static com.daydvr.store.base.BaseConstant.GUIDE_UI_UPDATE;
import static com.daydvr.store.base.GameConstant.APK_ID;


public class GuideFragment extends BaseNotifyDatasFragment implements GuideContract.View {

    public static final String TAG = "daydvr.GuideFragment";

    private View mRootView;
    private RecyclerView mGameRecyclerView;
    private RecyclerView mVideoRecyclerView;
    private GameListAdapter mGameAdapter;
    private VideoListAdapter mVideoAdapter;
    private BannerLayout mBannerLayout;
    private ViewGroup mVRGameConstraintLayout, mVRVideoConstraintLayout;
    private AppNestedScrollView mAppNestedScrollView;
    private ScrollViewListener mScrollViewListener;
    private LinearLayoutManager mLinearLayoutManager;

    private GuidePresenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_guide, container, false);
        mPresenter = new GuidePresenter(this);
        mPresenter.initUtils(getActivity());

        initHandler();
        initView();

        initDatas();
        return mRootView;
    }

    private void initHandler() {
        mScrollViewListener = new ScrollViewListener();
    }

    private void initView() {
        mAppNestedScrollView = mRootView.findViewById(R.id.ansv_main);
        mVRGameConstraintLayout = mRootView.findViewById(R.id.cl_vr_games);
        mVRVideoConstraintLayout = mRootView.findViewById(R.id.cl_vr_videos);
        mGameRecyclerView = mRootView.findViewById(R.id.rv_home_hotgame);
        mVideoRecyclerView = mRootView.findViewById(R.id.rv_home_hotvideo);
        mBannerLayout = mRootView.findViewById(R.id.bl_home_ad);

        configComponent();
    }

    private void configComponent() {
        initSearchBar(mRootView, mScrollViewListener);
        mAppNestedScrollView.setNestedScrollViewListener(mScrollViewListener);
        mBannerLayout.setImageLoader(new GlideImageLoader());
        mVRGameConstraintLayout.setOnClickListener(mOnClickListener);
        mVRVideoConstraintLayout.setOnClickListener(mOnClickListener);

        mGameRecyclerView.setHasFixedSize(true);
        mGameRecyclerView.setNestedScrollingEnabled(false);
        mGameRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mLinearLayoutManager = new LinearLayoutManager(mRootView.getContext(), LinearLayoutManager.VERTICAL, false);
        mGameRecyclerView.setLayoutManager(mLinearLayoutManager);

        mVideoRecyclerView.setHasFixedSize(true);
        mVideoRecyclerView.setNestedScrollingEnabled(false);
        mVideoRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mVideoRecyclerView.setLayoutManager(new GridLayoutManager(mRootView.getContext(), 2));

    }

    private void initDatas() {
        mPresenter.loadAD();

        mPresenter.loadGameRecommend();

        mPresenter.loadVideoRecommend();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.freeView();
        mPresenter = null;
    }

    @Override
    public void setPresenter(GuideContract.Presenter presenter) {
        mPresenter = (GuidePresenter) presenter;
    }

    @Override
    public <T> void showAD(List<T> beans) {
        mBannerLayout.setViewUrls((ArrayList<String>) beans);
    }

    @Override
    public <T> void showGameRecommend(List<T> beans, int start, int count) {
        if (mGameAdapter == null) {
            mGameAdapter = new GameListAdapter(mRootView.getContext(), false, 15);
            mGameAdapter.setDatas((ArrayList<GameListBean>) beans);
            mGameAdapter.setListener(mGameItemListener);
            mGameRecyclerView.setAdapter(mGameAdapter);
            mGameAdapter.notifyDataSetChanged();
        } else {
            mGameAdapter.notifyItemRangeInserted(start, count);
        }
        dismissLoadingDialog();
    }

    @Override
    public <T> void showVideoRecommend(List<T> beans, int start, int count) {
        if (mVideoAdapter == null) {
            mVideoAdapter = new VideoListAdapter();
            mVideoAdapter.setDatas((ArrayList<VideoListBean>) beans);
            mVideoAdapter.setListener(mVideoItemListener);
            mVideoRecyclerView.setAdapter(mVideoAdapter);
        } else {
            mVideoAdapter.notifyItemRangeInserted(start, count);
        }
    }

    @Override
    public void jumpGameList(Bundle bundle) {
        ((MainActivity) getActivity()).setShowFragment(GAME_LIST_FRAGEMNT_ITEM);
    }

    @Override
    public void jumpVideoList(Bundle bundle) {
        Intent intent = new Intent(getActivity(), VideoListActivity.class);
        startActivity(intent);
    }

    @Override
    public void jumpGameDetail(int apkId) {
        Intent intent = new Intent(getActivity(), GameDetailActivity.class);
        intent.putExtra(APK_ID, apkId);
        startActivity(intent);
    }

    @Override
    public void jumpVideoDetail(Bundle bundle) {
        Intent intent = new Intent(getActivity(), VideoDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public Context getViewContext() {
        return mRootView.getContext();
    }

    /**
     * Listener 与 Handler
     */
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cl_vr_games:
                    mPresenter.intoGameList();
                    break;

                case R.id.cl_vr_videos:
                    mPresenter.intoVideoList();
                    break;

                default:
                    break;
            }
        }
    };

    private VideoListAdapter.ItemOnClickListener mVideoItemListener = new VideoListAdapter.ItemOnClickListener() {

        @Override
        public void onItemClick(View view, VideoListBean bean) {
            mPresenter.intoVideoDetail();
        }
    };

    private GameListAdapter.ItemOnClickListener mGameItemListener = new GameListAdapter.ItemOnClickListener() {

        @Override
        public void onItemClick(View view, GameListBean bean) {
            mPresenter.intoGameDetail(bean.getId());
        }

        @Override
        public void onButtonClick(final View view, final GameListBean bean) {
            final GameListAdapter.ViewHolder holder = (GameListAdapter.ViewHolder) mGameRecyclerView.getChildViewHolder(view);
            mPresenter.downloadManager(holder, bean);
            AppInfoUtil.setHolderDownloadProgress(bean, holder);
        }

        @Override
        public void onCancelButtonClick(View view, GameListBean bean) {

        }
    };

    @Override
    protected int getCurrentUiView() {
        return GUIDE_UI_UPDATE;
    }

    @Override
    protected void notifyDatasForPresenter() {
        mPresenter.notifyDownloadDatas(this);
    }

    @Override
    public GameListAdapter getListAdapter() {
        return mGameAdapter;
    }

    class ScrollViewListener extends BaseScrollViewLisenter {

        @Override
        protected int getBannerHeight() {
            return mBannerLayout.getHeight();
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

