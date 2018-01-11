package com.daydvr.store.recevice;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/*
 * Copyright (C) 2017 3ivr. All rights reserved.
 *
 * Author: Jason(Liu ZhiCheng)
 * Mail  : jason@3ivr.cn
 * Date  : 2017/10/17 19:36
 */

public class BluetoothReceiver extends BroadcastReceiver {


  public BluetoothReceiver() {
     super();
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    switch (intent.getAction()) {
      case BluetoothAdapter.ACTION_STATE_CHANGED:
        int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
        switch (blueState) {
          case BluetoothAdapter.STATE_TURNING_ON:
            break;

          case BluetoothAdapter.STATE_ON:
            break;

          case BluetoothAdapter.STATE_TURNING_OFF:
            break;

          case BluetoothAdapter.STATE_OFF:
            break;

          default:
            break;
        }
       break;

      case BluetoothDevice.ACTION_ACL_CONNECTED:
       break;

      case BluetoothDevice.ACTION_ACL_DISCONNECTED:
        break;

      default:
        break;
    }
  }
}
