package com.daydvr.store.presenter.person;
/*
 * Copyright (C) 2018 3ivr. All rights reserved.
 *
 * @author: Jason(Liu ZhiCheng)
 * @mail  : jason@3ivr.cn
 * @date  : 2018/01/16 15:01
 */

import android.content.Context;
import com.daydvr.store.base.IBasePresenter;
import com.daydvr.store.base.IBaseView;
import com.daydvr.store.presenter.person.PersonContract.Presenter;
import java.util.List;

public class SignContract {

    public interface View extends IBaseView<Presenter> {

        <T> void showSignDay(List<T> beans);

        void signToday(int signDay);

        Context getViewContext();

    }

    public interface Presenter extends IBasePresenter{

        void showSignDay();

        void signToday();

        String getYearAndMonth();

        int getContinueSignDay();
    }

}
