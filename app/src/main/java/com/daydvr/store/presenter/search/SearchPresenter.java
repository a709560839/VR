package com.daydvr.store.presenter.search;

import android.app.Activity;
import android.os.Message;

import com.daydvr.store.bean.SearchListBean;
import com.daydvr.store.model.search.SearchModel;
import com.daydvr.store.util.LoaderHandler;

import java.util.ArrayList;
import java.util.List;

import static com.daydvr.store.base.BaseApplication.MultiThreadPool;
import static com.daydvr.store.base.BaseConstant.SEARCH_LOADER_OK;
import static com.daydvr.store.base.BaseConstant.NORMAL;
import static com.daydvr.store.base.BaseConstant.CATEGORY;
import static com.daydvr.store.base.BaseConstant.SHOW_SOFT_INPUT;

/**
 * @author LoSyc
 * @version Created on 2017/12/28. 19:12
 */

public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View mView;

    private LoaderHandler mHandler;

    private SearchModel mSearchModel;
    private List<SearchListBean> mSeachDatas = new ArrayList<>();

    public SearchPresenter(SearchContract.View view) {
        this.mView = view;

        mHandler = new LoaderHandler();
        mHandler.setListener(mHandleListener);

        mSearchModel = new SearchModel();
        mView.setPresenter(this);

        mSearchModel.setHandler(mHandler);
    }

    @Override
    public void freeView() {
        mView = null;
    }

    @Override
    public void initUtils(Activity activity) {

    }

    @Override
    public void loadSearchDatas() {
        MultiThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                mSearchModel.getSearchData();
            }
        });
    }

    @Override
    public void searchCategory(final int type) {
        MultiThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                List<SearchListBean> datas = new ArrayList<>();
                for (SearchListBean bean : mSeachDatas) {
                    String str = bean.getType();
                    if (type == 0) {
                        if ("游戏".equals(str)) {
                            datas.add(bean);
                        }
                    }
                    if (type == 1) {
                        if ("视频".equals(str)) {
                            datas.add(bean);
                        }
                    }
                }
                Message msg = mHandler.createMessage(SEARCH_LOADER_OK, CATEGORY, 0, datas);
                mHandler.sendMessage(msg);
            }
        });
    }

    @Override
    public void intoGameDetail(int apkId) {
        if (mView != null) {
            mView.jumpGameDetail(apkId);
        }
    }

    @Override
    public void intoVideoDetail() {
        if (mView != null) {
            mView.jumpVideoDetail(null);
        }
    }

    @Override
    public void showSoftInput() {
        Message msg = mHandler.createMessage(SHOW_SOFT_INPUT, 0, 0, null);
        mHandler.sendMessageDelayed(msg,500);
    }

    private LoaderHandler.LoaderHandlerListener mHandleListener = new LoaderHandler.LoaderHandlerListener() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SEARCH_LOADER_OK:
                    if (msg.arg1 == NORMAL) {
                        mSeachDatas.addAll((List<SearchListBean>) msg.obj);
                        if (mSeachDatas != null && mView != null) {
                            mView.showSearchDatas(mSeachDatas);
                        }
                    }
                    if (msg.arg1 == CATEGORY && mView != null) {
                        mView.showCategorySearch((List<SearchListBean>) msg.obj);
                    }
                    break;

                case SHOW_SOFT_INPUT:
                    if(null!=mView){
                        mView.showSoftInput();
                    }
                    break;

                default:
                    break;
            }
        }
    };
}
