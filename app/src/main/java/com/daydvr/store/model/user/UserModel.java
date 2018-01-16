package com.daydvr.store.model.user;

import android.os.Message;

import com.daydvr.store.bean.UserBean;
import com.daydvr.store.util.LoaderHandler;

import java.util.Map;

import static com.daydvr.store.base.BaseConstant.LOGIN_REQUEST_OK;
import static com.daydvr.store.base.BaseConstant.USER_MSEEAGE_LOADER_OK;
import static com.daydvr.store.base.PersonConstant.loginTest;

/**
 * @author LoSyc
 * @version Created on 2018/1/16. 17:12
 */

public class UserModel {
    private LoaderHandler mHandler;

    public void verifyLogin(String user, String password) {
        for (Map.Entry<String, String> entry : loginTest.entrySet()) {
            if (entry.getKey().equals(user) && entry.getValue().equals(password)) {
                Message msg = mHandler.createMessage(LOGIN_REQUEST_OK, 0, 0, true);
                mHandler.sendMessage(msg);
                return;
            }
        }
        Message msg = mHandler.createMessage(LOGIN_REQUEST_OK, 0, 0, false);
        mHandler.sendMessage(msg);
    }

    public void getUserMessage() {
        UserBean bean = UserBean.getInstance();
        bean.setId(123);
        bean.setBirthday("2018-01-01");
        bean.setGender(1);
        bean.setTelephone("13333333333");
        bean.setAvatarUrl("https://img.tapimg.com/market/lcs/9e1328b55fab10aa59af1dd3273ee401_360.png");
        bean.setUserName("假的！");
        bean.setIntegral(1024);

        if (mHandler != null) {
            Message msg = mHandler.createMessage(USER_MSEEAGE_LOADER_OK, 0, 0, bean);
            mHandler.sendMessage(msg);
        }
    }

    public void setHandler(LoaderHandler handler) {
        mHandler = handler;
    }
}
