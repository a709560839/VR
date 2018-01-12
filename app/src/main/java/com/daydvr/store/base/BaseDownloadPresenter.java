package com.daydvr.store.base;

import com.daydvr.store.bean.GameListBean;
import com.daydvr.store.view.adapter.GameListAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.daydvr.store.base.BaseConstant.NOTIFY_ALL;
import static com.daydvr.store.base.GameConstant.DOWNLOADING;
import static com.daydvr.store.base.GameConstant.INSTALLABLE;
import static com.daydvr.store.base.GameConstant.PAUSED;

/**
 * @author LoSyc
 * @version Created on 2018/1/8. 16:59
 */

public abstract class BaseDownloadPresenter implements IBasePresenter {
    public abstract void downloadManager(GameListAdapter.ViewHolder holder, GameListBean bean);

    public abstract void startDownload(int apkId);

    public abstract void pauseDownload(int apkId);

    public abstract void installGame(int apkId);

    public abstract void openGame(String packageName);

    public abstract List<GameListBean> getListBean();

    public abstract boolean getIsCanceled();

    public abstract void setIsCanceled(boolean flag);

    public void notifyDownloadDatas(IBaseDownloadView view) {
        if (getIsCanceled()) {
            view.getListAdapter().notifyDataSetChanged();
            setIsCanceled(false);
            return;
        }
        if (getListBean() != null) {
            for (GameListBean bean : getListBean()) {
                if (bean.getStatus() == DOWNLOADING || bean.getStatus() == PAUSED
                        || bean.getStatus() == INSTALLABLE) {
                    view.getListAdapter().notifyItemChanged(getListBean().indexOf(bean), bean.getStatus());
                }
            }
        }
    }
}
