package com.daydvr.store.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.daydvr.store.R;
import com.daydvr.store.bean.AppTabItem;

/**
 * @author a79560839
 * @version Created on 2018/1/5. 11:16
 */

public class AppTabView extends LinearLayout {

    private ImageView mTabImage;
    private TextView mTabLable;

    public AppTabView(Context context) {
        super(context);
        initView(context);
    }

    public AppTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AppTabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        LayoutInflater.from(context).inflate(R.layout.item_tabview, this, true);
        mTabImage = findViewById(R.id.iv_tab);
        mTabLable = findViewById(R.id.tv_tab);

    }

    public void initData(AppTabItem tabItem) {

        mTabImage.setImageResource(tabItem.imageResId);
        mTabLable.setText(tabItem.lableResId);
    }
}
