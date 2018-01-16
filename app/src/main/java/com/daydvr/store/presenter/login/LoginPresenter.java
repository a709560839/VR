package com.daydvr.store.presenter.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Message;

import com.daydvr.store.bean.UserBean;
import com.daydvr.store.model.user.UserModel;
import com.daydvr.store.util.LoaderHandler;

import java.util.Map;

import static com.daydvr.store.base.BaseApplication.MultiThreadPool;
import static com.daydvr.store.base.BaseConstant.LOGIN_REQUEST_OK;
import static com.daydvr.store.base.BaseConstant.USER_MSEEAGE_LOADER_OK;
import static com.daydvr.store.base.PersonConstant.LOGIN_OK;
import static com.daydvr.store.base.PersonConstant.USER_AVATAR_URL;
import static com.daydvr.store.base.PersonConstant.USER_MESSGAE;
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

    private UserModel mUserModel;

    public LoginPresenter(LoginContract.View view) {
        mView = view;
        mView.setPresenter(this);

        mHandler = new LoaderHandler();
        mHandler.setListener(mHandleListener);

        mUserModel = new UserModel();
        mUserModel.setHandler(mHandler);
    }

    @Override
    public void freeView() {
        mView = null;
    }

    @Override
    public void initUtils(Activity activity) {

    }

    @Override
    public void login(final String user, final String password) {
        MultiThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                mUserModel.verifyLogin(user, password);
            }
        });
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
                    boolean flag = (boolean) msg.obj;
                    if (flag) {
                        if (mView != null) {
                            MultiThreadPool.execute(new Runnable() {
                                @Override
                                public void run() {
                                    mUserModel.getUserMessage();
                                }
                            });
                        }
                    } else {
                        new AlertDialog.Builder(mView.getViewContext())
                                .setMessage("请检查账号或者密码是否正确！")
                                .setPositiveButton("确定", null)
                                .show();
                    }
                    break;

                case USER_MSEEAGE_LOADER_OK:
                    UserBean bean = (UserBean) msg.obj;
                    if (mView != null) {
                        Intent intent = new Intent();
                        intent.putExtra(USER_MESSGAE, bean);
                        mView.setLoginResult(LOGIN_OK, intent);
                    }
                    break;

                default:
                    break;
            }
        }
    };
}
