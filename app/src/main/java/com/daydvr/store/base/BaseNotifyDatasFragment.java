package com.daydvr.store.base;

import static com.daydvr.store.base.BaseConstant.CURRENT_UPDTAE_UI;

/**
 * @author LoSyc
 * @version Created on 2018/1/11. 19:45
 */

public abstract class BaseNotifyDatasFragment extends BaseFragment {

    protected abstract int getCurrentUiView();

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        if (getCurrentUiView() > 99) {
            CURRENT_UPDTAE_UI = getCurrentUiView();
        }
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            if (getCurrentUiView() > 99) {
                CURRENT_UPDTAE_UI = getCurrentUiView();
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (getCurrentUiView() > 99) {
                CURRENT_UPDTAE_UI = getCurrentUiView();
            }
            notifyDatasForPresenter();
        }
    }

    abstract protected void notifyDatasForPresenter();
}
