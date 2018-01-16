package com.daydvr.store.view.person;

import static com.daydvr.store.base.PersonConstant.CHANGE_OK;
import static com.daydvr.store.base.PersonConstant.PERSON_MSG_BIRTHDAY;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue.IdleHandler;
import android.widget.Toast;
import com.daydvr.store.R;
import com.daydvr.store.base.BaseActivity;
import com.daydvr.store.util.DensityUtil;
import com.daydvr.store.view.custom.CommonToolbar;
import com.daydvr.store.view.custom.datepicker.DatePickerPopWin;

/**
 * @author a79560839
 */
public class SelectBirthdayActivity extends BaseActivity implements IdleHandler {

    private CommonToolbar mToolBar;
    private String birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_birthday);

        initIntent();

        initView();
    }

    private void initIntent() {
        Intent intent = getIntent();
        birthday = intent.getStringExtra(PERSON_MSG_BIRTHDAY);
    }

    private void initView() {
        mToolBar = findViewById(R.id.toolbar);

        configComponent();
    }

    private void configComponent() {
        mToolBar.setPadding(0, DensityUtil.getStatusBarHeight(this), 0, 0);
        mToolBar.setCenterTitle(getResources().getString(R.string.person_select_birthday));
        mToolBar.initmToolBar(this, false);

        Looper.myQueue().addIdleHandler(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initDatePicker() {
        DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(SelectBirthdayActivity.this,
                new DatePickerPopWin.OnDatePickedListener() {
                    @Override
                    public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                        setReturnResult(dateDesc);
                        finish();
                    }
                }).textConfirm("确定")
                .textCancel(" ")
                .btnTextSize(16)
                .viewTextSize(25)
                .colorCancel(Color.parseColor("#999999"))
                .colorConfirm(Color.parseColor("#0ca4ea"))
                .minYear(1900)
                .maxYear(2019)
                .dateChose(birthday)
                .build();
        pickerPopWin.showPopWin(SelectBirthdayActivity.this);
    }

    private void setReturnResult(String data) {
        Intent intent = new Intent();
        intent.putExtra(PERSON_MSG_BIRTHDAY, data);
        setResult(CHANGE_OK, intent);
    }

    @Override
    public boolean queueIdle() {
        initDatePicker();
        return false;
    }
}
