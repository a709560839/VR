package com.daydvr.store.presenter.person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.daydvr.store.base.IBasePresenter;
import com.daydvr.store.base.IBaseView;

/**
 * @author LoSyc
 * @version Created on 2018/1/15. 19:21
 */

public class PersonMessageContract {
    public interface View extends IBaseView<Presenter> {
        void showAvatar(Intent data, boolean isLocalImage);

        void showGender(Intent data);

        void showBirthday(Intent data);

        void showPhone(Intent data);

        void jumpPickerPhoto();

        void jumpChangeGender();

        void jumpChangeBirthday();

        void jumpChangePhone();

        void jumpChangePassword();

        void jumpGallery(Uri imageUri);

        void jumpCamera(Uri imageUri);

        Context getViewContext();
    }

    public interface Presenter extends IBasePresenter {
        void initUtils(Activity activity);

        void loadDatas();

        void pickerPhoto();

        void intoChangeGender();

        void intoChangeBirthday();

        void intoChangePhone();

        void intoChangePassword();

        void openCamera();

        void openGallery();

        void cutPhoto(int type, Intent data);

        void logout();
    }
}
