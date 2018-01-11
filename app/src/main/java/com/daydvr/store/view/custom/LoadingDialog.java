package com.daydvr.store.view.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import com.daydvr.store.R;

/*
 * Copyright (C) 2017 3ivr. All rights reserved.
 *
 * @author: Jason(Liu ZhiCheng)
 * @mail  : jason@3ivr.cn
 * @date  : 2017/12/29 19:54
 */

public class LoadingDialog extends Dialog {

    private CircularSmileLv mLodingSmile;

    public LoadingDialog(Context context) {
        super(context, R.style.LoadingDialog);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        initView();
    }

    private void initView() {
        mLodingSmile = findViewById(R.id.lvc_smile_loading);
        mLodingSmile.setDialogDismiss(this);
    }

    public void startAnim(){
        mLodingSmile.startAnim();
    }

    public void stopAnim(){
        mLodingSmile.stopAnim();

    }

}
