package com.daydvr.store.view.game;

import static com.daydvr.store.base.GameConstant.APK_ID;
import static com.daydvr.store.base.GameConstant.DOWNLOADABLE;
import static com.daydvr.store.base.GameConstant.DOWNLOADING;
import static com.daydvr.store.base.GameConstant.GAME_DETAIL_PIC;
import static com.daydvr.store.base.GameConstant.GAME_DETAIL_PIC_DATAS;
import static com.daydvr.store.base.GameConstant.INSTALLABLE;
import static com.daydvr.store.base.GameConstant.INSTALLED;
import static com.daydvr.store.base.GameConstant.PAUSED;
import static com.daydvr.store.base.GameConstant.UPDATE;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.OnOffsetChangedListener;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import com.daydvr.store.R;
import com.daydvr.store.base.BaseActivity;
import com.daydvr.store.bean.GameBean;
import com.daydvr.store.presenter.game.GameDetailContract;
import com.daydvr.store.presenter.game.GameDetailPresenter;
import com.daydvr.store.view.adapter.GamePicApdapter;
import com.daydvr.store.view.adapter.GamePicApdapter.ItemClick;
import com.daydvr.store.view.custom.FlikerProgressBar;
import com.daydvr.store.view.custom.CommonToolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameDetailActivity extends BaseActivity implements OnClickListener, Runnable, GameDetailContract.View {

    private GameDetailContract.Presenter mPresenter;

    private CommonToolbar mToolbar;
    private RecyclerView mRecyclerView;
    private GamePicApdapter mGamePicApdapter;
    private AppBarLayout mGameAppBarLayout;
    private CollapsingToolbarLayout mGameCollapsingToolbarLayout;
    private FlikerProgressBar mProgressBar;
    private LinearLayout mRatingLinearLayout;
    private LinearLayout mCategoryLinearLayout;
    private Thread downLoadThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        mPresenter = new GameDetailPresenter(this);

        initView();
        configComponent();
        animationStart();
        initDatas();

    }


    private void initView() {

        showLoadingDialog();
        mToolbar = findViewById(R.id.toolbar);
        mGameCollapsingToolbarLayout = findViewById(R.id.ctl_game_detail);
        mProgressBar = findViewById(R.id.fg_game_download);
        mGameAppBarLayout = findViewById(R.id.apl_game_detail);
        mRatingLinearLayout = findViewById(R.id.ll_game_detail_1);
        mCategoryLinearLayout = findViewById(R.id.ll_game_detail_2);
    }

    private void configComponent() {

        mToolbar.initmToolBar(this);
        mProgressBar.setOnClickListener(this);
        mProgressBar.setState(DOWNLOADABLE);
        mProgressBar.setMax(100);
        final MenuItem menuItem = mToolbar.getMenu().findItem(R.id.action_search);
        mGameAppBarLayout.addOnOffsetChangedListener(new OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                invalidateOptionsMenu();
                mToolbar.setNavigationIcon(R.mipmap.back_white);
                menuItem.setIcon(R.mipmap.search_white);
            }
        });
        mGameCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        mGameCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        mGameCollapsingToolbarLayout.setTitle("奇惑VR");
        mRecyclerView = findViewById(R.id.rcv_game_pic_detail);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void animationStart() {

        AnimationSet animationSet = new AnimationSet(true);
        Animation scale=new ScaleAnimation(0, 1, 0, 1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scale.setDuration(500);
        scale.setInterpolator(new DecelerateInterpolator());
        AlphaAnimation alph=new AlphaAnimation(0.1f, 1.0f);
        alph.setDuration(500);
        animationSet.addAnimation(scale);
        animationSet.addAnimation(alph);
        animationSet.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                 dismissLoadingDialog();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mRatingLinearLayout.startAnimation(animationSet);
        mCategoryLinearLayout.startAnimation(animationSet);
        
    }


    private void initDatas() {
        Intent intent = getIntent();
        int apkId = intent.getIntExtra(APK_ID, 0);

//        mPresenter.loadGameDetail(apkId);

        mPresenter.loadGameDetailPic();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fg_game_download) {
            switch (mProgressBar.getState()) {
                case INSTALLABLE:
//                    mPresenter.installGame();

                    break;

                case INSTALLED:
//                    mPresenter.openGame();
                    break;

                case DOWNLOADABLE:
//                    mPresenter.startDownload();

                    mProgressBar.setState(DOWNLOADING);
                    downLoad();
                    break;

                case DOWNLOADING:
//                mPresenter.pauseDownload();

                    downLoadThread.interrupt();
                    mProgressBar.setState(PAUSED);
                    break;

                case UPDATE:
//                    mPresenter.startDownload();

                    break;

                case PAUSED:
//                    mPresenter.startDownload();

                    downLoad();
                    mProgressBar.setState(DOWNLOADING);
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    public void run() {
        try {
            while (!downLoadThread.isInterrupted()) {
                float progress = mProgressBar.getDownloadProgress();
                progress += 2;
                Thread.sleep(200);
                Message message = handler.obtainMessage();
                message.arg1 = (int) progress;
                handler.sendMessage(message);
                if (progress == 100) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void reLoad(View view) {
        downLoadThread.interrupt();
        // 重新加载
        mProgressBar.reset();
        downLoad();
    }

    private void downLoad() {
        downLoadThread = new Thread(this);
        downLoadThread.start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mProgressBar.setDownloadProgress(msg.arg1);
            if (msg.arg1 == 100) {
                mProgressBar.setState(INSTALLABLE);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.initUtils();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.freeView();
        mPresenter = null;
    }

    @Override
    public void setPresenter(GameDetailContract.Presenter presenter) {
        if (mPresenter == null) {
            mPresenter = presenter;
        }
    }

    @Override
    public <T> void showGameDetailPic(final List<T> beans) {
        mGamePicApdapter = new GamePicApdapter((List<String>) beans, this);
        mGamePicApdapter.setItemClickListener(new ItemClick() {
            @Override
            public void onItemClick(View view, int position) {
                jumpToGameDetail(view, new ArrayList<CharSequence>(Arrays.asList(beans.toArray(new CharSequence[beans.size()]))), position);

            }
        });
        mRecyclerView.setAdapter(mGamePicApdapter);
    }

    @Override
    public void showGameDetail(GameBean gameBean) {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void jumpToGameDetail(View view, ArrayList<CharSequence> data, int position) {
        Intent i = new Intent(GameDetailActivity.this, GameDetailPictureActivity.class);
        i.putExtra(GAME_DETAIL_PIC, position);
        i.putCharSequenceArrayListExtra(GAME_DETAIL_PIC_DATAS, data);
        if (VERSION.SDK_INT >= 21) {
            startActivity(i, ActivityOptions
                    .makeSceneTransitionAnimation(this, view, getResources().getString(R.string.game_pic_share_name)).toBundle());

        } else {
            startActivity(i);
        }
    }
}
