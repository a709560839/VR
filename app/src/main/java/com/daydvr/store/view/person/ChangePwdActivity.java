package com.daydvr.store.view.person;

import android.os.Bundle;
import com.daydvr.store.R;
import com.daydvr.store.base.BaseActivity;
import com.daydvr.store.util.DensityUtil;
import com.daydvr.store.view.custom.CommonToolbar;

/**
 * @author a79560839
 */
public class ChangePwdActivity extends BaseActivity {

    private CommonToolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        initView();
    }

    private void initView() {
        mToolBar = findViewById(R.id.toolbar);

        configComponent();
    }

    private void configComponent() {
        mToolBar.setPadding(0, DensityUtil.getStatusBarHeight(this),0,0);
        mToolBar.setCenterTitle(getResources().getString(R.string.person_change_pwd));
        mToolBar.initmToolBar(this,false);
    }
}
