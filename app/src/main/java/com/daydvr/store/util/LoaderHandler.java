package com.daydvr.store.util;

import android.os.Handler;
import android.os.Message;

/**
 * @author LoSyc
 * @version Created on 2017/12/28. 11:05
 */

public class LoaderHandler extends Handler {
    private LoaderHandlerListener mListener;

    public Message createMessage(int what, int arg1, int arg2, Object obj) {
        Message msg = Message.obtain();
        msg.what = what;
        if (obj != null) {
            msg.obj = obj;
        }
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        return msg;
    }

    @Override
    public void handleMessage(Message msg) {
        if (mListener != null) {
            mListener.handleMessage(msg);
        }
    }


    public interface LoaderHandlerListener {
        void handleMessage(Message msg);
    }

    public void setListener(LoaderHandlerListener listener) {
        mListener = listener;
    }
}
