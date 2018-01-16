package com.daydvr.store.view.person;

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
    private TextView mNumTextView;
    private String num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_num);

        Intent intent = getIntent();
        num = intent.getStringExtra("phoneNum");

        initView();
    }

    private void initView() {
        mToolBar = findViewById(R.id.toolbar);

        mNumTextView = findViewById(R.id.ed_person_verify_num);
        mDetermineButton = findViewById(R.id.bt_determine);

        configComponent();
    }

    private void configComponent() {
        mToolBar.setPadding(0, DensityUtil.getStatusBarHeight(this),0,0);
        mToolBar.setCenterTitle(getResources().getString(R.string.person_verify_num));
        mToolBar.initmToolBar(this,false);

        mNumTextView.setText(num);

        mDetermineButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_determine:
                Intent intent = new Intent(VerifyNumActivity.this, ChangeNumActivity.class);
                startActivity(intent);
                finish();
                break;

            default:
                break;

        }
    }
}
