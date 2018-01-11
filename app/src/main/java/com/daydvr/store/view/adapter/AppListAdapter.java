package com.daydvr.store.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.daydvr.store.R;
import com.daydvr.store.bean.AppListBean;
import com.daydvr.store.view.custom.RoundImageView;
import java.util.List;

/**
 * @author a79560839
 * @version Created on 2018/1/8. 15:44
 */

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder>{

    private View mRootView;
    private ItemOnClickListener mListener;
    private List<AppListBean.ApkInfo> mDatas;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;

        mRootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app_list, parent, false);
        holder = new ViewHolder(mRootView);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.appImageView.setImageDrawable(mDatas.get(position).getGame_icon_drawable());
        holder.appNameTextView.setText(mDatas.get(position).getName());
        holder.appSizeTextView.setText(mDatas.get(position).getSize());
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void setDatas(List<AppListBean.ApkInfo> datas) {
        this.mDatas = datas;
    }

    public void setListener(AppListAdapter.ItemOnClickListener listener) {
        this.mListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RoundImageView appImageView;
        private TextView appNameTextView;
        private TextView appSizeTextView;
        private Button appUninstallButton;

        ViewHolder(View itemView) {
            super(itemView);
            appImageView = itemView.findViewById(R.id.iv_app_icon);
            appNameTextView = itemView.findViewById(R.id.tv_app_name);
            appSizeTextView = itemView.findViewById(R.id.tv_app_size);
            appUninstallButton = itemView.findViewById(R.id.bt_app_uninstall);

            appUninstallButton.setOnClickListener(new OnClickListener() {
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
        void onItemClick(View view,int position);

        void onButtonClick(View view, int position);
    }
}

