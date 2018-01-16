package com.daydvr.store.presenter.person;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.daydvr.store.bean.UserBean;
import com.daydvr.store.util.PhotoUtils;

import java.io.File;

import static com.daydvr.store.base.PersonConstant.CUT_PICKER_PHOTO_REQUEST_CODE;
import static com.daydvr.store.base.PersonConstant.AVATAR_OUTPUT_FILE;
import static com.daydvr.store.base.PersonConstant.PERSON_MSG_AVATAR;
import static com.daydvr.store.base.PersonConstant.PERSON_MSG_BIRTHDAY;
import static com.daydvr.store.base.PersonConstant.PERSON_MSG_GENDER;
import static com.daydvr.store.base.PersonConstant.PERSON_MSG_TELEPHONE;
import static com.daydvr.store.base.PersonConstant.PICKER_CAMERA_REQUEST_CODE;
import static com.daydvr.store.base.PersonConstant.PICKER_PHOTO_REQUEST_CODE;

/**
 * @author LoSyc
 * @version Created on 2018/1/15. 19:47
 */

public class PersonMessagePresenter implements PersonMessageContract.Presenter {
    private PersonMessageContract.View mView;

    private PhotoUtils mPhotoUtils;

    public PersonMessagePresenter(PersonMessageContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    private Uri createFileOfHead() {
        File outputfile = new File(mView.getViewContext().getExternalCacheDir(), AVATAR_OUTPUT_FILE);
        try {
            if (outputfile.exists()) {
                outputfile.delete();
            }
            outputfile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Uri imageUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(mView.getViewContext(),
                    "com.daydvr.store.fileProvider",
                    outputfile);
        } else {
            imageUri = Uri.fromFile(outputfile);
        }
        return imageUri;
    }

    @Override
    public void freeView() {
        mView = null;
    }

    @Override
    public void initUtils(Activity activity) {
        mPhotoUtils = new PhotoUtils(activity);
    }

    @Override
    public void loadDatas() {
        UserBean bean = UserBean.getInstance();
        Intent intent = new Intent();
        intent.putExtra(PERSON_MSG_AVATAR, bean.getAvatarUrl());
        if (bean.getGender() == 1) {
            intent.putExtra(PERSON_MSG_GENDER, "男");
        } else if (bean.getGender() == 2) {
            intent.putExtra(PERSON_MSG_GENDER, "女");
        }
        intent.putExtra(PERSON_MSG_BIRTHDAY, bean.getBirthday());
        String phoneStart = bean.getTelephone().substring(0, 3);
        String phoneEnd = bean.getTelephone().substring(7, 11);
        String phone = phoneStart + "****" + phoneEnd;
        intent.putExtra(PERSON_MSG_TELEPHONE, phone);

        mView.showAvatar(intent, false);
        mView.showGender(intent);
        mView.showBirthday(intent);
        mView.showTelephone(intent);
    }

    @Override
    public void pickerPhoto() {
        if (mView != null) {
            mView.jumpPickerPhoto();
        }
    }

    @Override
    public void intoChangeGender() {
        if (mView != null) {
            mView.jumpChangeGender();
        }
    }

    @Override
    public void intoChangeBirthday() {
        if (mView != null) {
            mView.jumpChangeBirthday();
        }
    }

    @Override
    public void intoChangePhone() {
        if (mView != null) {
            mView.jumpChangePhone();
        }
    }

    @Override
    public void intoChangePassword() {
        if (mView != null) {
            mView.jumpChangePassword();
        }
    }

    @Override
    public void openCamera() {
        mView.jumpCamera(createFileOfHead());
    }

    @Override
    public void openGallery() {
        mView.jumpGallery(createFileOfHead());
    }

    @Override
    public void cutPhoto(int type, Intent data) {
        if (type == PICKER_PHOTO_REQUEST_CODE) {
            ((Activity) mView.getViewContext()).startActivityForResult(mPhotoUtils.cutForPhoto(data.getData()), CUT_PICKER_PHOTO_REQUEST_CODE);
        }
        if (type == PICKER_CAMERA_REQUEST_CODE) {
            String path = mView.getViewContext().getExternalCacheDir().getPath();
            String name = AVATAR_OUTPUT_FILE;
            ((Activity) mView.getViewContext()).startActivityForResult(mPhotoUtils.cutForCamera(path, name), CUT_PICKER_PHOTO_REQUEST_CODE);
        }
    }

    @Override
    public void logout() {

    }
}
