package com.daydvr.store.view.login;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daydvr.store.R;
import com.daydvr.store.presenter.login.ForgotPasswordContract;
import com.daydvr.store.presenter.login.ForgotPasswordPresenter;

public class ForgotPasswordActivity extends BaseForLoginActivity implements ForgotPasswordContract.View, OnClickListener {

    ForgotPasswordPresenter mPresenter;

    private EditText mUserAccountEditText;
    private EditText mUserPasswordEditText;
    private EditText mVerifyCodeEditText;
    private TextView mGetVerifyCodeTextView;
    private Button mChangePasswordButton;
    private ImageView mBackImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mPresenter = new ForgotPasswordPresenter(this);
        initView();
    }

    private void initView() {
        mBackImageView = findViewById(R.id.iv_forgot_pw_back);
        mUserAccountEditText = findViewById(R.id.et_forgot_pw_account);
        mUserPasswordEditText = findViewById(R.id.et_forgot_pw_password);
        mVerifyCodeEditText = findViewById(R.id.et_login_verification_code);
        mGetVerifyCodeTextView = findViewById(R.id.tv_forgot_pw_getcode);
        mChangePasswordButton = findViewById(R.id.bt_forgot_pw_confirm);

        configComponent();
    }

    private void configComponent() {
        super.initBackButton(mBackImageView);
        mGetVerifyCodeTextView.setOnClickListener(this);
        mChangePasswordButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_forgot_pw_getcode:
                mPresenter.getVerifyCode();
                break;

                case R.id.bt_forgot_pw_confirm:
                    String account = mUserAccountEditText.getText().toString();
                    String password = mUserPasswordEditText.getText().toString();
                    String verifyCode = mVerifyCodeEditText.getText().toString();

                    mPresenter.changePassword(account, password, verifyCode);
                    mUserPasswordEditText.setText("");
                    mVerifyCodeEditText.setText("");
            break;

            default:
                break;
        }
    }

    @Override
    public void setPresenter(ForgotPasswordContract.Presenter presenter) {
        mPresenter = (ForgotPasswordPresenter) presenter;
    }

    @Override
    public void changeVerifyStatus(CharSequence text) {
        mGetVerifyCodeTextView.setText(text);
    }

    @Override
    public void changeSuccess() {
        this.finish();
    }

    @Override
    public Context getViewContext() {
        return this;
    }
}
