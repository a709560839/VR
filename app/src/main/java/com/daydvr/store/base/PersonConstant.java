package com.daydvr.store.base;

import android.net.Uri;
import android.util.ArrayMap;
import android.util.SparseArray;
import com.daydvr.store.model.game.TestThread;

import java.util.Map;

/**
 * @author LoSyc
 * @version Created on 2018/1/6. 9:35
 */

public class PersonConstant {
    /**
     * 测试用数据
     */
    public static ArrayMap<String, String> loginTest = new ArrayMap<>();
    public static  String flagVerifyCode = "verifyCode";
    public static SparseArray<TestThread> threadTest = new SparseArray<>();

    //--------------------------------------------------------------------------------------------------------------------------------------------

    public static boolean isAllowRequestCode = true;
    public static boolean isLogin = false;
    public static Uri cutedPhotoUri;

    public static final String HEAD_OUTPUT_FILE = "head.png";

    public static final String USER_NAME = "user_name";
    public static final String USER_HEAD_URL = "user_head_url";
    public static final String USER_INTEGRAL = "user_integral";

    public static final String HEAD_CUT_URI = "head_cut_uri";

    public static final int LOGIN_REQUEST_CODE = 100;
    public static final int REGISTERED_REQUEST_CODE = 200;
    public static final int PICKER_PHOTO_REQUEST_CODE = 300;
    public static final int PICKER_CAMERA_REQUEST_CODE = 400;
    public static final int CUT_PICKER_PHOTO_REQUEST_CODE = 500;
    public static final int CHANGE_SEX_REQUEST_CODE = 600;
    public static final int CHANGE_BIRTHDAY_REQUEST_CODE = 700;
    public static final int CHANGE_PHONE_REQUEST_CODE = 800;

    public static final String USER_ACCOUNT = "user_account";
    public static final String USER_PASSWORD = "user_password";

    public static final int LOGIN_OK = 101;
    public static final int CHANGE_OK = 101;
}
