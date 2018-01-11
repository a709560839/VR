package com.daydvr.store.base;

import android.util.ArrayMap;
import android.util.SparseArray;
import com.daydvr.store.model.game.TestThread;

/**
 * @author LoSyc
 * @version Created on 2018/1/6. 9:35
 */

public class LoginConstant {
    /**
     * 测试用数据
     */
    public static ArrayMap<String, String> loginTest = new ArrayMap<>();
    public static  String flagVerifyCode = "verifyCode";
    public static SparseArray<TestThread> threadTest = new SparseArray<>();

    //--------------------------------------------------------------------------------------------------------------------------------------------

    public static boolean IsAllowRequestCode = true;
    public static boolean isLogin = false;

    public static final String USER_NAME = "user_name";
    public static final String USER_HEAD_URL = "user_head_url";
    public static final String USER_REGISTERED_TIME = "user_registered_time";

    public static final int LOGIN_REQUEST_CODE = 100;
    public static final int REGISTERED_REQUEST_CODE = 200;

    public static final String USER_ACCOUNT = "user_account";
    public static final String USER_PASSWORD = "user_password";

    public static final int LOGIN_OK = 101;
}
