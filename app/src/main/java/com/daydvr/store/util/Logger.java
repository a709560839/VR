package com.daydvr.store.util;

import android.util.Log;
import com.daydvr.store.BuildConfig;

/**
 * Copyright (C) 2017 3ivr. All rights reserved.
 *
 * Author: Jason(Liu ZhiCheng)
 * Mail  : jason@3ivr.cn
 * Date  : 2017/10/25 17:43
 *
 * 打包打印 Log 类，开关是BuildConfig.DEBUG
 * BuildConfig.DEBUG:  Gradle 默认生成的boolean 字段
 * 更多关于BuildConfigFiled的内容：
 * http://gudong.name/2015/09/13/gradlebuild-best-practice.html
 *
 *
 * BuildConfig 类文件的生成依据于 Module，每一个 Module 编译时都会产生自己的这个文件。
 * 问题：在默认情况下，Library 的构建永远是以 Release 模式执行的，
 * 即使主 Module 使用 Debug 模式构建，也是如此。
 *
 * 解决：打开对应 Library 的 build.gradle 文件，添加这样一行配置代码：
 * android {
 * //这里省略其他内容
 * publishNonDefault true
 * }
 *
 * 在我们的主 Module 依赖的时候同时引入 debug 和 release 两种配置，这里以 extras/PullToRefresh 作为 Library 为例
 * dependencies {
 * releaseCompile project(path: ':extras:PullToRefresh', configuration: 'release')
 * debugCompile project(path: ':extras:PullToRefresh', configuration: 'debug')
 * }
 **/
public class Logger {

    private static String defaultTag = "Logger";


    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, llog() + msg);
        }
    }

    public static void e(String tag, Object obj) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, llog() + printObj(obj));
        }
    }

    public static void e(String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(defaultTag, llog() + msg);
        }
    }

    public static void e(Object obj) {
        if (BuildConfig.DEBUG) {
            Log.e(defaultTag, llog() + printObj(obj));
        }
    }

    /*------------------------------------------------------------------------------*/

    public static void w(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, llog() + msg);
        }
    }

    public static void w(String tag, Object obj) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, llog() + printObj(obj));
        }
    }

    public static void w(String msg) {
        if (BuildConfig.DEBUG) {
            Log.w(defaultTag, llog() + msg);
        }
    }

    public static void w(Object obj) {
        if (BuildConfig.DEBUG) {
            Log.w(defaultTag, llog() + printObj(obj));
        }
    }

    /*------------------------------------------------------------------------------*/

    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, llog() + msg);
        }
    }

    public static void i(String tag, Object obj) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, llog() + printObj(obj));
        }
    }

    public static void i(String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(defaultTag, llog() + msg);
        }
    }

    public static void i(Object obj) {
        if (BuildConfig.DEBUG) {
            Log.i(defaultTag, llog() + printObj(obj));
        }
    }

    /*------------------------------------------------------------------------------*/


    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, llog() + msg);
        }
    }

    public static void d(String tag, Object obj) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, llog() + printObj(obj));
        }
    }

    public static void d(String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(defaultTag, llog() + msg);
        }
    }

    public static void d(Object obj) {
        if (BuildConfig.DEBUG) {
            Log.d(defaultTag, llog() + printObj(obj));
        }
    }

    /*------------------------------------------------------------------------------*/


    public static void v(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, llog() + msg);
        }
    }

    public static void v(String tag, Object obj) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, llog() + printObj(obj));
        }
    }

    public static void v(String msg) {
        if (BuildConfig.DEBUG) {
            Log.v(defaultTag, llog() + msg);
        }
    }

    public static void v(Object obj) {
        if (BuildConfig.DEBUG) {
            Log.v(defaultTag, llog() + printObj(obj));
        }
    }

    /*-------------------------------------------------------------------------------*/

    public static String llog() {

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int index = 4;
        String className = stackTrace[index].getFileName();
        String methodName = stackTrace[index].getMethodName();
        int lineNumber = stackTrace[index].getLineNumber();

        StringBuffer logStringBuffer = new StringBuffer();
        logStringBuffer.append("[(").append(className).append(":").append(lineNumber).append(")#")
                .append(methodName).append("]~~~                  ");

        return logStringBuffer.toString();
    }


    private static String printObj(Object obj) {
        String msg;
        if (obj == null) {
            msg = "Logger with null Object!";
        } else {
            msg = obj.toString();
        }
        return msg;
    }
}

