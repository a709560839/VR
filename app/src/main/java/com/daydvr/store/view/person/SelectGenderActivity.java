package com.daydvr.store.view.person;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.daydvr.store.R;
import com.daydvr.store.base.BaseActivity;
import com.daydvr.store.util.DensityUtil;
import com.daydvr.store.view.custom.CommonToolbar;

import static com.daydvr.store.base.PersonConstant.CHANGE_OK;
import static com.daydvr.store.base.PersonConstant.PERSON_MSG_GENDER;

/**
 * @author a79560839
 */
public class SelectGenderActivity extends BaseActivity implements View.OnClickListener {

    private RadioButton mMaleRadioButton;
    private RadioButton mFemaleRadioButton;
    private Button mChangeButton;
    private CommonToolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_gender);

        initView();
    }

    private void initView() {
        mToolBar = findViewById(R.id.toolbar);
        mMaleRadioButton = findViewById(R.id.change_male);
        mFemaleRadioButton = findViewById(R.id.change_female);
        mChangeButton = findViewById(R.id.bt_determine);

        configComponent();
    }

    private void configComponent() {
        mToolBar.setPadding(0, DensityUtil.getStatusBarHeight(this),0,0);
        mToolBar.setCenterTitle(getResources().getString(R.string.person_change_gender));
        mToolBar.initmToolBar(this,true);

        mChangeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        if (mFemaleRadioButton.isChecked()) {
            intent.putExtra(PERSON_MSG_GENDER, "女");
        }
        if (mMaleRadioButton.isChecked()) {
            intent.putExtra(PERSON_MSG_GENDER, "男");
        }
        SelectGenderActivity.this.setResult(CHANGE_OK, intent);
        finish();
    }
}
