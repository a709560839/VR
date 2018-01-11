package com.daydvr.store.view.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.daydvr.store.R;
import com.daydvr.store.base.BaseActivity;
import com.daydvr.store.bean.SearchListBean;
import com.daydvr.store.presenter.search.SearchContract;
import com.daydvr.store.presenter.search.SearchPresenter;
import com.daydvr.store.util.DensityUtil;
import com.daydvr.store.view.adapter.SearchListAdapter;
import com.daydvr.store.view.custom.XEditText;
import com.daydvr.store.view.custom.XEditText.DrawableLeftListener;
import com.daydvr.store.view.custom.XEditText.DrawableRightListener;
import com.daydvr.store.view.game.GameDetailActivity;
import com.daydvr.store.view.video.VideoDetailActivity;

import java.util.ArrayList;
import java.util.List;

import static com.daydvr.store.base.GameConstant.APK_ID;

/**
 * @author LoSyc
 * @version Created on 2017/12/28. 13:30
 */

public class SearchActivity extends BaseActivity implements SearchContract.View {
    private Toolbar mToolbar;
    private SearchPresenter mPresenter;
    private RecyclerView mSearchRecyclerView;
    private SearchListAdapter mSearchListAdapter;
    private XEditText mSearchEditText;
    private TextView mTypeGameTextView;
    private TextView mTypeVideoTextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#0ca4ea"));
        }
        mPresenter = new SearchPresenter(this);

        initView();
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        mSearchRecyclerView = findViewById(R.id.rv_search_list);
        mSearchEditText = findViewById(R.id.et_search);
        mTypeGameTextView = findViewById(R.id.search_type_game);
        mTypeVideoTextView = findViewById(R.id.search_type_video);

        configComponent();
    }

    private void configComponent() {
        mToolbar.setPadding(0, DensityUtil.getStatusBarHeight(this),0,0);

        mSearchRecyclerView.setHasFixedSize(true);
        mSearchRecyclerView.setNestedScrollingEnabled(false);
        mSearchRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mSearchRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mSearchEditText.setImeOptions(EditorInfo.IME_ACTION_SEND);
        mSearchEditText.setOnEditorActionListener(mTextActionListener);
        mSearchEditText.setDrawableLeftListener(mDrawableLeftListener);
        mSearchEditText.setDrawableRightListener(mDrawableRightListener);
        mTypeGameTextView.setOnClickListener(mTypeClickListener);
        mTypeVideoTextView.setOnClickListener(mTypeClickListener);
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        mPresenter = (SearchPresenter) presenter;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mPresenter.showSoftInput();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.freeView();
        mPresenter = null;
    }

    @Override
    public <T> void showSearchDatas(List<T> beans) {
        if (mSearchListAdapter == null) {
            mSearchListAdapter = new SearchListAdapter(15);
            mSearchListAdapter.setListener(mItemListener);
            mSearchRecyclerView.setAdapter(mSearchListAdapter);
            mSearchListAdapter.setDatas((ArrayList<SearchListBean>) beans);
        }
        mSearchListAdapter.notifyDataSetChanged();
    }

    @Override
    public <T> void showCategorySearch(List<T> beans) {
        if (mSearchListAdapter == null) {
            mSearchListAdapter = new SearchListAdapter(15);
            mSearchListAdapter.setListener(mItemListener);
            mSearchRecyclerView.setAdapter(mSearchListAdapter);
        }
        mSearchListAdapter.setDatas((ArrayList<SearchListBean>) beans);
        mSearchListAdapter.notifyDataSetChanged();
    }

    @Override
    public void jumpGameDetail(int apkId) {
        Intent intent = new Intent(this, GameDetailActivity.class);
        intent.putExtra(APK_ID, apkId);
        startActivity(intent);
    }

    @Override
    public void jumpVideoDetail(Bundle bundle) {
        Intent intent = new Intent(this, VideoDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void showSoftInput() {
        mSearchEditText.setFocusable(true);
        mSearchEditText.setFocusableInTouchMode(true);
        mSearchEditText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager)mSearchEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(null!=inputManager){
            inputManager.showSoftInput(mSearchEditText, 0);
        }
    }

    private TextView.OnEditorActionListener mTextActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEND
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                mPresenter.loadSearchDatas();
                View viewFocus = v.findFocus();
                if (viewFocus != null) {
                    InputMethodManager imManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(null!=imManager){
                        imManager.hideSoftInputFromWindow(viewFocus.getWindowToken(), 0);
                    }
                }
            }
            return true;
        }
    };

    private SearchListAdapter.ItemOnClickListener mItemListener = new SearchListAdapter.ItemOnClickListener() {

        @Override
        public void onItemClick(View view, SearchListBean bean) {
            if ("游戏".equals(bean.getType())) {
                mPresenter.intoGameDetail(bean.getId());
            }
            if ("视频".equals(bean.getType())) {
                mPresenter.intoVideoDetail();
            }
        }
    };

    private View.OnClickListener mTypeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.search_type_game:
                    mPresenter.searchCategory(0);
                    break;

                case R.id.search_type_video:
                    mPresenter.searchCategory(1);
                    break;

                default:
                    break;
            }
        }
    };

    private DrawableLeftListener mDrawableLeftListener = new DrawableLeftListener() {
        @Override
        public void onDrawableLeftClick(View view) {
            finish();
        }
    };

    private DrawableRightListener mDrawableRightListener = new DrawableRightListener() {
        @Override
        public void onDrawableRightClick(View view) {
            mPresenter.loadSearchDatas();
        }
    };
}
