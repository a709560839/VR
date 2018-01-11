package com.daydvr.store.view.login;

import android.view.View;
import android.widget.ImageView;

import com.daydvr.store.base.BaseActivity;

/**
 * @author LoSyc
 * @version Created on 2018/1/6. 0:25
 */
public class BaseForLoginActivity extends BaseActivity {

    protected void initBackButton(ImageView back) {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
