package com.daydvr.store.view.person;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.daydvr.store.R;
import com.daydvr.store.base.BaseActivity;
import com.daydvr.store.util.DensityUtil;
import com.daydvr.store.view.custom.CommonToolbar;

/**
 * @author a79560839
 */
public class SelectGenderActivity extends BaseActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private RadioButton radioButton1;
    private CommonToolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_gender);

        initView();
    }

    private void initView() {
        mToolBar = findViewById(R.id.toolbar);

        configComponent();
    }

    private void configComponent() {
        mToolBar.setPadding(0, DensityUtil.getStatusBarHeight(this),0,0);
        mToolBar.setCenterTitle(getResources().getString(R.string.person_change_gender));
        mToolBar.initmToolBar(this,false);
    }
}
