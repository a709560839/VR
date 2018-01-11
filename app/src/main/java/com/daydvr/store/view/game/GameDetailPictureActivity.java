package com.daydvr.store.view.game;

import static com.daydvr.store.base.GameConstant.GAME_DETAIL_PIC;
import static com.daydvr.store.base.GameConstant.GAME_DETAIL_PIC_DATAS;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.daydvr.store.R;
import com.daydvr.store.base.BaseActivity;
import com.daydvr.store.view.custom.BannerLayout;
import com.daydvr.store.view.custom.BannerLayout.OnBannerItemClickListener;
import com.daydvr.store.util.GlideImageLoader;
import java.util.List;

public class GameDetailPictureActivity extends BaseActivity {

    private BannerLayout mBanner;
    private int pagePostion;
    private List<CharSequence> pageDatas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail_picture);

        initIntent();
        initView();
    }

    private void initIntent() {
        pageDatas = getIntent().getCharSequenceArrayListExtra(GAME_DETAIL_PIC_DATAS);
        pagePostion = getIntent().getIntExtra(GAME_DETAIL_PIC,0);
    }

    private void initView() {
        //添加监听事件
        mBanner = findViewById(R.id.bl_game_detail_pic);
        //低于三张
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setViewUrlss(pageDatas);
        mBanner.setOnBannerItemClickListener(new OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(VERSION.SDK_INT>=21){
                    finishAfterTransition();
                }else {
                    finish();
                }
            }
        });
        mBanner.setAutoPlay(false);

        mBanner.setCurrentPager(pagePostion);
    }
}
