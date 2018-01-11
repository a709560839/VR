package com.daydvr.store.base;

import com.daydvr.store.bean.GameListBean;
import com.daydvr.store.view.adapter.GameListAdapter;

/**
 * @author LoSyc
 * @version Created on 2018/1/8. 16:59
 */

public interface IBaseDownloadPresenter extends IBasePresenter {
    void downloadManager(GameListAdapter.ViewHolder holder, GameListBean bean);

    void startDownload(int apkId);

    void pauseDownload(int apkId);

    void installGame(int apkId);

    void openGame(String packageName);
}
