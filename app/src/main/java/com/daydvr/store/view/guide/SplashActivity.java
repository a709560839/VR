package com.daydvr.store.view.guide;

import android.os.Bundle;
import android.view.WindowManager;
import com.daydvr.store.R;
import com.daydvr.store.base.BaseActivity;
import com.daydvr.store.view.custom.ParticleView;

public class SplashActivity extends BaseActivity {

    private ParticleView mGuideParticleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        initView();
    }

    private void initView() {
        mGuideParticleView = findViewById(R.id.pv_guide);
        configComponent();
    }

    private void configComponent() {
        mGuideParticleView.setOnParticleAnimListener(new ParticleView.ParticleAnimListener() {
            @Override
            public void onAnimationEnd() {
                finish();
            }
        });

        mGuideParticleView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mGuideParticleView.startAnim();
            }
        }, 200);
    }

}
