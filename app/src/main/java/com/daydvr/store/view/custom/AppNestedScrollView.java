package com.daydvr.store.view.custom;
/*
 * Copyright (C) 2017 3ivr. All rights reserved.
 *
 * Author: SJ
 * Mail  : soap@3ivr.cn
 * Date  : 2017/12/25 14:33
 */

import android.content.Context;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.daydvr.store.util.Logger;

public class AppNestedScrollView extends NestedScrollView {

    public static final String TAG = "daydvr.AppNestedScrollView";

    private NestedScrollViewListener mNestedScrollViewListener = null;

    public AppNestedScrollView(Context context) {
        super(context);
    }

    public AppNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AppNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setNestedScrollViewListener(NestedScrollViewListener nestedScrollViewListener) {
        this.mNestedScrollViewListener = nestedScrollViewListener;
    }

    @Override
    protected void onScrollChanged(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        super.onScrollChanged(scrollX, scrollY, oldScrollX, oldScrollY);
        if (mNestedScrollViewListener != null) {
            mNestedScrollViewListener.onNestedScrollChanged(this, scrollX, scrollY, oldScrollX, oldScrollY);
        }
    }

    public interface NestedScrollViewListener {
        void onNestedScrollChanged(AppNestedScrollView nestedScrollView, int scrollX, int scrollY,
                                   int oldScrollX, int oldScrollY);
    }
}
