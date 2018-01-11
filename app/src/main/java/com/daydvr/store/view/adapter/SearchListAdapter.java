package com.daydvr.store.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.daydvr.store.R;
import com.daydvr.store.bean.SearchListBean;

import java.util.List;

/**
 * @author LoSyc
 * @version Created on 2017/12/28. 19:23
 */

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {
    private final static byte TYPE_FOOTER = 0;
    private final static byte TYPE_NORMAL = 1;

    private View mRootView;
    private List<SearchListBean> mDatas;
    private ItemOnClickListener mListener;
    private int mPerPageCount;

    public SearchListAdapter(int perPageCount) {
        this.mPerPageCount = perPageCount;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;

        switch (viewType) {
            case TYPE_NORMAL:
                mRootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_data, parent, false);
                holder = new ViewHolder(mRootView, TYPE_NORMAL);
                break;
            case TYPE_FOOTER:
                mRootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer, parent, false);
                holder = new ViewHolder(mRootView, TYPE_FOOTER);
                break;
            default:
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(SearchListAdapter.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            holder.searchDataNameTextView.setText(mDatas.get(position).getName());
            holder.searchDataTypeTextView.setText(mDatas.get(position).getType());
            String imageUrl = mDatas.get(position).getIconUrl();
            Glide.with(mRootView.getContext()).load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(new GlideDrawableImageViewTarget(holder.searchDataImageView) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                            super.onResourceReady(resource, animation);
                        }
                    });
        } else if (getItemViewType(position) == TYPE_FOOTER) {
            if (mDatas.size() % mPerPageCount != 0) {
                holder.gameLoadTipTextView.setText("已经到底了！");
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    public void setDatas(List<SearchListBean> datas) {
        this.mDatas = datas;
    }

    public void setListener(ItemOnClickListener listener) {
        this.mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView searchDataNameTextView;
        private TextView searchDataTypeTextView;
        private ImageView searchDataImageView;

        private TextView gameLoadTipTextView;

        private ViewHolder(View itemView, byte flag) {
            super(itemView);
            if (flag == TYPE_NORMAL) {
                searchDataNameTextView = itemView.findViewById(R.id.tv_search_name);
                searchDataTypeTextView = itemView.findViewById(R.id.tv_search_type);
                searchDataImageView = itemView.findViewById(R.id.iv_search_icon);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick(v, mDatas.get(getAdapterPosition()));
                    }
                });
            } else if (flag == TYPE_FOOTER) {
                gameLoadTipTextView = itemView.findViewById(R.id.tv_loading_tip);
            }
        }
    }

    public interface ItemOnClickListener {
        void onItemClick(View view, SearchListBean bean);
    }
}
