package com.daydvr.store.service;

import android.accessibilityservice.AccessibilityService;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.daydvr.store.R;
import com.daydvr.store.util.Logger;

import java.util.List;


public class AuxiliaryService extends AccessibilityService {


    private static final String TAG = "AuxiliaryService";

    private static final String CLASS_NAME_BUTTON = "android.widget.Button";

    private static final String INSTALLER_PACKAGE_NAME_ANDROID = "com.android.packageinstaller";

    private static final String INSTALLER_PACKAGE_NAME_SUMSUNG = "com.samsung.android.packageinstaller";

    private static final String INSTALLER_PACKAGE_NAME_GOOGLE = "com.google.android.packageinstaller";

    private static final String INSTALLER_PACKAGE_NAME_LENOVO = "com.lenovo.safecenter";

    String[] tags;

    public AuxiliaryService() {
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            return;
        }
        tags = new String[]{
                getResources().getString(R.string.install),
                getResources().getString(R.string.continue_install),
                getResources().getString(R.string.next),
                getResources().getString(R.string.done),
                getResources().getString(R.string.confirm)
        };
        processAccessibilityEvent(event);
    }

    @Override
    public void onInterrupt() {
    }

    public static void reset() {
//    INVOKE_TYPE = 0;
    }

    private void processAccessibilityEvent(AccessibilityEvent event) {

        if (event.getSource() == null) {
            Logger.d(TAG, "the source = null");
        } else {
            processApplication(event);
        }
    }


    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        return true;
    }


    /**
     * 应用事件点击处理
     */
    private void processApplication(AccessibilityEvent event) {

        if (event.getSource() != null) {
            if (INSTALLER_PACKAGE_NAME_ANDROID.equals(event.getPackageName()) |
                    INSTALLER_PACKAGE_NAME_SUMSUNG.equals(event.getPackageName()) |
                    INSTALLER_PACKAGE_NAME_GOOGLE.equals(event.getPackageName()) |
                    INSTALLER_PACKAGE_NAME_LENOVO.equals(event.getPackageName())) {
                nodePerformActions(event, CLASS_NAME_BUTTON, tags);
                nodePerformActionById(event, "com.android.packageinstaller:id/ok_button");
                nodePerformActionById(event, "com.android.packageinstaller:id/done_button");
            }
        }
    }

    /**
     * 根据tag来获取
     */
    private void nodePerformActionByTag(AccessibilityEvent event, String clsName, String tag) {
        List<AccessibilityNodeInfo> nodes = event.getSource()
                .findAccessibilityNodeInfosByText(tag);
        if (nodes != null && !nodes.isEmpty()) {
            AccessibilityNodeInfo node;
            for (int i = 0; i < nodes.size(); i++) {
                node = nodes.get(i);
                if (node.getClassName().equals(clsName)) {
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    Logger.d(TAG, "click ok");
                }
            }
        }
    }


    private void nodePerformActionById(AccessibilityEvent event, String id) {
        List<AccessibilityNodeInfo> nodes = event.getSource()
                .findAccessibilityNodeInfosByViewId(id);
        if (nodes != null && !nodes.isEmpty()) {
            AccessibilityNodeInfo node;
            for (int i = 0; i < nodes.size(); i++) {
                node = nodes.get(i);
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                Logger.d(TAG, "click ok");
            }
        }
    }

    private void nodePerformActions(AccessibilityEvent event, String clsName, String[] tags) {
        for (String tag : tags) {
            nodePerformActionByTag(event, clsName, tag);
        }
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
    }
}
