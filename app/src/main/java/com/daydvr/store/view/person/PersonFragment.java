package com.daydvr.store.view.person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daydvr.store.R;
import com.daydvr.store.base.BaseFragment;
import com.daydvr.store.presenter.person.PersonContract;
import com.daydvr.store.presenter.person.PersonPresenter;
import com.daydvr.store.util.GlideImageLoader;
import com.daydvr.store.view.custom.RoundImageView;
import com.daydvr.store.view.home.MainActivity;
import com.daydvr.store.view.login.LoginActivity;
import com.daydvr.store.view.setting.SettingActivity;
import com.daydvr.store.view.video.VideoListActivity;

import static com.daydvr.store.base.BaseConstant.GAME_LIST_FRAGEMNT_ITEM;
import static com.daydvr.store.base.BaseConstant.GUIDE_FRAGEMNT_ITEM;
import static com.daydvr.store.base.LoginConstant.LOGIN_OK;
import static com.daydvr.store.base.LoginConstant.LOGIN_REQUEST_CODE;
import static com.daydvr.store.base.LoginConstant.USER_HEAD_URL;
import static com.daydvr.store.base.LoginConstant.USER_NAME;
import static com.daydvr.store.base.LoginConstant.USER_REGISTERED_TIME;
import static com.daydvr.store.base.LoginConstant.isLogin;

/**
 * @author a79560839
 * @version Created on 2018/1/5. 16:30
 */

public class PersonFragment extends BaseFragment implements PersonContract.View {

    private PersonPresenter mPresenter;

    private View mRootView;
    //private ViewGroup mHotViewGroup, mGameViewGroup, mVideoViewGroup;
    private ViewGroup mAppListViewGroup;
    private RoundImageView mHeadRoundImageView;
    private TextView mUserNameTextView;
    private TextView mRegisteredTextView;
    private RoundImageView mLoginedRoundImageView;
    private ConstraintLayout mSettingConstraintLayout;
    private TextView mHeadTextView;
    private ViewGroup mDownloadManagerViewGroup;
    private ScrollViewListener mScrollViewListener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_person, container, false);
        mPresenter = new PersonPresenter(this);

        mScrollViewListener = new ScrollViewListener();

        initView();

        return mRootView;
    }

    @Override
    protected void onFragmentFirstVisible() {
        initSearchBar(mRootView, mScrollViewListener);
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
    }

    private void initView() {
        mLoginedRoundImageView = mRootView.findViewById(R.id.riv_person_logined);
        mHeadTextView = mRootView.findViewById(R.id.tv_person_login);
        //mHotViewGroup = mRootView.findViewById(R.id.cl_person_hot);
        mUserNameTextView = mRootView.findViewById(R.id.tv_user_id);
        mRegisteredTextView = mRootView.findViewById(R.id.tv_registered_time);
        //mGameViewGroup = mRootView.findViewById(R.id.cl_person_game);
        //mVideoViewGroup = mRootView.findViewById(R.id.cl_person_video);
        mHeadRoundImageView = mRootView.findViewById(R.id.riv_person);
        mSettingConstraintLayout = mRootView.findViewById(R.id.cl_person_setting);
        mAppListViewGroup = mRootView.findViewById(R.id.cl_person_applist);
        mDownloadManagerViewGroup = mRootView.findViewById(R.id.cl_person_download);

        configComponent();
    }

    private void configComponent() {
        mHeadRoundImageView.setOnClickListener(mOnClickListener);
        mHeadTextView.setOnClickListener(mOnClickListener);
        //mHotViewGroup.setOnClickListener(mOnClickListener);
        //mGameViewGroup.setOnClickListener(mOnClickListener);
        //mVideoViewGroup.setOnClickListener(mOnClickListener);
        mSettingConstraintLayout.setOnClickListener(mOnClickListener);
        mDownloadManagerViewGroup.setOnClickListener(mOnClickListener);
        mAppListViewGroup.setOnClickListener(mOnClickListener);
    }

    private OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_person_login:
                case R.id.riv_person:
                    if (!isLogin) {
                        mPresenter.intoLogin();
                    }
                    break;

                case R.id.cl_person_hot:
                    mPresenter.intoRecommend();
                    break;

               /*case R.id.cl_person_game:
                    mPresenter.intoGameList();
                    break;*/

                /*case R.id.cl_person_video:
                    mPresenter.intoVideoList();
                    break;*/

                case R.id.cl_person_setting:
                    mPresenter.intoSetting();
                    break;

                case R.id.cl_person_applist:
                    mPresenter.intoAppList();
                    break;

                case R.id.cl_person_download:
                    mPresenter.intoDownloadManager();
                    break;

                default:
                    break;

            }
        }
    };

    @Override
    public void setPresenter(PersonContract.Presenter presenter) {
        mPresenter = (PersonPresenter) presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.freeView();
        mPresenter = null;
    }

    @Override
    public void showPersonalMessage(Intent intent) {
        String name = intent.getStringExtra(USER_NAME);
        String headUrl = intent.getStringExtra(USER_HEAD_URL);
        String registeredTime = intent.getStringExtra(USER_REGISTERED_TIME);

        mHeadTextView.setVisibility(View.GONE);
        mHeadRoundImageView.setVisibility(View.GONE);

        mLoginedRoundImageView.setVisibility(View.VISIBLE);
        mUserNameTextView.setVisibility(View.VISIBLE);
        mRegisteredTextView.setVisibility(View.VISIBLE);

        mUserNameTextView.setText(getString(R.string.person_account) + name);
        mRegisteredTextView.setText(getString(R.string.person_registered_time) + registeredTime);
        GlideImageLoader.commonLoader(mRootView.getContext(),headUrl,mLoginedRoundImageView);
    }

    @Override
    public void jumpLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivityForResult(intent, LOGIN_REQUEST_CODE);
    }

    @Override
    public void jumpRecommend() {
        ((MainActivity) getActivity()).setShowFragment(GUIDE_FRAGEMNT_ITEM);
    }

    @Override
    public void jumpGameList() {
        ((MainActivity) getActivity()).setShowFragment(GAME_LIST_FRAGEMNT_ITEM);
    }

    @Override
    public void jumpVideoList() {
        Intent intent = new Intent(getActivity(), VideoListActivity.class);
        startActivity(intent);
    }

    @Override
    public void jumpSetting() {
        Intent i = new Intent(getActivity(), SettingActivity.class);
        startActivity(i);
    }
    
	@Override
    public void jumpAppList() {
        startActivity(new Intent(getActivity(), AppManagerActivity.class));
    }

    @Override
    public void jumpDownloadManager() {
        Intent intent = new Intent(getActivity(), DownloadManagerActivity.class);
        startActivity(intent);
    }

    @Override
    public Context getViewContext() {
        return mRootView.getContext();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == LOGIN_OK) {
            switch (requestCode) {
                case LOGIN_REQUEST_CODE:
                    if (data != null) {
                        showPersonalMessage(data);
                    }
                    break;

                default:
                    break;
            }
        }
    }

    class ScrollViewListener extends BaseScrollViewLisenter {

        @Override
        protected int getBannerHeight() {
            return 0;
        }

        @Override
        protected View getSearchBackground() {
            return mRootView.findViewById(R.id.v_search_bg);
        }

        @Override
        protected Activity getHostActivity() {
            return getActivity();
        }

        @Override
        protected View getSearchToolbar() {
            return mRootView.findViewById(R.id.tv_search);
        }
    }
}
