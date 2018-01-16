package com.daydvr.store.view.person;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.daydvr.store.R;
import com.daydvr.store.base.BaseActivity;
import com.daydvr.store.view.custom.CommonToolbar;

public class IntegralRuleActivity extends BaseActivity {

    private CommonToolbar mToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral_rule);
        initView();
    }

    private void initView() {
        mToolBar = findViewById(R.id.toolbar);
        configComponent();
    }

    private void configComponent() {
        mToolBar.setCenterTitle(getResources().getString(R.string.integral_rule_toolbar));
        mToolBar.initmToolBar(this,false);
    }
}
