package com.daydvr.store.bean;

import android.graphics.drawable.Drawable;
import java.util.List;

/**
 * Created by LoSyc on 2017/11/14.
 */

public class AppListBean {
    public String status;
    public String msg;
    public List<ApkInfo> data;

    public static class ApkInfo {
        public int id;
        public String name;
        public String filename;
        public String package_name;
        public String version_name;
        public String version_code;
        public int mini_sdk_level;
        public String mini_sdk_platform;
        public int game_follow;
        public  String size;
        public int cracked;
        public String language;
        public int downloads;
        public String update_description;
        public int app_id;
        public int dfs_file_id;
        public String created_at;
        public String updated_at;
        public String game_comment;
        public int game_stars;
        public String game_icon_url;
        public Drawable game_icon_drawable;
        public String download_url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getPackage_name() {
            return package_name;
        }

        public void setPackage_name(String package_name) {
            this.package_name = package_name;
        }

        public String getVersion_name() {
            return version_name;
        }

        public void setVersion_name(String version_name) {
            this.version_name = version_name;
        }

        public String getVersion_code() {
            return version_code;
        }

        public void setVersion_code(String version_code) {
            this.version_code = version_code;
        }

        public int getMini_sdk_level() {
            return mini_sdk_level;
        }

        public void setMini_sdk_level(int mini_sdk_level) {
            this.mini_sdk_level = mini_sdk_level;
        }

        public String getMini_sdk_platform() {
            return mini_sdk_platform;
        }

        public void setMini_sdk_platform(String mini_sdk_platform) {
            this.mini_sdk_platform = mini_sdk_platform;
        }

        public int getGame_follow() {
            return game_follow;
        }

        public void setGame_follow(int game_follow) {
            this.game_follow = game_follow;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public int getCracked() {
            return cracked;
        }

        public void setCracked(int cracked) {
            this.cracked = cracked;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public int getDownloads() {
            return downloads;
        }

        public void setDownloads(int downloads) {
            this.downloads = downloads;
        }

        public String getUpdate_description() {
            return update_description;
        }

        public void setUpdate_description(String update_description) {
            this.update_description = update_description;
        }

        public int getApp_id() {
            return app_id;
        }

        public void setApp_id(int app_id) {
            this.app_id = app_id;
        }

        public int getDfs_file_id() {
            return dfs_file_id;
        }

        public void setDfs_file_id(int dfs_file_id) {
            this.dfs_file_id = dfs_file_id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getGame_comment() {
            return game_comment;
        }

        public void setGame_comment(String game_comment) {
            this.game_comment = game_comment;
        }

        public int getGame_stars() {
            return game_stars;
        }

        public void setGame_stars(int game_stars) {
            this.game_stars = game_stars;
        }

        public String getGame_icon_url() {
            return game_icon_url;
        }

        public void setGame_icon_url(String game_icon_url) {
            this.game_icon_url = game_icon_url;
        }

        public Drawable getGame_icon_drawable() {
            return game_icon_drawable;
        }

        public void setGame_icon_drawable(Drawable game_icon_drawable) {
            this.game_icon_drawable = game_icon_drawable;
        }

        public String getDownload_url() {
            return download_url;
        }

        public void setDownload_url(String download_url) {
            this.download_url = download_url;
        }
    }
}
