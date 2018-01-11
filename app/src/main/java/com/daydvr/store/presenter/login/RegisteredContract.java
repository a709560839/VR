package com.daydvr.store.presenter.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.daydvr.store.base.IBasePresenter;
import com.daydvr.store.base.IBaseView;

/**
 * @author LoSyc
 * @version Created on 2018/1/6. 18:09
 */

public class RegisteredContract {
    public interface View extends IBaseView<Presenter> {

        void changeVerifyStatus(CharSequence text);

        void setRegisteredResult(int resultCode, Intent data);

        Context getViewContext();
    }

    public interface Presenter extends IBasePresenter {
        void initUtils(Activity activity);

        void getVerifyCode();

        void registered(String user, String password, String verifyCode);
    }
}
