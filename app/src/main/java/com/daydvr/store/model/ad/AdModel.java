package com.daydvr.store.model.ad;

import android.os.Message;

import com.daydvr.store.util.LoaderHandler;

import java.util.ArrayList;
import java.util.List;

import static com.daydvr.store.base.BaseConstant.AD_LOADER_OK;

/**
 * @author LoSyc
 * @version Created on 2017/12/28. 9:27
 */

public class AdModel {
    private LoaderHandler mHandler;
    List<String> mAdDatas = new ArrayList<>();

    public void getADBannerUrls() {
        List<String> datas = new ArrayList<>();
        datas.add("http://www.monsterhunterworld.com/images/top/img_intro01.jpg");
        datas.add("http://www.monsterhunterworld.com/images/top/img_intro02.jpg");
        datas.add("http://img.3dmgame.com/uploads/allimg/170614/316-1F614100A1.jpg");
        mAdDatas.addAll(datas);

        if (mHandler != null) {
            Message msg = mHandler.createMessage(AD_LOADER_OK, 0, 0, datas);
            mHandler.sendMessage(msg);
        }
    }

    private List<String> getAdDatas() {
        return mAdDatas;
    }

    public void setHandler(LoaderHandler handler) {
        mHandler = handler;
    }
}
