package com.daydvr.store.recevice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.daydvr.store.util.Logger;

/*
 * Copyright (C) 2017 3ivr. All rights reserved.
 *
 * Author: Jason(Liu ZhiCheng)
 * Mail  : jason@3ivr.cn
 * Date  : 2017/10/17 21:07
 */

public class BatteryReceiver extends BroadcastReceiver {

  private static final String TAG = "BatteryReceiver";

  public static int BATTERY_PERCENT;

  public BatteryReceiver() {
    super();
  }

  @Override
  public void onReceive(Context context, Intent intent) {

    if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
      // 获得当前电量
      int current = intent.getExtras().getInt(BatteryManager.EXTRA_LEVEL);
      // 获得总电量
      int total = intent.getExtras().getInt(BatteryManager.EXTRA_SCALE);
      //電量百分比
      BATTERY_PERCENT = current * 100 / total;           
      Logger.d(TAG, String.valueOf(BATTERY_PERCENT));
    }
  }
}
