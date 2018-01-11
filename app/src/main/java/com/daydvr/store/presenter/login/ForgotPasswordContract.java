package com.daydvr.store.presenter.login;

import android.app.Activity;
import android.content.Context;

import com.daydvr.store.base.IBasePresenter;
import com.daydvr.store.base.IBaseView;

/**
 * @author LoSyc
 * @version Created on 2018/1/8. 11:56
 */

public class ForgotPasswordContract {
    public interface View extends IBaseView<Presenter> {

        void changeVerifyStatus(CharSequence text);

        void changeSuccess();

        Context getViewContext();
    }

    public interface Presenter extends IBasePresenter {
        void initUtils(Activity activity);

        void getVerifyCode();

        void changePassword(String user, String password, String verifyCode);
    }
}
