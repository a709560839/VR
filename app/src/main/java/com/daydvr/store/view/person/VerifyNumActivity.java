package com.daydvr.store.view.person;

import static com.daydvr.store.base.PersonConstant.CHANGE_OK;
import static com.daydvr.store.base.PersonConstant.CHANGE_PHONE_REQUEST_CODE;
import static com.daydvr.store.base.PersonConstant.PERSON_MSG_BIRTHDAY;
import static com.daydvr.store.base.PersonConstant.PERSON_MSG_TELEPHONE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.daydvr.store.R;
import com.daydvr.store.base.BaseActivity;
import com.daydvr.store.util.DensityUtil;
import com.daydvr.store.view.custom.CommonToolbar;

/**
 * @author a79560839
 */
public class VerifyNumActivity extends BaseActivity implements OnClickListener {

    private CommonToolbar mToolBar;

    private Button mDetermineButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_num);

        initView();
    }

    private void initView() {
        mToolBar = findViewById(R.id.toolbar);

        mDetermineButton = findViewById(R.id.bt_determine);

        configComponent();
    }

    private void configComponent() {
        mToolBar.setPadding(0, DensityUtil.getStatusBarHeight(this),0,0);
        mToolBar.setCenterTitle(getResources().getString(R.string.person_verify_num));
        mToolBar.initmToolBar(this,false);

        mDetermineButton.setOnClickListener(this);
    }

    private void setReturnResult(String data) {
        Intent intent = new Intent();
        intent.putExtra(PERSON_MSG_TELEPHONE,data);
        setResult(CHANGE_OK, intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CHANGE_OK) {
            switch (requestCode) {
                case CHANGE_PHONE_REQUEST_CODE:
                    if (data != null) {
                        setReturnResult(data.getStringExtra(PERSON_MSG_TELEPHONE));
                        finish();
                    }
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_determine:
                Intent intent = new Intent(VerifyNumActivity.this, ChangeNumActivity.class);
                startActivityForResult(intent,CHANGE_PHONE_REQUEST_CODE);
                break;

            default:
                break;

        }
    }
}
