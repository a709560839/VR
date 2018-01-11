package com.daydvr.store.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.daydvr.store.bean.AppTabItem;
import java.util.ArrayList;

/**
 * @author a79560839
 * @version Created on 2018/1/5. 11:26
 */

public class AppTabLayout extends LinearLayout implements View.OnClickListener {

    private ArrayList<AppTabItem> tabs;
    private OnTabClickListener listener;
    private int tabCount;
    private View selectView;

    public AppTabLayout(Context context) {
        super(context);
        initView();
    }

    public AppTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AppTabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        setOrientation(HORIZONTAL);
    }

    public void initData(ArrayList<AppTabItem> tabs, OnTabClickListener listener) {
        this.tabs = tabs;
        this.listener = listener;
        LayoutParams params = new LayoutParams(0,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        if (tabs != null && tabs.size() > 0) {
            AppTabView mTabView;
            tabCount=tabs.size();
            for (int i = 0; i < tabs.size(); i++) {
                mTabView = new AppTabView(getContext());
                mTabView.setTag(tabs.get(i));
                mTabView.initData(tabs.get(i));
                mTabView.setOnClickListener(this);
                addView(mTabView, params);
            }

        } else {
            throw new IllegalArgumentException("tabs can not be empty");
        }
    }

    @Override
    public void onClick(View v) {
        listener.onTabClick((AppTabItem) v.getTag());
    }

    public void setCurrentTab(int i) {
        if (i < tabCount && i >= 0) {
            View view = getChildAt(i);
            if (selectView != view) {
                view.setSelected(true);
                if (selectView != null) {
                    selectView.setSelected(false);
                }
                selectView = view;
            }
        }
    }

    public interface OnTabClickListener {

        void onTabClick(AppTabItem tabItem);
    }
}
