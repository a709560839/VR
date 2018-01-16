package com.daydvr.store.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;


import com.daydvr.store.R;
import com.daydvr.store.bean.GameListBean;
import com.daydvr.store.bean.UserBean;
import com.daydvr.store.manager.GameManager;
import com.daydvr.store.util.AppInfoUtil;

import com.daydvr.store.util.GlideImageLoader;
import com.daydvr.store.util.Logger;

import java.lang.ref.WeakReference;
import java.util.List;

import static com.daydvr.store.base.BaseConstant.CURRENT_UPDTAE_UI;
import static com.daydvr.store.base.BaseConstant.GAME_MANAGER_UI_UPDATE;
import static com.daydvr.store.base.BaseConstant.GUIDE_UI_UPDATE;
import static com.daydvr.store.base.GameConstant.DOWNLOADABLE;
import static com.daydvr.store.base.GameConstant.DOWNLOADING;
import static com.daydvr.store.base.GameConstant.INSTALLABLE;
import static com.daydvr.store.base.GameConstant.INSTALLED;
import static com.daydvr.store.base.GameConstant.NOT_ADAPTION;
import static com.daydvr.store.base.GameConstant.PAUSED;
import static com.daydvr.store.base.GameConstant.TEXT_ADAPTION;
import static com.daydvr.store.base.GameConstant.TEXT_CONTINUE;
import static com.daydvr.store.base.GameConstant.TEXT_DOWNLOAD;
import static com.daydvr.store.base.GameConstant.TEXT_INSTALL;
import static com.daydvr.store.base.GameConstant.TEXT_OPEN;
import static com.daydvr.store.base.GameConstant.TEXT_PAUSE;
import static com.daydvr.store.base.GameConstant.TEXT_UPDATE;
import static com.daydvr.store.base.GameConstant.UPDATE;
import static com.daydvr.store.base.PersonConstant.threadTest;

