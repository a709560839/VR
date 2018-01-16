package com.daydvr.store.view.person;

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
public class SelectBirthdayActivity extends BaseActivity {

    private CommonToolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_birthday);

        initView();
    }

    private void initView() {
        mToolBar = findViewById(R.id.toolbar);

        configComponent();
    }

    private void configComponent() {
        mToolBar.setPadding(0, DensityUtil.getStatusBarHeight(this),0,0);
        mToolBar.setCenterTitle(getResources().getString(R.string.person_select_birthday));
        mToolBar.initmToolBar(this,false);

        Looper.myQueue().addIdleHandler(new IdleHandler() {
            @Override
            public boolean queueIdle() {
                initDatePicker();
                return false;
            }
        });
    }

    private void initDatePicker(){
        DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(SelectBirthdayActivity.this, new DatePickerPopWin.OnDatePickedListener() {
            @Override
            public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                Toast.makeText(SelectBirthdayActivity.this, dateDesc, Toast.LENGTH_SHORT).show();
            }
        }).textConfirm("确定") //text of confirm button
                .textCancel(" ")
                .btnTextSize(16) // button text size
                .viewTextSize(25) // pick view text size
                .colorCancel(Color.parseColor("#999999")) //color of cancel button
                .colorConfirm(Color.parseColor("#0ca4ea"))//color of confirm button
                .minYear(1900) //min year in loop
                .maxYear(2019) // max year in loop
                .dateChose("2018-1-1") // date chose when init popwindow
                .build();
        pickerPopWin.showPopWin(SelectBirthdayActivity.this);
    }
}
