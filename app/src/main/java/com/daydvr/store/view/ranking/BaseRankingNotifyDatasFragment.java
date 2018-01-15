package com.daydvr.store.view.ranking;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.daydvr.store.base.BaseNotifyDatasFragment;
import com.daydvr.store.presenter.ranking.BaseGameRankingContract;
import com.daydvr.store.presenter.ranking.BaseGameRankingPresenter;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * @author LoSyc
 * @version Created on 2018/1/11. 20:01
 */

public abstract class BaseRankingNotifyDatasFragment extends BaseNotifyDatasFragment implements BaseGameRankingContract.View{

    protected abstract BaseGameRankingPresenter getCurrentItemPresenter();

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            getCurrentItemPresenter().notifyDownloadDatas(this);
        }
    }

    protected abstract void loadMoreDatas();

    public RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int totalItemCount = 0;
            if (recyclerView.getAdapter() != null) {
                totalItemCount = recyclerView.getAdapter().getItemCount();
            }
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
            int lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
            int visibleItemCount = recyclerView.getChildCount();
            int position = lastVisibleItemPosition - 1;
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItemPosition == totalItemCount - 1
                    && visibleItemCount > 0) {
                loadMoreDatas();
            }
            super.onScrollStateChanged(recyclerView, newState);
        }
    };
}
