package com.daydvr.store.base;

import com.daydvr.store.view.adapter.GameListAdapter;

import java.util.List;

import static com.daydvr.store.base.BaseConstant.NOTIFY_ALL;

/**
 * @author LoSyc
 * @version Created on 2018/1/11. 19:45
 */

public abstract class BaseNotifyDatasFragment extends BaseFragment {
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            List<Integer> list = getDownloadDatas();
            if (getListAdapter() != null && list.size() > 0) {
                if (list.get(0) == NOTIFY_ALL) {
                    getListAdapter().notifyDataSetChanged();
                    return;
                }
                for (int position : list) {
                    getListAdapter().notifyItemChanged(position);
                }
            }
        }
    }

    abstract protected List<Integer> getDownloadDatas();

    public abstract GameListAdapter getListAdapter();
}
