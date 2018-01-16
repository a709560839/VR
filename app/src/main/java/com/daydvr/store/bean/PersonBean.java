package com.daydvr.store.bean;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author LoSyc
 * @version Created on 2018/1/16. 15:37
 */

public class PersonBean implements Parcelable {
        private static PersonBean user;

    private int _id;
    private String avatarUrl;
    private String birthday;
    private String userName;
    private  int gender;
    private String telephone;
    private int integral;

    protected PersonBean(Parcel in) {
        _id = in.readInt();
        avatarUrl = in.readString();
        birthday = in.readString();
        userName = in.readString();
        gender = in.readInt();
        telephone = in.readString();
        integral = in.readInt();
    }

    public static final Creator<PersonBean> CREATOR = new Creator<PersonBean>() {
        @Override
        public PersonBean createFromParcel(Parcel in) {
            return new PersonBean(in);
        }

        @Override
        public PersonBean[] newArray(int size) {
            return new PersonBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(avatarUrl);
        dest.writeString(birthday);
        dest.writeString(userName);
        dest.writeInt(gender);
        dest.writeString(telephone);
        dest.writeInt(integral);
    }

    private PersonBean() {

    }

    public static PersonBean getInstance() {
        if (user == null) {
            user = new PersonBean();
        }
        return user;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }
}
