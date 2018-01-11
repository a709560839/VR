package com.daydvr.store.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.daydvr.store.R;
import com.daydvr.store.manager.ActivityManager;
import com.daydvr.store.util.AppInfoUtil;
import com.daydvr.store.util.DensityUtil;
import com.daydvr.store.view.custom.LoadingDialog;


/**
 * @author LoSyc
 * @version Created on 2017/12/25. 18:01
 */

public abstract class BaseActivity extends AppCompatActivity {

//    protected abstract int getContentViewId();
//
//    protected abstract int setFragmentContentId();

    private DensityUtil mDensityUtil = new DensityUtil();
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getManager().pushOneActivity(this);
        mDensityUtil.setImmerseState(this);
        mDensityUtil.statusBarLightMode(this);
        mLoadingDialog = new LoadingDialog(this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getManager().popOneActivity(this);

    }

//    protected void addDisplayerFragment(BaseFragment fragment) {
//        if (fragment != null) {
//            getFragmentManager().beginTransaction()
//                    .add(setFragmentContentId(), fragment)
//                    .addToBackStack(fragment.toString())
//                    .commit();
//        }
//    }

//    protected void removeFragment() {
//        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
//            getSupportFragmentManager().popBackStack();
//        } else {
//            finish();
//        }
//    }

    public void showLoadingDialog(){
        mLoadingDialog.show();
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.startAnim();
    }

    public void dismissLoadingDialog(){
        mLoadingDialog.dismiss();
    }
}