/**
 * @author LoSyc
 * @version Created on 2017/12/26. 10:13
 */

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ViewHolder> {

    private final static byte TYPE_FOOTER = 0;
    private final static byte TYPE_NORMAL = 1;

    private boolean mIsRanking = false;

    private WeakReference<Context> mContext;
    private View mRootView;
    private List<GameListBean> mDatas;
    private ItemOnClickListener mListener;
    private int mPerPageCount;
    private AppInfoUtil mAppInfoUtil;

    public GameListAdapter(Context context, boolean isRanking, int perPageCount) {
        mContext = new WeakReference<Context>(context);
        mAppInfoUtil = new AppInfoUtil(context);
        mIsRanking = isRanking;
        mPerPageCount = perPageCount;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;

        switch (viewType) {
            case TYPE_NORMAL:
                mRootView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_game_list, parent, false);
                holder = new ViewHolder(mRootView, TYPE_NORMAL);
                break;
            case TYPE_FOOTER:
                mRootView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_footer, parent, false);
                holder = new ViewHolder(mRootView, TYPE_FOOTER);
                break;
            default:
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        Logger.i("--------------------------has  payloads");
        if (payloads.isEmpty()) {
            Logger.i("--------------------------no  payloads");
            onBindViewHolder(holder, position);
        } else {
            Logger.i("--------------------------false  payloads");
            GameListBean bean = mDatas.get(position);
            byte status = (byte) payloads.get(0);
            holder.setFlag(position, status);
            String text = "";
            switch (status) {
                case DOWNLOADING:
                    holder.setInitViewVisibility();
                    holder.setAfterDownloadViewVisibility();
                    threadTest.get(bean.getId()).setBean(bean);
                    threadTest.get(bean.getId()).setHolder(holder);
                    text = TEXT_PAUSE;
                    break;

                case PAUSED:
                    holder.setAfterDownloadViewVisibility();
                    holder.gameProgressBar.setProgress(bean.getProgress());
                    threadTest.get(bean.getId()).setBean(bean);
                    threadTest.get(bean.getId()).setHolder(holder);
                    text = TEXT_CONTINUE;
                    break;

                case INSTALLABLE:
                    holder.setInitViewVisibility();
                    text = TEXT_INSTALL;
                    break;

                default:
                    break;
            }
            holder.gameDownloadTextView.setText(text);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {

            checkIsRanking(holder, position);

            initItemView(holder, position);

            initDownloadStatus(holder, position);

        } else if (getItemViewType(position) == TYPE_FOOTER && mDatas.size() > mPerPageCount) {
            if (mDatas.size() % mPerPageCount != 0) {
                holder.gameLoadTipTextView.setText("已经到底了！");
            }
        } else if (CURRENT_UPDTAE_UI == GUIDE_UI_UPDATE ||
                CURRENT_UPDTAE_UI == GAME_MANAGER_UI_UPDATE) {
            holder.itemView.setVisibility(View.GONE);
            ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(0, 0);
            holder.itemView.setLayoutParams(params);
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

    private void checkIsRanking(ViewHolder holder, int position) {
        if (mIsRanking) {
            holder.gameRankingTextView.setVisibility(View.VISIBLE);
            if (position == 0) {
                holder.gameRankingTextView.setTextColor(ContextCompat.getColor(mContext.get(), R.color.ranking_first));
            } else if (position == 1 || position == 2) {
                holder.gameRankingTextView.setTextColor(ContextCompat.getColor(mContext.get(), R.color.ranking_secondtothird));
            } else {
                holder.gameRankingTextView.setTextColor(ContextCompat.getColor(mContext.get(), R.color.ranking_default));
            }
            if (position < 9) {
                holder.gameRankingTextView.setText(" " + (position + 1)+" ");
            } else {
                holder.gameRankingTextView.setText(String.valueOf(position + 1));
            }
        }
    }

    private void initItemView(ViewHolder holder, int position) {
        GameListBean bean = mDatas.get(position);
        UserBean userBean = UserBean.getInstance();
        holder.gameNameTextView.setText(bean.getName());
        if (bean.getIntegral() > 0) {
            holder.gameIntegralTextView.setText(String.valueOf(bean.getIntegral()) + " 积分");
            holder.gameIntegralTextView.setTextColor(Color.RED);
            if (userBean == null || bean.getIntegral() > userBean.getIntegral()) {
                holder.gameDownloadTextView.setEnabled(false);
                holder.gameDownloadTextView.setTextColor(Color.GRAY);
                holder.gameIntegralTipTextView.setVisibility(View.VISIBLE);
            }
        } else {
            holder.gameIntegralTextView.setText("免积分下载");
            holder.gameIntegralTextView.setTextColor(Color.GRAY);
        }
        holder.gameRatingBar.setMax(5);
        holder.gameRatingBar.setRating(bean.getRating());
        holder.gameSizeTextView.setText(bean.getSize() + "M");
        holder.gameTypeTextView.setText(bean.getType());
        String iconUrl = bean.getIconUrl();
        GlideImageLoader.commonLoader(mRootView.getContext(), iconUrl, holder.gameIconImageView);
    }

    private void initDownloadStatus(ViewHolder holder, int position) {
        GameListBean bean = mDatas.get(position);
        byte state = bean.getStatus();
        //            state = mAppInfoUtil.getApplicationStatus(bean);
        holder.setFlag(position, state);

        String text = "";
        switch (state) {
            case DOWNLOADABLE:
                holder.setInitViewVisibility();
                text = TEXT_DOWNLOAD;
                break;

            case DOWNLOADING:
                holder.setInitViewVisibility();
                holder.setAfterDownloadViewVisibility();
                threadTest.get(bean.getId()).setBean(bean);
                threadTest.get(bean.getId()).setHolder(holder);
                text = TEXT_PAUSE;
                break;

            case PAUSED:
                holder.setAfterDownloadViewVisibility();
                holder.gameProgressBar.setProgress(bean.getProgress());
                threadTest.get(bean.getId()).setBean(bean);
                threadTest.get(bean.getId()).setHolder(holder);
                text = TEXT_CONTINUE;
                break;

            case UPDATE:
                text = TEXT_UPDATE;
                break;

            case NOT_ADAPTION:
                text = TEXT_ADAPTION;
                break;

            case INSTALLED:
                text = TEXT_OPEN;
                break;

            case INSTALLABLE:
                holder.setInitViewVisibility();
                text = TEXT_INSTALL;
                break;


            default:
                text = "";
                break;
        }
        holder.gameDownloadTextView.setText(text);
    }

    public void setDatas(List<GameListBean> datas) {
        this.mDatas = datas;
    }

    public void setListener(ItemOnClickListener listener) {
        this.mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private byte flag;
        private TextView gameRankingTextView;
        private ImageView gameIconImageView;
        private TextView gameTypeTextView;
        private TextView gameNameTextView;
        private TextView gameIntegralTextView;
        private TextView gameIntegralTipTextView;
        private TextView gameSizeTextView;
        private RatingBar gameRatingBar;
        private TextView gameDownloadTextView;
        private ProgressBar gameProgressBar;
        private TextView gameProgressTextView;
        private TextView gameSpeedTextView;
        private TextView cancelDownloadTextView;

        private TextView gameLoadTipTextView;

        private ViewHolder(View itemView, byte flag) {
            super(itemView);
            if (flag == TYPE_NORMAL) {
                initNormalView(itemView);
            } else if (flag == TYPE_FOOTER) {
                initFooterView(itemView);
            }
        }

        private void initNormalView(final View itemView) {
            flag = this.getFlag();
            gameRankingTextView = itemView.findViewById(R.id.tv_rangking);
            gameDownloadTextView = itemView.findViewById(R.id.tv_game_detail);
            gameIconImageView = itemView.findViewById(R.id.iv_game_icon);
            gameNameTextView = itemView.findViewById(R.id.tv_game_name);
            gameIntegralTextView = itemView.findViewById(R.id.tv_game_integral);
            gameIntegralTipTextView = itemView.findViewById(R.id.tv_disdownload_integrel);
            gameSizeTextView = itemView.findViewById(R.id.tv_game_size);
            gameRatingBar = itemView.findViewById(R.id.rb_game_rating);
            gameTypeTextView = itemView.findViewById(R.id.tv_game_type);
            gameProgressBar = itemView.findViewById(R.id.pb_download);
            gameProgressTextView = itemView.findViewById(R.id.tv_download_progress);
            gameSpeedTextView = itemView.findViewById(R.id.tv_download_speed);
            cancelDownloadTextView = itemView.findViewById(R.id.tv_cancel_download);

            gameDownloadTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GameListBean bean = mDatas.get(getAdapterPosition());

                    if (flag == DOWNLOADABLE) {
                        if (!mAppInfoUtil.checkMemoryAndNet(bean)) {
                            return;
                        }
                        if (TEXT_DOWNLOAD.equals(gameDownloadTextView.getText())) {
                            AppInfoUtil.notifyDownloadAppProgress(mRootView.getContext(), bean.getId(), bean.getName());
                            setAfterDownloadViewVisibility();
                        }
                        GameManager.saveGameDownloadStatus(bean);
                    }
                    mListener.onButtonClick(itemView, bean);
                }
            });

            cancelDownloadTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GameListBean bean = mDatas.get(getAdapterPosition());
                    if (flag == DOWNLOADING || flag == PAUSED) {
                        setInitViewVisibility();
                        setFlag(getAdapterPosition(), DOWNLOADABLE);
                        GameManager.removeGameDownloadStatus(bean);
                        GameManager.setIsCanceled(true);
                        mListener.onCancelButtonClick(itemView, bean);
                        AppInfoUtil.notifyCancelById(bean.getId());
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v, mDatas.get(getAdapterPosition()));
                }
            });
        }

        private void initFooterView(final View itemView) {
            gameLoadTipTextView = itemView.findViewById(R.id.tv_loading_tip);
        }

        public void setInitViewVisibility() {
            gameDownloadTextView.setText(TEXT_DOWNLOAD);
            gameRatingBar.setVisibility(View.VISIBLE);
            gameSizeTextView.setVisibility(View.VISIBLE);
            gameIntegralTextView.setVisibility(View.VISIBLE);

            gameProgressBar.setVisibility(View.INVISIBLE);
            gameProgressBar.setProgress(0);
            gameProgressTextView.setVisibility(View.INVISIBLE);
            gameSpeedTextView.setVisibility(View.INVISIBLE);
            cancelDownloadTextView.setVisibility(View.INVISIBLE);
        }

        public void setAfterDownloadViewVisibility() {
            gameRatingBar.setVisibility(View.INVISIBLE);
            gameSizeTextView.setVisibility(View.INVISIBLE);
            gameIntegralTextView.setVisibility(View.INVISIBLE);

            gameProgressBar.setVisibility(View.VISIBLE);
            gameProgressTextView.setVisibility(View.VISIBLE);
            gameSpeedTextView.setVisibility(View.VISIBLE);
            cancelDownloadTextView.setVisibility(View.VISIBLE);
        }

        public void setDownloadButtonText(CharSequence text) {
            gameDownloadTextView.setText(text);
        }

        public void setProgressTextView(GameListBean bean, CharSequence text) {
            gameProgressTextView.setText(text);
        }

        public void setDownloadProgress(GameListBean bean, int soFarBytes) {
            bean.setProgress(soFarBytes);
            gameProgressBar.setMax((int) bean.getSize());
            gameProgressBar.setProgress(soFarBytes);
        }

        public int getDownloadProgress() {
            return gameProgressBar.getProgress();
        }

        public void setDownloadSpeed(GameListBean bean, int speed) {
            gameSpeedTextView.setText(speed + "kb/s");
        }

        public byte getFlag() {
            return flag;
        }

        public void setFlag(int position, byte flag) {
            mDatas.get(position).setStatus(flag);
            this.flag = flag;
        }
    }

    public interface ItemOnClickListener {

        void onItemClick(View view, GameListBean bean);

        void onButtonClick(View view, GameListBean bean);

        void onCancelButtonClick(View view, GameListBean bean);
    }
}
