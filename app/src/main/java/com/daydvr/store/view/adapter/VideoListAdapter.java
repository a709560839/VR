package com.daydvr.store.view.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daydvr.store.R;
import com.daydvr.store.bean.VideoListBean;
import com.daydvr.store.util.GlideImageLoader;
import com.daydvr.store.view.custom.RoundImageView;

import java.util.List;

/**
 * @author LoSyc
 * @version Created on 2017/12/26. 17:49
 */

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder>{

    private final static byte TYPE_FOOTER = 0;
    private final static byte TYPE_NORMAL = 1;

    private View mRootView;
    private List<VideoListBean> mDatas;
    private VideoListAdapter.ItemOnClickListener mListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;

        if (viewType == TYPE_NORMAL) {
            mRootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_grid, parent,false);
            holder = new ViewHolder(mRootView,TYPE_NORMAL);
        } else if (viewType == TYPE_FOOTER) {
            mRootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer, parent,false);
            holder = new ViewHolder(mRootView,TYPE_FOOTER);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            holder.videoNameTextView.setText(mDatas.get(position).getName());
            String imageUrl = mDatas.get(position).getVideoUrl();
            GlideImageLoader.commonLoader(mRootView.getContext(),imageUrl,holder.videoImageView);
            holder.videoViewsTextView.setText(mDatas.get(position).getViews());
        } else if (getItemViewType(position) == TYPE_FOOTER) {
            if (mDatas.size() <= 5) {
                holder.itemView.setVisibility(View.GONE);
                ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(0, 0);
                holder.itemView.setLayoutParams(params);
            } else {
//                holder.videoFooterTextView.setText("已经到底了！");
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    public void setDatas(List<VideoListBean> datas) {
        this.mDatas = datas;
    }

    public void setListener(VideoListAdapter.ItemOnClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_FOOTER ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RoundImageView videoImageView;
        private TextView videoNameTextView;
        private TextView videoViewsTextView;

        private TextView videoFooterTextView;

        public ViewHolder(View itemView,int type) {
            super(itemView);
            if (type == TYPE_NORMAL) {
                initNormalView(itemView);
            } else if (type == TYPE_FOOTER) {
                initFooterView(itemView);
            }
        }

        private void initFooterView(View itemView) {
            videoFooterTextView = itemView.findViewById(R.id.tv_loading_tip);
        }

        private void initNormalView(View itemView){
            videoImageView = itemView.findViewById(R.id.iv_video_show);
            videoNameTextView = itemView.findViewById(R.id.tv_video_name);
            videoViewsTextView = itemView.findViewById(R.id.tv_video_views);

            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(view,mDatas.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface ItemOnClickListener {
        void onItemClick(View view, VideoListBean bean);
    }
}
