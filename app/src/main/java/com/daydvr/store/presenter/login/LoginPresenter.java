package com.daydvr.store.presenter.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Message;

import com.daydvr.store.util.LoaderHandler;

import java.util.Map;

import static com.daydvr.store.base.BaseConstant.LOGIN_REQUEST_OK;
import static com.daydvr.store.base.PersonConstant.LOGIN_OK;
import static com.daydvr.store.base.PersonConstant.USER_HEAD_URL;
import static com.daydvr.store.base.PersonConstant.USER_NAME;
import static com.daydvr.store.base.PersonConstant.USER_INTEGRAL;
import static com.daydvr.store.base.PersonConstant.loginTest;

/**
 * @author LoSyc
 * @version Created on 2018/1/6. 0:14
 */
public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View mView;

    private LoaderHandler mHandler;

    public LoginPresenter(LoginContract.View view) {
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
    public void login(String user, String password) {
        for (Map.Entry<String, String> entry : loginTest.entrySet()) {
            if (entry.getKey().equals(user) && entry.getValue().equals(password)) {
                Intent intent = new Intent();
                intent.putExtra(USER_NAME, user);
                intent.putExtra(USER_HEAD_URL, "https://img.tapimg.com/market/lcs/9e1328b55fab10aa59af1dd3273ee401_360.png");

                String str = "1024";

                intent.putExtra(USER_INTEGRAL, str);
                Message msg = mHandler.createMessage(LOGIN_REQUEST_OK, 0, 0, intent);
                mHandler.sendMessage(msg);
                return;
            }
        }
        new AlertDialog.Builder(mView.getViewContext())
                .setMessage("请检查账号或者密码是否正确！")
                .setPositiveButton("确定", null)
                .show();
    }

    @Override
    public void intoForgotPasswordView() {
        if (mView != null) {
            mView.jumpForgotPasswordView();
        }
    }

    @Override
    public void intoRegisterView() {
        if (mView != null) {
            mView.jumpRegisterView();
        }
    }

    @Override
    public void intoQQLogin() {

    }

    @Override
    public void intoWeChatLogin() {

    }

    private LoaderHandler.LoaderHandlerListener mHandleListener = new LoaderHandler.LoaderHandlerListener() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOGIN_REQUEST_OK:
                    Intent intent = (Intent) msg.obj;
                    if (mView != null) {
                        mView.setLoginResult(LOGIN_OK, intent);
                    }
                    break;

                default:
                    break;
            }
        }
    };
}
