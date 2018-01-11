package com.daydvr.store.base;

import com.daydvr.store.util.PermissionUtil;

/**
 * @author LoSyc
 * @version Created on 2017/12/28. 11:34
 */

public class BaseConstant {

    public static final int NOTIFY_ALL = -10;

    public static boolean IS_CANCELED_ALL_TASK = false;
    public static boolean GUIDE_CANCELED = false;
    public static boolean GAME_LIST_CANCELED = false;
    public static boolean DOWNLOAD_RANKING_CANCELED = false;
    public static boolean SORAING_RANKING_CANCELED = false;
    public static boolean NEWS_RANKING_CANCELED = false;

    public static int GUIDE_FRAGEMNT_ITEM = 0;
    public static int GAME_LIST_FRAGEMNT_ITEM = 1;
    public static int RANKING_LIST_FRAGEMNT_ITEM = 2;
    public static int PERSON_FRAGEMNT_ITEM = 3;

    public static final int AD_LOADER_OK = 1;
    public static final int GAME_LOADER_OK = 2;
    public static final int VIDEO_LOADER_OK = 3;
    public static final int SEARCH_LOADER_OK = 4;
    public static final int LOGIN_REQUEST_OK = 5;
    public static final int REGISTERED_REQUEST_OK = 6;
    public static final int VERIFYCODE_REQUEST_OK = 7;
    public static final int VERIFYCODE_TIMECHANGE_OK = 8;
    public static final int CHANGE_REQUEST_OK = 9;
    public static final int SHOW_SOFT_INPUT = 10;
    public static final int GAME_MANAGER_LOADER_OK = 11;
    public static final int SOARING_RANKING_LOADER_OK = 12;
    public static final int NEWS_RANKING_LOADER_OK = 13;
    public static final int DOWNLOAD_RANKING_LOADER_OK  = 14;

    public static final int GAME_DETAIL_OK = 51;
    public static final int GAME_DETAIL_PIC_OK = 52;

    public static final int GUIDE_UI_UPDATE = 100;
    public static final int GAME_LIST_UI_UPDATE = 101;
    public static final int GAME_MANAGER_UI_UPDATE = 102;
    public static final int SOARING_RANKING_UI_UPDATE = 103;
    public static final int NEWS_RANKING_UI_UPDATE = 104;
    public static final int DOWNLOAD_RANKING_UI_UPDATE = 105;


    public static final int NORMAL = 0;
    public static final int CATEGORY = 1;

    public static final int CATEGORY_SOARING = 1;
    public static final int CATEGORY_NEWS = 2;
    public static final int CATEGORY_DOWNLOAD = 3;

    /**
     * 已安装应用列表成功获取
     */
    public static final int APPLIST_LOADED_OK = 9;

    /**
     * 加载进度条动画重复次数
     */
    public static final int LOADING_PROGRESS_REPEAT_COUNT = 8;

    /**
     * 请求短信验证码的时间间隔 单位s
     */
    public static final int VERIFYCODE_REQUEST_TIME = 60;

    /**
     * 设置中检查版本中的key
     */
    public static final String SETTING_CHECK_VERSION_KEY ="check_update_perference";
}
