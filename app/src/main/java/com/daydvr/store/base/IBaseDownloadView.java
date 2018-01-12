package com.daydvr.store.base;

import com.daydvr.store.view.adapter.GameListAdapter;

/**
 * @author LoSyc
 * @version Created on 2018/1/12. 0:28
 */
public interface IBaseDownloadView<T> extends IBaseView<T> {
    GameListAdapter getListAdapter();
}
