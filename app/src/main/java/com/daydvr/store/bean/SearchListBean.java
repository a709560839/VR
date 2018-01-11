package com.daydvr.store.bean;

/**
 * @author LoSyc
 * @version Created on 2017/12/28. 19:17
 */

public class SearchListBean {
    private String name;
    private String type;
    private String iconUrl;
    private int id;
    private int loadedPage;
    private int lastPage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoadedPage() {
        return loadedPage;
    }

    public void setLoadedPage(int loadedPage) {
        this.loadedPage = loadedPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }
}
