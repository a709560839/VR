package com.daydvr.store.util;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;


/**
 * @author LoSyc
 * @version Created on 2017/12/27. 23:43
 */

public class PermissionUtil {

    public static void getPermission(final Activity activity) {
        if (!AndPermission.hasPermission(activity, Permission.STORAGE)) {
            AndPermission.with(activity)
                    .requestCode(100)
                    .permission(Permission.STORAGE)
                    .callback(new PermissionListener() {
                        @Override
                        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        }

                        @Override
                        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                            // 权限申请失败回调。

                            if (AndPermission.hasAlwaysDeniedPermission(activity, deniedPermissions)) {
                                // 第一种：用默认的提示语。
                                AndPermission.defaultSettingDialog(activity, 300).show();
                            }
                        }
                    }).start();
        }
        if (!AndPermission.hasPermission(activity, Permission.CAMERA)) {
            AndPermission.with(activity)
                    .requestCode(100)
                    .permission(Permission.CAMERA)
                    .callback(new PermissionListener() {
                        @Override
                        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        }

                        @Override
                        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                            // 权限申请失败回调。

                            if (AndPermission.hasAlwaysDeniedPermission(activity, deniedPermissions)) {
                                // 第一种：用默认的提示语。
                                AndPermission.defaultSettingDialog(activity, 300).show();
                            }
                        }
                    }).start();
        }
    }
}
