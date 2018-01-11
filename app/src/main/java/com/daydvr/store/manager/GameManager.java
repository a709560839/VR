package com.daydvr.store.manager;

import com.daydvr.store.base.IBaseDownloadPresenter;
import com.daydvr.store.bean.GameListBean;
import com.daydvr.store.view.adapter.GameListAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.daydvr.store.base.GameConstant.DOWNLOADABLE;
import static com.daydvr.store.base.GameConstant.DOWNLOADING;
import static com.daydvr.store.base.GameConstant.INSTALLABLE;
import static com.daydvr.store.base.GameConstant.INSTALLED;
import static com.daydvr.store.base.GameConstant.PAUSED;
import static com.daydvr.store.base.GameConstant.TEXT_CONTINUE;
import static com.daydvr.store.base.GameConstant.TEXT_PAUSE;

/**
 * @author LoSyc
 * @version Created on 2018/1/8. 15:01
 */

public class GameManager {

    private static List<GameListBean> mDownloadGameDatas = new ArrayList<>();

    public static void saveGameDownloadStatus(GameListBean bean) {
        if (mDownloadGameDatas.contains(bean)) {
            return;
        }
        mDownloadGameDatas.add(bean);
    }

    public static void removeGameDownloadStatus(GameListBean bean) {
        if (!mDownloadGameDatas.contains(bean)) {
            return;
        }
        mDownloadGameDatas.remove(bean);
    }

    public static List<GameListBean> getDownloadGameDatas() {
        return mDownloadGameDatas;
    }

    public void downloadManager(IBaseDownloadPresenter presenter, final GameListAdapter.ViewHolder holder, final GameListBean bean) {
        byte flag = holder.getFlag();
        switch (flag) {
            case DOWNLOADABLE:
//              presenter.startDownload(bean.getId());
                holder.setFlag(holder.getAdapterPosition(), DOWNLOADING);
                holder.setDownloadButtonText(TEXT_PAUSE);
                break;

            case DOWNLOADING:
//                presenter.pauseDownload(bean.getId());

                holder.setFlag(holder.getAdapterPosition(), PAUSED);
                holder.setDownloadButtonText(TEXT_CONTINUE);
                break;

            case PAUSED:
//                presenter.startDownload(bean.getId());
                holder.setFlag(holder.getAdapterPosition(), DOWNLOADING);
                holder.setDownloadButtonText(TEXT_PAUSE);
                break;

            case INSTALLABLE:
                    presenter.installGame(bean.getId());
                break;

            case INSTALLED:
//                  presenter.openGame(bean.getPackageName());
                break;

            default:
                break;
        }
    }
}
