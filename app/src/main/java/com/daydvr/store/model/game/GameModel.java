package com.daydvr.store.model.game;

import android.os.Message;

import com.daydvr.store.bean.GameBean;
import com.daydvr.store.bean.GameListBean;
import com.daydvr.store.util.LoaderHandler;

import java.util.ArrayList;
import java.util.List;

import static com.daydvr.store.base.BaseConstant.DOWNLOAD_RANKING_LOADER_OK;
import static com.daydvr.store.base.BaseConstant.EXCHANGE_DATA_LOADER_OK;
import static com.daydvr.store.base.BaseConstant.GAME_DETAIL_OK;
import static com.daydvr.store.base.BaseConstant.GAME_DETAIL_PIC_OK;
import static com.daydvr.store.base.BaseConstant.GAME_LOADER_OK;
import static com.daydvr.store.base.BaseConstant.NEWS_RANKING_LOADER_OK;
import static com.daydvr.store.base.BaseConstant.SOARING_RANKING_LOADER_OK;

/**
 * @author LoSyc
 * @version Created on 2017/12/28. 9:34
 */

public class GameModel {
    private LoaderHandler mHandler;

    private static List<GameListBean> mGameDatas = new ArrayList<>();
    private static List<GameListBean> mSoaringRankingDatas = new ArrayList<>();
    private static List<GameListBean> mNewsRankingDatas = new ArrayList<>();
    private static List<GameListBean> mDownloadRankingDatas = new ArrayList<>();
    private static List<GameListBean> mExchangeDatas = new ArrayList<>();

    public void getGameListDatas(int page) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<GameListBean> datas = new ArrayList<>();
        for (int start = (page - 1) * 15; start < 15 * page; start++) {
            GameListBean bean = new GameListBean();
            bean.setId(start);
            bean.setName("游戏：" + start);
            bean.setPackageName("游戏：" + start);
            bean.setIconUrl("https://img.tapimg.com/market/lcs/a74d23d8f38335d04d25317cbf44349e_360.png");
            bean.setVersion(1);
            bean.setRating((int) (Math.random() * 3) + 3);
            int integral = (int) (Math.random() * 100 + 123);
            if (integral > 150) {
                bean.setIntegral(integral);
            }
            bean.setType("类型：" + start);
            bean.setSize((long) (Math.random() * 100 + 100));


            /**
             * 此处假数据使用apkId遍历是否加载过游戏数据
             * 在真实环境下可以直接使用下面的判断是否包含
             */

            if (start < 4) {
                datas.add(mGameDatas.get(start));
                continue;
            }

            if (mGameDatas.contains(bean)) {
                continue;
            }

            datas.add(bean);
            mGameDatas.add(bean);
        }
        if (mHandler != null) {
            Message msg = mHandler.createMessage(GAME_LOADER_OK, 0, 0, datas);
            mHandler.sendMessage(msg);
        }
    }

    public void getDownloadRankingDatas(int page) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<GameListBean> datas = new ArrayList<>();
        for (GameListBean bean : mGameDatas) {
            if (bean.getId() % 2 == 0) {
                if (!mDownloadRankingDatas.contains(bean)) {
                    mDownloadRankingDatas.add(bean);
                    datas.add(bean);
                }
            }
        }
        if (mHandler != null) {
            Message msg = mHandler.createMessage(DOWNLOAD_RANKING_LOADER_OK, 0, 0, datas);
            mHandler.sendMessage(msg);
        }
    }

    public void getNewsRankingDatas(int page) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<GameListBean> datas = new ArrayList<>();
        for (GameListBean bean : mGameDatas) {
            if (bean.getId() % 3 == 0) {
                if (!mNewsRankingDatas.contains(bean)) {
                    mNewsRankingDatas.add(bean);
                    datas.add(bean);
                }
            }
        }
        if (mHandler != null) {
            Message msg = mHandler.createMessage(NEWS_RANKING_LOADER_OK, 0, 0, datas);
            mHandler.sendMessage(msg);
        }
    }

    public void getSoaringRankingDatas(int page) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<GameListBean> datas = new ArrayList<>();
        for (GameListBean bean : mGameDatas) {
            if (bean.getId() % 4 == 0) {
                if (!mSoaringRankingDatas.contains(bean)) {
                    mSoaringRankingDatas.add(bean);
                    datas.add(bean);
                }
            }
        }
        if (mHandler != null) {
            Message msg = mHandler.createMessage(SOARING_RANKING_LOADER_OK, 0, 0, datas);
            mHandler.sendMessage(msg);
        }
    }

    public void getHotGameListDatas() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<GameListBean> datas = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            GameListBean bean = new GameListBean();
            bean.setId(i);
            bean.setName("游戏：" + i);
            bean.setPackageName("游戏：" + i);
            bean.setIconUrl("https://img.tapimg.com/market/lcs/a74d23d8f38335d04d25317cbf44349e_360.png");
            bean.setVersion(1);
            bean.setRating((int) (Math.random() * 2) + 3);
            int integral = (int) (Math.random() * 100 + 123);
            if (integral > 150) {
                bean.setIntegral(integral);
            }
            bean.setType("类型：" + i);
            bean.setSize((long) (Math.random() * 100 + 100));
            datas.add(bean);
        }
        mGameDatas.addAll(datas);
        if (mHandler != null) {
            Message msg = mHandler.createMessage(GAME_LOADER_OK, 0, 0, datas);
            mHandler.sendMessage(msg);
        }
    }

    public void getExchangeGameListDatas() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<GameListBean> datas = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            GameListBean bean = new GameListBean();
            bean.setId(i);
            bean.setName("游戏：" + i);
            bean.setPackageName("游戏：" + i);
            bean.setIconUrl("https://img.tapimg.com/market/lcs/a74d23d8f38335d04d25317cbf44349e_360.png");
            bean.setVersion(1);
            bean.setRating((int) (Math.random() * 2) + 3);
            int integral = (int) (Math.random() * 100 + 123);
            if (integral > 150) {
                bean.setIntegral(integral);
            }
            bean.setType("类型：" + i);
            bean.setSize((long) (Math.random() * 100 + 100));
            datas.add(bean);
        }
        mExchangeDatas.addAll(datas);
        if (mHandler != null) {
            Message msg = mHandler.createMessage(EXCHANGE_DATA_LOADER_OK, 0, 0, datas);
            mHandler.sendMessage(msg);
        }
    }

    public void getGameDetailData() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        GameBean bean = new GameBean();
        if (mHandler != null) {
            Message msg = mHandler.createMessage(GAME_LOADER_OK, GAME_DETAIL_OK, 0, bean);
            mHandler.sendMessage(msg);
        }
    }

    public void getGamePicDatas(){
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<String> data = new ArrayList<>();
        data.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3596752690,1143575717&fm=27&gp=0.jpg");
        data.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1598489765,1705985192&fm=27&gp=0.jpg");
        data.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3849430457,2850967814&fm=11&gp=0.jpg");
        data.add("http://01.imgmini.eastday.com/mobile/20171127/6d6351b53cafb6d236685dfabbb6e6b7_wmk.jpeg");
        if(mHandler!=null){
            Message msg = mHandler.createMessage(GAME_LOADER_OK, GAME_DETAIL_PIC_OK, 0, data);
            mHandler.sendMessage(msg);
        }
    }

    public static List<GameListBean> getGameDatas() {
        return mGameDatas;
    }

    public void setHandler(LoaderHandler handler) {
        mHandler = handler;
    }
}
