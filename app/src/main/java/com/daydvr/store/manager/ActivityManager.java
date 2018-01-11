package com.daydvr.store.manager;

import android.app.Activity;

import com.daydvr.store.util.Logger;

import java.util.Stack;

/**
 * @author LoSyc
 * @version Created on 2017/12/25. 18:02
 */

public class ActivityManager {
    public static final String TAG = "daydvr.ActivityManager";

    private static ActivityManager mManager;
    private Stack<Activity> mStack;

    private ActivityManager() {
    }

    public static ActivityManager getManager() {
        if (mManager == null) {
            mManager = new ActivityManager();
        }
        return mManager;
    }

    /**
     * 把一个activity压入栈中
     *
     * @param activity
     */
    public void pushOneActivity(Activity activity) {
        if (mStack == null) {
            mStack = new Stack<Activity>();
        }
        mStack.add(activity);
        Logger.d(TAG, "size = " + mStack.size());
    }

    /**
     * 获取栈顶的activity，先进后出原则
     *
     * @return
     */
    public Activity getLastActivity() {
        return mStack.lastElement();
    }

    /**
     * 移除一个activity
     *
     * @param activity
     */
    public void popOneActivity(Activity activity) {
        if (mStack != null && mStack.size() > 0) {
            if (activity != null) {
                activity.finish();
                mStack.remove(activity);
                activity = null;
            }
        }
    }

    /**
     * 退出所有activity
     */
    public void finishAllActivity() {
        if (mStack != null) {
            while (mStack.size() > 0) {
                Activity activity = getLastActivity();
                if (activity == null) {
                    break;
                }
                popOneActivity(activity);
            }
        }
    }
}


