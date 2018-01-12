package com.daydvr.store.util;

import android.os.Handler;
import android.os.Message;

import com.daydvr.store.view.adapter.GameListAdapter;

import static com.daydvr.store.base.BaseConstant.DOWNLOAD_RANKING_UI_UPDATE;
import static com.daydvr.store.base.BaseConstant.GAME_LIST_UI_UPDATE;
import static com.daydvr.store.base.BaseConstant.GAME_MANAGER_UI_UPDATE;
import static com.daydvr.store.base.BaseConstant.GUIDE_UI_UPDATE;
import static com.daydvr.store.base.BaseConstant.NEWS_RANKING_UI_UPDATE;
import static com.daydvr.store.base.BaseConstant.SOARING_RANKING_UI_UPDATE;
import static com.daydvr.store.base.BaseConstant.UI_UPDATE_INSTALLABLE;
import static com.daydvr.store.base.GameConstant.INSTALLABLE;
import static com.daydvr.store.base.GameConstant.TEXT_INSTALL;

/**
 * @author LoSyc
 * @version Created on 2018/1/12. 9:26
 */

public class UpdateUiHandler extends Handler {
    private UpdateUiListener mListener;
    private static UpdateUiHandler mHandler;

    private UpdateUiHandler () {

    }

    public static UpdateUiHandler newInstance() {
        if (mHandler == null) {
            mHandler = new UpdateUiHandler();
        }
        return mHandler;
    }

    public static Message createMessage(int what, int arg1, int arg2, Object obj) {
        Message msg = Message.obtain();
        msg.what = what;
        if (obj != null) {
            msg.obj = obj;
        }
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        return msg;
    }

    public static void sendMessageForUiHandler(Message msg) {
        mHandler.sendMessage(msg);
    }

    @Override
    public void handleMessage(Message msg) {
        if (msg.what > 99 && msg.arg1 == UI_UPDATE_INSTALLABLE) {
            switch (msg.what) {
                case GUIDE_UI_UPDATE:
                case GAME_LIST_UI_UPDATE:
                case GAME_MANAGER_UI_UPDATE:
                case SOARING_RANKING_UI_UPDATE:
                case NEWS_RANKING_UI_UPDATE:
                case DOWNLOAD_RANKING_UI_UPDATE:
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

        if (mListener != null) {
            mListener.handleMessage(msg);
        }
    }


    public interface UpdateUiListener {
        void handleMessage(Message msg);
    }

    public void setListener(UpdateUiListener listener) {
        mListener = listener;
    }
}