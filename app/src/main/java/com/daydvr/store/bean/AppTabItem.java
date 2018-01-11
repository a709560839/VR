package com.daydvr.store.bean;


import com.daydvr.store.base.BaseFragment;

/**
 * Created by yx on 16/4/3.
 */
public class AppTabItem {

    /**
     * icon
     */
    public int imageResId;
    /**
     * 文本
     */
    public int lableResId;

    public Class<? extends BaseFragment>tagFragmentClz;

    public AppTabItem(int imageResId, int lableResId ,Class<? extends BaseFragment> tagFragmentClz) {
        this.imageResId = imageResId;
        this.lableResId = lableResId;
        this.tagFragmentClz = tagFragmentClz;
    }
}
