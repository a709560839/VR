package com.daydvr.store.view.person;

import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.daydvr.store.R;
import com.daydvr.store.base.BaseActivity;
import com.daydvr.store.bean.SignEntity;
import com.daydvr.store.view.adapter.SignAdapter;
import com.daydvr.store.view.custom.CommonToolbar;
import com.daydvr.store.view.custom.SignIntegralLayout;
import com.daydvr.store.view.custom.SignView;
import com.daydvr.store.view.custom.SignView.DayType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SignActivity extends BaseActivity {

    private SignView mSignView;
    private Button mSignButton;
    private SignIntegralLayout mSignIntegralLayout;
    private CommonToolbar mToolBar;
    private TextView mSignContinueTextView;
    private TextView mSignYearAndMonth;
    private SignAdapter signAdapter;

    private List<SignEntity> data;
    private int sign = 3;
    private int year;
    private int month;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
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
        mSignIntegralLayout.setSignDay(sign);
        setSignContinueText(sign);
        mSignButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ++sign;
                signToday(sign);
            }
        });
        StringBuffer time = new StringBuffer();
        time.append(year).append("年").append(month).append("月");
        mSignYearAndMonth.setText(time);
    }

    private void signToday(int signDay) {
        setSignContinueText(signDay);
        mSignIntegralLayout.setSignDay(signDay);
        data.get(mSignView.getDayOfMonthToday()-1).setDayType(DayType.SIGNED.getValue());
        mSignView.notifyDataSetChanged();
        mSignButton.setBackground(ContextCompat.getDrawable(SignActivity.this, R.drawable.shape_login_btn_clicked));
        mSignButton.setText(getResources().getString(R.string.sign_had));
        mSignButton.setClickable(false);
        mSignButton.setEnabled(false);
    }

    private void setSignContinueText(int day){
        mSignContinueTextView.setText(getResources().getString(R.string.sign_continue_sign).replace("?",String.valueOf(day)));
    }

    private void initData() {
        data = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        int dayOfMonthToday = calendar.get(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < 30; i++) {
            SignEntity signEntity = new SignEntity();
            if (dayOfMonthToday == i + 1) {
                /*今天*/
                signEntity.setDayType(DayType.WAITING.getValue());
            } else {
                /*今天之前*/
                signEntity.setDayType(DayType.SIGNED.getValue());
            }
            data.add(signEntity);
        }
        signAdapter = new SignAdapter(data);
    }
}
