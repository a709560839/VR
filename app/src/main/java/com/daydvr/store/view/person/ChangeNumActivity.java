package com.daydvr.store.view.person;

import static com.daydvr.store.base.PersonConstant.CHANGE_OK;
import static com.daydvr.store.base.PersonConstant.PERSON_MSG_TELEPHONE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.daydvr.store.R;
import com.daydvr.store.base.BaseActivity;
import com.daydvr.store.util.DensityUtil;
import com.daydvr.store.view.custom.CommonToolbar;

/**
 * @author a79560839
 */
public class ChangeNumActivity extends BaseActivity implements OnClickListener{

    private CommonToolbar mToolBar;
    private EditText mPhoneNumEditText;
    private Button mDetermineButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_number);

        initView();
    }

    private void initView() {
        mToolBar = findViewById(R.id.toolbar);

        mPhoneNumEditText = findViewById(R.id.ed_person_change_num);
        mDetermineButton = findViewById(R.id.bt_determine);

        configComponent();
    }

    private void configComponent() {
        mToolBar.setPadding(0, DensityUtil.getStatusBarHeight(this),0,0);
        mToolBar.setCenterTitle(getResources().getString(R.string.person_change_num));
        mToolBar.initmToolBar(this,false);

        mDetermineButton.setOnClickListener(this);
    }

    private void setReturnResult(String data) {
        Intent intent = new Intent();
        intent.putExtra(PERSON_MSG_TELEPHONE,data);
        setResult(CHANGE_OK, intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_determine:
//                if () {
//                }
                setReturnResult(mPhoneNumEditText.getText().toString());
                finish();
                break;

            default:
                break;

        }
    }
}
