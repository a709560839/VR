package com.daydvr.store.base;
/*
 * Copyright (C) 2018 3ivr. All rights reserved.
 *
 * @author: Jason(Liu ZhiCheng)
 * @mail  : jason@3ivr.cn
 * @date  : 2018/01/05 10:18
 */

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;

import android.view.View.OnClickListener;
import android.widget.RelativeLayout.LayoutParams;
import com.daydvr.store.R;

import com.daydvr.store.util.Logger;
import com.daydvr.store.view.adapter.GameListAdapter;

import com.daydvr.store.util.DensityUtil;

import com.daydvr.store.view.custom.AppNestedScrollView;
import com.daydvr.store.view.search.SearchActivity;

public abstract class BaseFragment extends Fragment {

    private boolean isFragmentVisible;
    private boolean isReuseView;
    private boolean isFirstVisible;
    private View mRootView;

    /**
     * Created by dasu on 2016/9/27.
     *
     * 1、Viewpager + Fragment情况下，fragment的生命周期因Viewpager的缓存机制而失去了具体意义
     * 该抽象类自定义新的回调方法，当fragment可见状态改变时会触发的回调方法，和 Fragment 第一次可见时会回调的方法
     *
     * @see #onFragmentVisibleChange(boolean)
     * @see #onFragmentFirstVisible()
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //如果setUserVisibleHint()在rootView创建前调用时，那么
        //就等到rootView创建完后才回调onFragmentVisibleChange(true)
        //保证onFragmentVisibleChange()的回调发生在rootView创建完成之后，以便支持ui操作
        if (mRootView == null) {
            mRootView = view;
            if (getUserVisibleHint()) {
                if (isFirstVisible) {
                    onFragmentFirstVisible();
                    isFirstVisible = false;
                }
                onFragmentVisibleChange(true);
                isFragmentVisible = true;
            }
        }
        super.onViewCreated(isReuseView ? mRootView : view, savedInstanceState);
    }

    /**
     * setUserVisibleHint()在Fragment创建时会先被调用一次，传入isVisibleToUser = false
     * 如果当前Fragment可见，那么setUserVisibleHint()会再次被调用一次，传入isVisibleToUser = true
     * 如果Fragment从可见->不可见，那么setUserVisibleHint()也会被调用，传入isVisibleToUser = false
     * 总结：setUserVisibleHint()除了Fragment的可见状态发生变化时会被回调外，在new Fragment()时也会被回调
     * 如果我们需要在 Fragment 可见与不可见时干点事，用这个的话就会有多余的回调了，那么就需要重新封装一个,如下
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //setUserVisibleHint()有可能在fragment的生命周期外被调用
        if (mRootView == null) {
            return;
        }
        if (isFirstVisible && isVisibleToUser) {
            onFragmentFirstVisible();
            isFirstVisible = false;
        }
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            isFragmentVisible = false;
            onFragmentVisibleChange(false);
        }
    }

    private void initVariable() {
        isFirstVisible = true;
        isFragmentVisible = false;
        mRootView = null;
        isReuseView = true;
    }

    /**
     * 设置是否使用 view 的复用，默认开启
     * view 的复用是指，ViewPager 在销毁和重建 Fragment 时会不断调用 onCreateView() -> onDestroyView()
     * 之间的生命函数，这样可能会出现重复创建 view 的情况，导致界面上显示多个相同的 Fragment
     * view 的复用其实就是指保存第一次创建的 view，后面再 onCreateView() 时直接返回第一次创建的 view
     *
     * @param isReuse
     */
    protected void reuseView(boolean isReuse) {
        isReuseView = isReuse;
    }

    /**
     * 去除setUserVisibleHint()多余的回调场景，保证只有当fragment可见状态发生变化时才回调
     * 回调时机在view创建完后，所以支持ui操作，解决在setUserVisibleHint()里进行ui操作有可能报null异常的问题
     *
     * 可在该回调方法里进行一些ui显示与隐藏，比如加载框的显示和隐藏
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {
    }

    /**
     * 在fragment首次可见时回调，可在这里进行加载数据，保证只在第一次打开Fragment时才会加载数据，
     * 这样就可以防止每次进入都重复加载数据
     * 该方法会在 onFragmentVisibleChange() 之前调用，所以第一次打开时，可以用一个全局变量表示数据下载状态，
     * 然后在该方法内将状态设置为下载状态，接着去执行下载的任务
     * 最后在 onFragmentVisibleChange() 里根据数据下载状态来控制下载进度ui控件的显示与隐藏
     */
    protected void onFragmentFirstVisible() {
    }

    protected boolean isFragmentVisible() {
        return isFragmentVisible;
    }

    protected void initSearchBar(View view, final BaseScrollViewLisenter lisenter) {
        LayoutParams lp = (LayoutParams) view.findViewById(R.id.v_search_bg).getLayoutParams();
        lp.height = DensityUtil.getStatusBarHeight(view.getContext()) + DensityUtil.dip2px(view.getContext(), 40);
        view.findViewById(R.id.v_search_bg).setLayoutParams(lp);
        if (lisenter != null) {
            lisenter.getSearchToolbar().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Activity activity = lisenter.getHostActivity();
                    Intent intent = new Intent(activity, SearchActivity.class);
                    activity.startActivity(intent);
                }
            });
        }

    }

    public GameListAdapter getListAdapter() {
        return null;
    }

    /**
     * 内部类
     */
    public abstract class BaseScrollViewLisenter implements AppNestedScrollView.NestedScrollViewListener {

        protected ScrollViewHandle mHandle;

        protected abstract int getBannerHeight();

        protected abstract View getSearchBackground();

        protected abstract Activity getHostActivity();

        protected abstract View getSearchToolbar();

        public void setHandler(ScrollViewHandle handle) {
            mHandle = handle;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onNestedScrollChanged(AppNestedScrollView nestedScrollView, int scrollX,
                                          int scrollY, int oldScrollX, int oldScrollY) {
            float alpha = (float) scrollY / getBannerHeight();
//            int whiteFeelt = 0xF2F2F2;
            if (scrollY <= getBannerHeight()) {
                // 随着滑动距离改变透明度
                getSearchBackground().setAlpha(alpha);
//                int statusAlpha = (int) (255 * alpha);
//                if (Build.VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
//                    getHostActivity().getWindow().setStatusBarColor(whiteFeelt + (statusAlpha << 24));
//                }
                return;
            }
            alpha = 1;
            getSearchBackground().setAlpha(alpha);
//            if (Build.VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
//                getHostActivity().getWindow().setStatusBarColor(whiteFeelt + (255 << 24));
//            }
            if (mHandle != null) {
                mHandle.onNestedScrollChanged(nestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY);
            }
        }
    }

    protected interface ScrollViewHandle {
        void onNestedScrollChanged(AppNestedScrollView nestedScrollView, int scrollX,
        int scrollY, int oldScrollX, int oldScrollY);
    }
}
