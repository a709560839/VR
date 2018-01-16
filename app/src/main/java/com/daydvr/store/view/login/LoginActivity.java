package com.daydvr.store.view.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daydvr.store.R;
import com.daydvr.store.presenter.login.LoginContract;
import com.daydvr.store.presenter.login.LoginPresenter;

import static com.daydvr.store.base.PersonConstant.LOGIN_OK;
import static com.daydvr.store.base.PersonConstant.REGISTERED_REQUEST_CODE;
import static com.daydvr.store.base.PersonConstant.USER_ACCOUNT;
import static com.daydvr.store.base.PersonConstant.USER_PASSWORD;
import static com.daydvr.store.base.PersonConstant.isLogin;

public class LoginActivity extends BaseForLoginActivity implements LoginContract.View, OnClickListener {

    private LoginPresenter mPresenter;

    private TextView mRegisteredTextView;
    private TextView mForgotPasswordTextView;
    private ImageView mBackImageView;
    private ImageView mQQLoginImageView;
    private ImageView mWeChatLoginImageView;

    private EditText mUserAccountEditText;
    private EditText mUserPasswordEditText;
    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPresenter = new LoginPresenter(this);

        initView();
    }

    private void initView() {
        mRegisteredTextView = findViewById(R.id.tv_registered);
        mForgotPasswordTextView = findViewById(R.id.tv_forgot_password);
        mBackImageView = findViewById(R.id.iv_login_back);
        mQQLoginImageView = findViewById(R.id.iv_qq_login);
        mWeChatLoginImageView = findViewById(R.id.iv_wechat_login);
        mUserAccountEditText = findViewById(R.id.et_login_account);
        mUserPasswordEditText = findViewById(R.id.et_login_password);
        mLoginButton = findViewById(R.id.bt_login);

        configComponent();
    }

    private void configComponent() {
        mRegisteredTextView.setOnClickListener(this);
        mForgotPasswordTextView.setOnClickListener(this);
        super.initBackButton(mBackImageView);
        mQQLoginImageView.setOnClickListener(this);
        mWeChatLoginImageView.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.freeView();
        mPresenter = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_registered:
                mPresenter.intoRegisterView();
                break;

            case R.id.tv_forgot_password:
                mPresenter.intoForgotPasswordView();
                break;

            case R.id.iv_qq_login:
                mPresenter.intoQQLogin();
                break;

            case R.id.iv_wechat_login:
                mPresenter.intoWeChatLogin();
                break;

            case R.id.bt_login:
                String user = mUserAccountEditText.getText().toString();
                String password = mUserPasswordEditText.getText().toString();
                mPresenter.login(user, password);
                mUserAccountEditText.setText("");
                mUserPasswordEditText.setText("");
                break;

            default:
                break;
        }
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = (LoginPresenter) presenter;
    }

    @Override
    public void jumpForgotPasswordView() {
        Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public void jumpRegisterView() {
        Intent intent = new Intent(LoginActivity.this, RegisteredActivity.class);
        startActivityForResult(intent, REGISTERED_REQUEST_CODE);
    }

    @Override
    public void jumpQQLogin() {

    }

    @Override
    public void jumpWeChatLogin() {

    }

    @Override
    public void setLoginResult(int resultCode, Intent data) {
        if (data != null) {
            setResult(resultCode, data);
        } else {
            setResult(resultCode);
        }
        isLogin = true;
        finish();
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == LOGIN_OK) {
            switch (requestCode) {
                case REGISTERED_REQUEST_CODE:
                    if (data != null) {
                        String user = data.getStringExtra(USER_ACCOUNT);
                        String password = data.getStringExtra(USER_PASSWORD);
                        mPresenter.login(user, password);
                    }
                    break;

                default:
                    break;
            }
        }
    }
}
