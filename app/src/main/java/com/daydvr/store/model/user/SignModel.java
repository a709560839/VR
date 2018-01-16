package com.daydvr.store.model.user;
/*
 * Copyright (C) 2018 3ivr. All rights reserved.
 *
 * @author: Jason(Liu ZhiCheng)
 * @mail  : jason@3ivr.cn
 * @date  : 2018/01/16 15:24
 */

import static com.daydvr.store.base.BaseConstant.SIGN_DATA_LOADER_OK;

import android.os.Message;
import com.daydvr.store.bean.SignEntity;
import com.daydvr.store.util.LoaderHandler;
import com.daydvr.store.view.custom.SignView.DayType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SignModel {

    private LoaderHandler mHandler;

    public void getSignDayData(){
        List<SignEntity> data = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
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

        if (mHandler != null) {
            Message msg = mHandler.createMessage(SIGN_DATA_LOADER_OK, 0, 0, data);
            mHandler.sendMessage(msg);
        }
    }

    public int getContinueSignDay(){
        return 3;
    }

    public void setHandler(LoaderHandler handler) {
        mHandler = handler;
    }
}
