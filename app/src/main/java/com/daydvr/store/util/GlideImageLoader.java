package com.daydvr.store.util;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.daydvr.store.view.custom.BannerLayout;

/**
 * Created by Administrator on 2016/12/21 0021.
 */

public class GlideImageLoader implements BannerLayout.ImageLoader {
    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context).load(path).centerCrop().into(imageView);
    }
}
