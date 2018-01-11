package com.daydvr.store.presenter.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.daydvr.store.base.IBasePresenter;
import com.daydvr.store.base.IBaseView;

/**
 * @author LoSyc
 * @version Created on 2018/1/6. 0:11
 */
public class LoginContract {
    public interface View extends IBaseView<Presenter> {

        void jumpForgotPasswordView();

        void jumpRegisterView();

        void jumpQQLogin();

        void jumpWeChatLogin();

        void setLoginResult(int resultCode, Intent data);

        Context getViewContext();
    }

    public interface Presenter extends IBasePresenter {
        void initUtils(Activity activity);

        void login(String user, String password);

        void intoForgotPasswordView();

        void intoRegisterView();

        void intoQQLogin();

        void intoWeChatLogin();
    }
}
