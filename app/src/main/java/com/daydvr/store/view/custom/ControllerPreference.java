package com.daydvr.store.view.custom;
/*
 * Copyright (C) 2018 3ivr. All rights reserved.
 *
 * @author: Jason(Liu ZhiCheng)
 * @mail  : jason@3ivr.cn
 * @date  : 2018/01/16 16:44
 */

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.daydvr.store.R;

public class ControllerPreference extends Preference {


    private View mContentView;
    public ControllerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ControllerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ControllerPreference(Context context) {
        super(context);
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        super.onCreateView(parent);
        mContentView = LayoutInflater.from(getContext())
                .inflate(R.layout.controller_preference, parent, false);
        return mContentView;
    }
}
