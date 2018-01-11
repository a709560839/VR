package com.daydvr.store.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.Target;
import com.daydvr.store.R;
import com.daydvr.store.view.custom.BannerLayout;

/**
 * Created by Administrator on 2016/12/21 0021.
 */

public class GlideImageLoader implements BannerLayout.ImageLoader {

    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().placeholder(R.drawable.images_loading)
                .error(R.drawable.images_loading).crossFade().into(imageView);
    }

    public static DrawableTypeRequest<String> commonLoader(Context context, String imageUrl, ImageView imageView) {
        DrawableTypeRequest<String> request = Glide.with(context).load(imageUrl);
        request.diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.images_loading)
                .error(R.drawable.images_loading).crossFade().into(imageView);
        return request;
    }
}

