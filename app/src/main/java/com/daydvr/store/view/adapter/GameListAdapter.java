package com.daydvr.store.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.daydvr.store.R;
import com.daydvr.store.bean.GameListBean;
import com.daydvr.store.manager.GameManager;
import com.daydvr.store.util.AppInfoUtil;

import com.daydvr.store.util.Logger;
import java.lang.ref.WeakReference;
import java.util.List;

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
import static com.daydvr.store.base.LoginConstant.threadTest;

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
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            GameListBean bean = mDatas.get(position);
            byte state = bean.getStatus();
            //            state = mAppInfoUtil.getApplicationStatus(bean);
            holder.setFlag(position, state);

            if (mIsRanking) {
                holder.gameRankingTextView.setVisibility(View.VISIBLE);
                if (position == 0) {
                    holder.gameRankingTextView.setTextColor(mContext.get().getResources().getColor(R.color.ranking_first));
                } else if (position == 1 || position == 2) {
                    holder.gameRankingTextView.setTextColor(mContext.get().getResources().getColor(R.color.ranking_secondtothird));
                } else {
                    holder.gameRankingTextView.setTextColor(mContext.get().getResources().getColor(R.color.ranking_default));
                }
                if (position < 9) {
                    holder.gameRankingTextView.setText("  " + (position + 1));
                } else {
                    holder.gameRankingTextView.setText(String.valueOf(position + 1));
                }
            }
            holder.gameNameTextView.setText(bean.getName());
            holder.gameSummaryTextView.setText(bean.getSummary());
            holder.gameRatingBar.setMax(5);
            holder.gameRatingBar.setRating(bean.getRating());
            holder.gameSizeTextView.setText(bean.getSize() + "M");
            holder.gameTypeTextView.setText(bean.getType());
            String icon = bean.getIconUrl();
            Glide.with(mRootView.getContext()).load(icon)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(new GlideDrawableImageViewTarget(holder.gameIconImageView) {
                        @Override
                        public void onResourceReady(GlideDrawable resource,

                                GlideAnimation<? super GlideDrawable> animation) {
                            super.onResourceReady(resource, animation);
                        }
                    });

            if (state == DOWNLOADING || state == PAUSED) {
                holder.setAfterDownloadViewVisibility();
                holder.gameProgressBar.setMax((int) bean.getSize());
                holder.gameProgressBar.setProgress(bean.getProgress());
                threadTest.get(bean.getId()).setBean(bean);
                threadTest.get(bean.getId()).setHolder(holder);
            } else if (state == DOWNLOADABLE) {
                holder.setInitViewVisibility();
            }
            String text = getDownloadButton(holder, state);
            holder.gameDetailButton.setText(text);

        } else if (getItemViewType(position) == TYPE_FOOTER && mDatas.size() > mPerPageCount) {
            if (mDatas.size() % mPerPageCount != 0) {
                holder.gameLoadTipTextView.setText("已经到底了！");
            }
        } else if (mDatas.size() < mPerPageCount) {
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

    public void setDatas(List<GameListBean> datas) {
        this.mDatas = datas;
    }

    public void setListener(ItemOnClickListener listener) {
        this.mListener = listener;
    }

    public String getDownloadButton(ViewHolder holder, int state) {
        String text = null;
        switch (state) {
            case DOWNLOADABLE:
                text = TEXT_DOWNLOAD;
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
                text = TEXT_INSTALL;
                holder.setInitViewVisibility();
                break;

            case DOWNLOADING:
                text = TEXT_PAUSE;
                break;

            case PAUSED:
                text = TEXT_CONTINUE;
                break;

            default:
                text = "";
                break;
        }
        return text;
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        private byte flag;
        private TextView gameRankingTextView;
        private ImageView gameIconImageView;
        private TextView gameTypeTextView;
        private TextView gameNameTextView;
        private TextView gameSummaryTextView;
        private TextView gameSizeTextView;
        private RatingBar gameRatingBar;
        private Button gameDetailButton;
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
            gameDetailButton = itemView.findViewById(R.id.bt_game_detail);
            gameIconImageView = itemView.findViewById(R.id.iv_game_icon);
            gameNameTextView = itemView.findViewById(R.id.tv_game_name);
            gameSummaryTextView = itemView.findViewById(R.id.tv_game_summary);
            gameSizeTextView = itemView.findViewById(R.id.tv_game_size);
            gameRatingBar = itemView.findViewById(R.id.rb_game_rating);
            gameTypeTextView = itemView.findViewById(R.id.tv_game_type);
            gameProgressBar = itemView.findViewById(R.id.pb_download);
            gameProgressTextView = itemView.findViewById(R.id.tv_download_progress);
            gameSpeedTextView = itemView.findViewById(R.id.tv_download_speed);
            cancelDownloadTextView = itemView.findViewById(R.id.tv_cancel_download);

            gameDetailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GameListBean bean = mDatas.get(getAdapterPosition());
                    if (flag == DOWNLOADABLE) {
                        if (!mAppInfoUtil.checkMemoryAndNet(bean)) {
                            return;
                        }

                        if (TEXT_DOWNLOAD.equals(gameDetailButton.getText())) {
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
                        mListener.onCancelButtonClick(itemView, bean);
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
            gameDetailButton.setText(TEXT_DOWNLOAD);
            gameRatingBar.setVisibility(View.VISIBLE);
            gameSizeTextView.setVisibility(View.VISIBLE);
            gameSummaryTextView.setVisibility(View.VISIBLE);

            gameProgressBar.setVisibility(View.INVISIBLE);
            gameProgressBar.setProgress(0);
            gameProgressTextView.setVisibility(View.INVISIBLE);
            gameSpeedTextView.setVisibility(View.INVISIBLE);
            cancelDownloadTextView.setVisibility(View.INVISIBLE);
        }

        public void setAfterDownloadViewVisibility() {
            gameRatingBar.setVisibility(View.INVISIBLE);
            gameSizeTextView.setVisibility(View.INVISIBLE);
            gameSummaryTextView.setVisibility(View.INVISIBLE);

            gameProgressBar.setVisibility(View.VISIBLE);
            gameProgressTextView.setVisibility(View.VISIBLE);
            gameSpeedTextView.setVisibility(View.VISIBLE);
            cancelDownloadTextView.setVisibility(View.VISIBLE);
        }

        public void setDownloadButtonText(CharSequence text) {
            gameDetailButton.setText(text);
        }

        public void setProgressTextView(GameListBean bean, CharSequence text) {
            gameProgressTextView.setText(text);
        }

        public void setDownloadProgress(GameListBean bean, int soFarBytes) {
            bean.setProgress(soFarBytes);
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
