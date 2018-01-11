package com.daydvr.store.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.daydvr.store.R;
import com.daydvr.store.util.GlideImageLoader;
import java.util.ArrayList;
import java.util.List;

/*
 * Copyright (C) 2017 3ivr. All rights reserved.
 *
 * @author: Jason(Liu ZhiCheng)
 * @mail  : jason@3ivr.cn
 * @date  : 2017/12/28 21:27
 */

public class GamePicApdapter extends RecyclerView.Adapter <GamePicApdapter.ViewHolder>{

    private List<String> imageUrls = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;
    private ItemClick mItemClick;

    public GamePicApdapter(List<String> imageUrls, Context context) {
        this.imageUrls = imageUrls;
        this.context = context;
        this.mInflater = LayoutInflater.from(this.context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_game_pic,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(view,mItemClick);

        viewHolder.mImg = (ImageView) view
                .findViewById(R.id.iv_game_pic_detail);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
         GlideImageLoader.commonLoader(context,imageUrls.get(position),holder.mImg);
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private ItemClick itemClick;
        public ViewHolder(View arg0,ItemClick itemClick)
        {
            super(arg0);
            this.itemClick = itemClick;
            arg0.setOnClickListener(this);

        }
        ImageView mImg;

        @Override
        public void onClick(View v) {
           if(mItemClick!=null){
               mItemClick.onItemClick(v,getAdapterPosition());
           }
        }
    }

    public interface ItemClick{
        void onItemClick(View view,int position);
    }

    public void setItemClickListener(ItemClick itemClickListener){
        this.mItemClick = itemClickListener;
    }
}
