package com.daydvr.store.view.guide;

import android.content.Intent;
import android.os.Bundle;
import com.daydvr.store.R;
import com.daydvr.store.base.BaseActivity;
import com.daydvr.store.view.custom.ParticleView;
import com.daydvr.store.view.home.MainActivity;

public class GuideActivity extends BaseActivity {

    private ParticleView mGuideParticleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

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
                Intent i = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(i);
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
