package com.daydvr.store.model.search;

import android.os.Message;

import com.daydvr.store.bean.SearchListBean;
import com.daydvr.store.util.LoaderHandler;

import java.util.ArrayList;
import java.util.List;

import static com.daydvr.store.base.BaseConstant.SEARCH_LOADER_OK;

/**
 * @author LoSyc
 * @version Created on 2017/12/28. 19:55
 */

public class SearchModel {
    private LoaderHandler mHandler;

    public void getSearchData() {
        List<SearchListBean> datas = new ArrayList<>();
        for (int i = 0; i < 45; i++) {
            SearchListBean bean = new SearchListBean();
            bean.setId(i);
            if (i % 2 == 0) {
                bean.setName("游戏：" + i);
                bean.setType("游戏");
            } else {
                bean.setName("视频：" + i);
                bean.setType("视频");
            }
            bean.setIconUrl("http://www.monsterhunterworld.com/images/top/img_intro01.jpg");
            datas.add(bean);
        }
        if (mHandler != null) {
            Message msg = mHandler.createMessage(SEARCH_LOADER_OK, 0, 0, datas);
            mHandler.sendMessage(msg);
        }
    }

    public void setHandler(LoaderHandler handler) {
        mHandler = handler;
    }
}
