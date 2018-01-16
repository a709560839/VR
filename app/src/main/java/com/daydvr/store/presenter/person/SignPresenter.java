package com.daydvr.store.presenter.person;
/*
 * Copyright (C) 2018 3ivr. All rights reserved.
 *
 * @author: Jason(Liu ZhiCheng)
 * @mail  : jason@3ivr.cn
 * @date  : 2018/01/16 15:12
 */

import static com.daydvr.store.base.BaseApplication.MultiThreadPool;
import static com.daydvr.store.base.BaseConstant.SIGN_DATA_LOADER_OK;

import android.os.Message;
import com.daydvr.store.bean.SignEntity;
import com.daydvr.store.model.sign.SignModel;
import com.daydvr.store.presenter.person.SignContract.Presenter;
import com.daydvr.store.presenter.person.SignContract.View;
import com.daydvr.store.util.LoaderHandler;
import com.daydvr.store.util.LoaderHandler.LoaderHandlerListener;
import com.daydvr.store.view.custom.SignView.DayType;
import java.util.Calendar;
import java.util.List;

public class SignPresenter implements Presenter {

    private SignContract.View mView;

    private LoaderHandler mHandler;

    private SignModel mSignModel;

    private List<SignEntity> data;

    private Calendar calendar;

    private int continueSignDay = -1;

    public SignPresenter(View mView) {
        this.mView = mView;
        mHandler = new LoaderHandler();
        mHandler.setListener(mHandlerListener);
        mSignModel = new SignModel();
        mSignModel.setHandler(mHandler);
        mView.setPresenter(this);
        calendar = Calendar.getInstance();
    }

    @Override
    public void freeView() {
        mView = null;
    }

    @Override
    public void showSignDay() {
        MultiThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                mSignModel.getSignDayData();
            }
        });
    }

    @Override
    public void signToday() {
        if(data!=null){
            data.get(calendar.get(Calendar.DAY_OF_MONTH)-1).setDayType(DayType.SIGNED.getValue());
        }
        mView.signToday(getContinueSignDay()+1);
    }

    @Override
    public String getYearAndMonth() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        StringBuffer time = new StringBuffer();
        time.append(year).append("年").append(month).append("月");
        return time.toString();
    }


    @Override
    public int getContinueSignDay() {
        return mSignModel.getContinueSignDay();
    }

    private LoaderHandler.LoaderHandlerListener mHandlerListener = new LoaderHandlerListener() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SIGN_DATA_LOADER_OK:
                     data = (List<SignEntity>) msg.obj;
                    if(data!=null&&mView!=null){
                        mView.showSignDay(data);
                    }
                    break;
                default:
                    break;
            }
        }
    };
}
