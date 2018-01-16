package com.daydvr.store.presenter.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Message;

import com.daydvr.store.util.LoaderHandler;
import com.daydvr.store.util.Logger;

import static com.daydvr.store.base.BaseApplication.MultiThreadPool;
import static com.daydvr.store.base.BaseConstant.CHANGE_REQUEST_OK;
import static com.daydvr.store.base.BaseConstant.VERIFYCODE_REQUEST_OK;
import static com.daydvr.store.base.BaseConstant.VERIFYCODE_REQUEST_TIME;
import static com.daydvr.store.base.BaseConstant.VERIFYCODE_TIMECHANGE_OK;
import static com.daydvr.store.base.PersonConstant.isAllowRequestCode;
import static com.daydvr.store.base.PersonConstant.flagVerifyCode;
import static com.daydvr.store.base.PersonConstant.loginTest;

/**
 * @author LoSyc
 * @version Created on 2018/1/8. 11:58
 */

public class ForgotPasswordPresenter implements ForgotPasswordContract.Presenter {

    private ForgotPasswordContract.View mView;

    private LoaderHandler mHandler;

    public ForgotPasswordPresenter(ForgotPasswordContract.View view) {
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
    public void changePassword(String user, String password, String verifyCode) {
        Message msg;
        if (flagVerifyCode.equals(verifyCode)) {
            for (String key : loginTest.keySet()) {
                if (key.equals(user)) {
                    loginTest.put(user, password);
                    msg = mHandler.createMessage(CHANGE_REQUEST_OK, 0, 0, null);
                    mHandler.sendMessage(msg);
                    return;
                }
            }
            new AlertDialog.Builder(mView.getViewContext())
                    .setMessage("不存在此用户！")
                    .setPositiveButton("确定", null)
                    .show();
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
                case CHANGE_REQUEST_OK:
                    new AlertDialog.Builder(mView.getViewContext())
                            .setTitle("重置密码成功！")
                            .setMessage("点击确认键返回登录页面！")
                            .setCancelable(false)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (mView != null) {
                                        mView.changeSuccess();
                                    }
                                }
                            })
                            .show();
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
                            mView.changeVerifyStatus(i + "s");
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
