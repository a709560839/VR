package com.daydvr.store.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.daydvr.store.R;
import com.daydvr.store.bean.GameListBean;
import com.daydvr.store.util.GlideImageLoader;
import com.daydvr.store.view.custom.RoundImageView;
import java.util.List;

/**
 * @author a79560839
 * @version Created on 2018/1/8. 15:44
 */

public class ExchangeListAdapter extends RecyclerView.Adapter<ExchangeListAdapter.ViewHolder>{

    private View mRootView;
    private ItemOnClickListener mListener;
    private List<GameListBean> mDatas;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;

        mRootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exchange_app_list, parent, false);
        holder = new ViewHolder(mRootView);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String iconUrl = mDatas.get(position).getIconUrl();
        GlideImageLoader.commonLoader(mRootView.getContext(), iconUrl, holder.appImageView);
        holder.appNameTextView.setText(mDatas.get(position).getName());
        holder.appSizeTextView.setText(mDatas.get(position).getSize()+"M");
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void setDatas(List<GameListBean> datas) {
        this.mDatas = datas;
    }

    public void setListener(ExchangeListAdapter.ItemOnClickListener listener) {
        this.mListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RoundImageView appImageView;
        private TextView appNameTextView;
        private TextView appSizeTextView;
        private TextView appExchangeTextView;

        ViewHolder(View itemView) {
            super(itemView);
            appImageView = itemView.findViewById(R.id.iv_app_icon);
            appNameTextView = itemView.findViewById(R.id.tv_app_name);
            appSizeTextView = itemView.findViewById(R.id.tv_app_size);
            appExchangeTextView = itemView.findViewById(R.id.tv_app_exchange_success);

            appExchangeTextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onButtonClick(view, getAdapterPosition());
                }
            });

            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(view,getAdapterPosition());
                }
            });
        }
    }

    public interface ItemOnClickListener {
        void onItemClick(View view, int position);

        void onButtonClick(View view, int position);
    }
}

