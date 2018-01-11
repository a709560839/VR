package com.daydvr.store.base;

/**
 * @author LoSyc
 * @version Created on 2017/12/26. 14:24
 */

public class GameConstant {
    public static String FILE_SAVE_DIR;

    public static final int APKURL = 0;
    public static final int OBBURL = 1;
    public static final int APKPATH = 2;
    public static final int OBBPATH = 3;
    public static final int APKTASKID = 4;
    public static final int OBBTASKID = 5;

    public static final int ARCHIVER_START = 0x01;
    public static final int ARCHIVER_PROGRESS = 0x02;
    public static final int ARCHIVER_END = 0x03;

    public static final byte DOWNLOADABLE = 0x01 << 1;
    public static final byte UPDATE = 0x01 << 2;
    public static final byte NOT_ADAPTION = 0x01 << 3;
    public static final byte INSTALLED = 0x01 << 4;
    public static final byte INSTALLABLE = ~INSTALLED;
    public static final byte DOWNLOADING = 0x01 << 5;
    public static final byte PAUSED = ~DOWNLOADING;

    public static final String APK_ID = "APK_ID";
    public static final String GAME_DETAIL_PIC = "PAGE_POSITION";
    public static final String GAME_DETAIL_PIC_DATAS = "PAGE_DATAS";


    public static final String TEXT_DOWNLOAD = "下载";
    public static final String TEXT_PAUSE = "暂停";
    public static final String TEXT_INSTALL = "安装";
    public static final String TEXT_OPEN = "打开";
    public static final String TEXT_CONTINUE = "继续";
    public static final String TEXT_UPDATE = "更新";
    public static final String TEXT_ADAPTION = "适配";

}
