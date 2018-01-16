package com.daydvr.store.presenter.person;
/*
 * Copyright (C) 2018 3ivr. All rights reserved.
 *
 * @author: Jason(Liu ZhiCheng)
 * @mail  : jason@3ivr.cn
 * @date  : 2018/01/16 20:35
 */

import android.content.Context;
import com.daydvr.store.base.IBasePresenter;
import com.daydvr.store.base.IBaseView;
import com.daydvr.store.presenter.person.PersonContract.Presenter;
import java.util.List;

public interface ExchangeContract {

     interface View extends IBaseView<Presenter>{

         <T> void showExchangeList(List<T> beans);

         Context getViewContext();

         void jumpGameDetail();
     }

     interface Presenter extends IBasePresenter{
         void loadExchangeAppList();

         void intoAppDetail(int position);
     }

}
