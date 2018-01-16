package com.daydvr.store.view.custom;
/*
 * Copyright (C) 2018 3ivr. All rights reserved.
 *
 * @author: Jason(Liu ZhiCheng)
 * @mail  : jason@3ivr.cn
 * @date  : 2018/01/15 15:52
 */

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.daydvr.store.R;

public class SignIntegralLayout extends ConstraintLayout {

    private final int NORMAL = 0;
    private final int PROGRESS = 1;
    private ProgressBar mProgress;
    private TextView mRoundSign1;
    private TextView mRoundSign2;
    private TextView mRoundSign3;
    private TextView mRoundSign4;

    public SignIntegralLayout(Context context) {
        this(context, null);
    }

    public SignIntegralLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignIntegralLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.groud_sign_integral, this, true);
        mProgress = findViewById(R.id.pb_sign);
        mProgress.setMax(100);
        mRoundSign1 = findViewById(R.id.tv_sign_first_round);
        mRoundSign2 = findViewById(R.id.tv_sign_second_round);
        mRoundSign3 = findViewById(R.id.tv_sign_third_round);
        mRoundSign4 = findViewById(R.id.tv_sign_fourth_round);
    }

    public void setSignDay(int signDay){
        if(signDay == 0){
            mProgress.setProgress(0);
            roundBackground(NORMAL,mRoundSign1,mRoundSign2,mRoundSign3,mRoundSign4);
        }
        if(signDay>=1){
            roundBackground(PROGRESS,mRoundSign1);
        }
        if(signDay>=2){
            mProgress.setProgress(33);
        }
        if(signDay>=3){
            roundBackground(PROGRESS,mRoundSign2);
        }
        if(signDay>=4){
            mProgress.setProgress(66);
        }
        if(signDay>=5){
            roundBackground(PROGRESS,mRoundSign3);
        }
        if(signDay>=6){
            mProgress.setProgress(100);
        }
        if(signDay>=7){
            roundBackground(PROGRESS,mRoundSign4);
        }
    }

    private void roundBackground(int type,TextView... roundSign){
        if(type==NORMAL){
            for (TextView item :roundSign){
                item.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.shape_sign_integral_normal));
            }

        }
        if(type == PROGRESS){
            for (TextView item :roundSign){
                item.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.shape_sign_integral));
            }
        }
    }
}
