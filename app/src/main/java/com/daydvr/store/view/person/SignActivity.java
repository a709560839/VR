package com.daydvr.store.view.person;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.daydvr.store.R;
import com.daydvr.store.base.BaseActivity;
import com.daydvr.store.bean.SignEntity;
import com.daydvr.store.presenter.person.SignContract;
import com.daydvr.store.presenter.person.SignContract.Presenter;
import com.daydvr.store.presenter.person.SignPresenter;
import com.daydvr.store.view.adapter.SignAdapter;
import com.daydvr.store.view.custom.CommonToolbar;
import com.daydvr.store.view.custom.SignIntegralLayout;
import com.daydvr.store.view.custom.SignView;
import com.daydvr.store.view.custom.SignView.DayType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SignActivity extends BaseActivity implements SignContract.View{

    private SignView mSignView;
    private Button mSignButton;
    private SignIntegralLayout mSignIntegralLayout;
    private CommonToolbar mToolBar;
    private TextView mSignContinueTextView;
    private TextView mSignYearAndMonth;
    private SignAdapter signAdapter;

    private SignPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        mPresenter = new SignPresenter(this);
        initView();
        initData();
        configComponent();

    }

    private void initView() {
        mSignView = findViewById(R.id.sv_sign);
        mToolBar = findViewById(R.id.toolbar);
        mSignButton = findViewById(R.id.bt_sign);
        mSignIntegralLayout = findViewById(R.id.sil_cus);
        mSignContinueTextView = findViewById(R.id.tv_sign_continue);
        mSignYearAndMonth = findViewById(R.id.tv_sign_year_month);
    }

    private void configComponent() {
        mSignView.setAdapter(signAdapter);
        mToolBar.setCenterTitle(getResources().getString(R.string.sign_everyday));
        mToolBar.initmToolBar(this,false);
        mSignIntegralLayout.setSignDay(mPresenter.getContinueSignDay());
        setSignContinueText(mPresenter.getContinueSignDay());
        mSignButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.signToday();
            }
        });
        mSignYearAndMonth.setText(mPresenter.getYearAndMonth());
    }


    private void setSignContinueText(int day){
        mSignContinueTextView.setText(getResources().getString(R.string.sign_continue_sign).replace("?",String.valueOf(day)));
    }

    private void initData() {
        mPresenter.showSignDay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.freeView();
        mPresenter = null;
    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = (SignPresenter) presenter;
    }

    @Override
    public <T> void showSignDay(List<T> beans) {
        signAdapter = new SignAdapter((List<SignEntity>) beans);
        mSignView.setAdapter(signAdapter);
    }

    @Override
    public void signToday(int signDay) {
        setSignContinueText(signDay);
        mSignIntegralLayout.setSignDay(signDay);
        mSignView.notifyDataSetChanged();
        mSignButton.setBackground(ContextCompat.getDrawable(SignActivity.this, R.drawable.shape_login_btn_clicked));
        mSignButton.setText(getResources().getString(R.string.sign_had));
        mSignButton.setClickable(false);
        mSignButton.setEnabled(false);
    }

    @Override
    public Context getViewContext() {
        return this;
    }

}
