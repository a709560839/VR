package com.daydvr.store.view.guide;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.daydvr.store.R;
import com.daydvr.store.base.BaseActivity;
import com.daydvr.store.view.custom.bgabanner.BGABanner;
import com.daydvr.store.view.home.MainActivity;
import org.w3c.dom.Text;

public class GuideActivity extends BaseActivity {

    private BGABanner mBackgroundBanner;
    private BGABanner mForegroundBanner;
    private TextView mGuideSkip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        processLogic();
    }

    private void initView() {
        mBackgroundBanner = findViewById(R.id.banner_guide_background);
        mForegroundBanner = findViewById(R.id.banner_guide_foreground);
        mGuideSkip = findViewById(R.id.tv_guide_skip);
        configView();
    }

    private void configView() {
        mGuideSkip.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void processLogic() {
        mBackgroundBanner.setData(R.drawable.guide_background_1,R.drawable.guide_background_2,R.drawable.guide_background_3);
        mForegroundBanner.setData(R.drawable.guide_foreground_1,R.drawable.guide_foreground_2,R.drawable.guide_foreground_3);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 如果开发者的引导页主题是透明的，需要在界面可见时给背景 Banner 设置一个白色背景，避免滑动过程中两个 Banner 都设置透明度后能看到 Launcher
        mBackgroundBanner.setBackgroundResource(android.R.color.white);
    }
}
