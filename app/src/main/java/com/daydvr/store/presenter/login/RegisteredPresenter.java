package com.daydvr.store.presenter.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Message;

import com.daydvr.store.util.LoaderHandler;
import com.daydvr.store.util.Logger;

import static com.daydvr.store.base.BaseApplication.MultiThreadPool;
import static com.daydvr.store.base.PersonConstant.isAllowRequestCode;
import static com.daydvr.store.base.BaseConstant.REGISTERED_REQUEST_OK;
import static com.daydvr.store.base.BaseConstant.VERIFYCODE_REQUEST_OK;
import static com.daydvr.store.base.BaseConstant.VERIFYCODE_REQUEST_TIME;
import static com.daydvr.store.base.BaseConstant.VERIFYCODE_TIMECHANGE_OK;
import static com.daydvr.store.base.PersonConstant.LOGIN_OK;
import static com.daydvr.store.base.PersonConstant.USER_ACCOUNT;
import static com.daydvr.store.base.PersonConstant.USER_PASSWORD;
import static com.daydvr.store.base.PersonConstant.loginTest;
import static com.daydvr.store.base.PersonConstant.flagVerifyCode;

/**
 * @author LoSyc
 * @version Created on 2018/1/6. 18:14
 */

public class RegisteredPresenter implements RegisteredContract.Presenter {

    private RegisteredContract.View mView;

    private LoaderHandler mHandler;

    public RegisteredPresenter(RegisteredContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mHandler = new LoaderHandler();
        mHandler.setListener(mHandleListener);
    }

    @Override
    public void freeView() {
        mView = null;
    }

    @Override
    public void initUtils(Activity activity) {

    }

    @Override
    public void getVerifyCode() {
        if (isAllowRequestCode) {
            flagVerifyCode = String.valueOf((int) (Math.random() * 1000000));
            while (flagVerifyCode.length() < 6) {
                flagVerifyCode = "0" + flagVerifyCode;
            }
            Logger.d(flagVerifyCode);
            new AlertDialog.Builder(mView.getViewContext())
                    .setMessage(flagVerifyCode)
                    .setPositiveButton("确定", null)
                    .show();
            Message msg = mHandler.createMessage(VERIFYCODE_REQUEST_OK, 0, 0, null);
            mHandler.sendMessage(msg);
        }
    }

    @Override
    public void registered(String user, String password, String verifyCode) {
        if (flagVerifyCode.equals(verifyCode)) {
            Intent intent = new Intent();
            intent.putExtra(USER_ACCOUNT, user);
            intent.putExtra(USER_PASSWORD, password);
            loginTest.put(user, password);
            Message msg = mHandler.createMessage(REGISTERED_REQUEST_OK, 0, 0, intent);
            mHandler.sendMessage(msg);
        } else {
            new AlertDialog.Builder(mView.getViewContext())
                    .setMessage("验证码不正确！")
                    .setPositiveButton("确定", null)
                    .show();
        }
    }

    private LoaderHandler.LoaderHandlerListener mHandleListener = new LoaderHandler.LoaderHandlerListener() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REGISTERED_REQUEST_OK:
                    Intent intent = (Intent) msg.obj;
                    if (mView != null) {
                        mView.setRegisteredResult(LOGIN_OK, intent);
                    }
                    break;

                case VERIFYCODE_REQUEST_OK:
                    MultiThreadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = VERIFYCODE_REQUEST_TIME; i >= 0; i--) {
                                try {
                                    Message msg = mHandler.createMessage(VERIFYCODE_TIMECHANGE_OK, 0, 0, i);
                                    mHandler.sendMessage(msg);
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    isAllowRequestCode = false;
                    break;

                case VERIFYCODE_TIMECHANGE_OK:
                        int i = (int) msg.obj;
                        if (i > 0) {
                            if (mView != null) {
                                mView.changeVerifyStatus(i +"s");
                            }
                        } else {
                            isAllowRequestCode = true;
                            if (mView != null) {
                                mView.changeVerifyStatus("获取验证码");
                            }
                        }
                    break;

                default:
                    break;
            }
        }
    };
}
