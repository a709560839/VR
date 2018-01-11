package com.daydvr.store.view.ranking;

import static com.daydvr.store.base.BaseApplication.MultiThreadPool;
import static com.daydvr.store.base.BaseConstant.CATEGORY_SOARING;
import static com.daydvr.store.base.BaseConstant.SOARING_RANKING_UI_UPDATE;
import static com.daydvr.store.base.GameConstant.APK_ID;
import static com.daydvr.store.base.GameConstant.DOWNLOADING;
import static com.daydvr.store.base.GameConstant.INSTALLABLE;
import static com.daydvr.store.base.GameConstant.PAUSED;
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
import com.daydvr.store.base.BaseFragment;
import com.daydvr.store.bean.GameListBean;
import com.daydvr.store.model.game.TestThread;
import com.daydvr.store.presenter.ranking.BaseGameRankingContract;
import com.daydvr.store.presenter.ranking.BaseGameRankingPresenter;
import com.daydvr.store.presenter.ranking.SoaringRankingPresenter;
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

public class SoaringRankingFragment extends BaseFragment implements BaseGameRankingContract.View {

    private View mRootView;
    private RecyclerView mRecyclerView;
    private BaseGameRankingPresenter mPresenter;
    private GameListAdapter mGameAdapter;

    private LoaderHandler mHandler;
    private LoaderListener mHandlerListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_soaring_ranking, container, false);
        mPresenter = new SoaringRankingPresenter(this);

        initHandler();
        initView();

        return mRootView;
    }

    @Override
    public GameListAdapter getListAdapter() {
        return mGameAdapter;
    }

    @Override
    protected void onFragmentFirstVisible() {
        initDatas();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        Logger.d("daydvr.SoaringRankingFragment", "   isVisible:   " + isVisible);
        if (isVisible) {
            if (mGameAdapter != null ) {
                mGameAdapter.notifyDataSetChanged();
            }
        }
    }

    private void initHandler() {
        mHandler = new LoaderHandler();
        mHandlerListener = new LoaderListener();
    }

    private void initView() {
        mRecyclerView = mRootView.findViewById(R.id.rv_soaring_ranking);

        configComponent();
    }

    private void configComponent() {
        mHandler.setListener(mHandlerListener);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(mRootView.getContext(), LinearLayoutManager.VERTICAL,
                        false));
    }

    private void initDatas() {
        mPresenter.loadRanking(CATEGORY_SOARING, 1);
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
        mPresenter = (SoaringRankingPresenter) presenter;
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

            if (holder.getDownloadProgress() == 0) {
                if (threadTest.get(bean.getId()) == null) {
                    threadTest.put(bean.getId(), new TestThread(holder, bean, mHandler) {
                        @Override
                        public void run() {
                            for (int i = 1; i <= 300 && !Thread.currentThread().isInterrupted(); ) {
                                try {
                                    if (this.getHolder().getFlag() == DOWNLOADING) {
                                        this.getHolder().setDownloadProgress(this.getBean(), (int) (this.getBean().getSize() * i / 300));
                                        i++;
                                    } else if (this.getHolder().getFlag() != PAUSED) {
                                        break;
                                    }
                                    if (i == 300 && this.getHandler().equals(mHandler)) {
                                        Message msg = this.getHandler().createMessage(SOARING_RANKING_UI_UPDATE, 0, 0, this.getHolder());
                                        this.getHandler().sendMessage(msg);
                                        break;
                                    }
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            return;
                        }
                    });
                }

                MultiThreadPool.execute(threadTest.get(bean.getId()));
            }
        }

        @Override
        public void onCancelButtonClick(View view, GameListBean bean) {
            if (threadTest.get(bean.getId()) != null) {
                threadTest.get(bean.getId()).interrupt();
                threadTest.remove(bean.getId());
            }
        }
    };

    class LoaderListener implements LoaderHandler.LoaderHandlerListener {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SOARING_RANKING_UI_UPDATE:
                    GameListAdapter.ViewHolder holder = (GameListAdapter.ViewHolder) msg.obj;
                    if (holder.getAdapterPosition() != -1) {
                        holder.setInitViewVisibility();
                        holder.setDownloadButtonText(TEXT_INSTALL);
                        holder.setFlag(holder.getAdapterPosition(), INSTALLABLE);
                    }
                    break;

                default:
                    break;
            }
        }
    }
}
