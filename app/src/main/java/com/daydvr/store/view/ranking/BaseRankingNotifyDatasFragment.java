package com.daydvr.store.view.ranking;

import com.daydvr.store.base.BaseNotifyDatasFragment;
import com.daydvr.store.presenter.ranking.BaseGameRankingContract;
import com.daydvr.store.presenter.ranking.BaseGameRankingPresenter;

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
}
