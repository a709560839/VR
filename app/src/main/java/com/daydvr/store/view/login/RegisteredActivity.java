package com.daydvr.store.view.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daydvr.store.R;
import com.daydvr.store.presenter.login.RegisteredContract;
import com.daydvr.store.presenter.login.RegisteredPresenter;

public class RegisteredActivity extends BaseForLoginActivity implements RegisteredContract.View, OnClickListener {

    private RegisteredPresenter mPresenter;
    private ImageView mBackImageView;
    private TextView mHadAccountTextView;
    private EditText mUserAccountEditText;
    private EditText mUserPasswordEditText;
    private EditText mVerifyCodeEditText;
    private Button mRegisteredButton;
    private TextView mGetVerifyCodeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        mPresenter = new RegisteredPresenter(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.freeView();
        mPresenter = null;
    }

    private void initView() {
        mBackImageView = findViewById(R.id.iv_registered_back);
        mHadAccountTextView = findViewById(R.id.tv_had_account);
        mUserAccountEditText = findViewById(R.id.et_registered_account);
        mUserPasswordEditText = findViewById(R.id.et_registered_password);
        mVerifyCodeEditText = findViewById(R.id.et_login_verification_code);
        mRegisteredButton = findViewById(R.id.bt_registered);
        mGetVerifyCodeTextView = findViewById(R.id.tv_registered_getcode);

        configComponent();
    }

    private void configComponent() {
        super.initBackButton(mBackImageView);
        mHadAccountTextView.setOnClickListener(this);
        mRegisteredButton.setOnClickListener(this);
        mGetVerifyCodeTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_had_account:
                finish();
                break;

            case R.id.bt_registered:
                String account = mUserAccountEditText.getText().toString();
                String password = mUserPasswordEditText.getText().toString();
                String verifyCode = mVerifyCodeEditText.getText().toString();

                mPresenter.registered(account, password, verifyCode);
                break;

            case R.id.tv_registered_getcode:
                if (!TextUtils.isEmpty(mUserAccountEditText.getText().toString())) {
                    mPresenter.getVerifyCode();
                } else {
                    new AlertDialog.Builder(this)
                            .setMessage("请输入手机号！")
                            .setPositiveButton("确定", null)
                            .show();
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void setPresenter(RegisteredContract.Presenter presenter) {
        mPresenter = (RegisteredPresenter) presenter;
    }

    @Override
    public void changeVerifyStatus(CharSequence text) {
        mGetVerifyCodeTextView.setText(text);
    }

    @Override
    public void setRegisteredResult(int resultCode, Intent data) {
        if (data != null) {
            setResult(resultCode, data);
        } else {
            setResult(resultCode);
        }
        finish();
    }

    @Override
    public Context getViewContext() {
        return this;
    }
}
