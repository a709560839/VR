package com.daydvr.store.presenter.person;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.daydvr.store.util.PhotoUtils;

import java.io.File;

import static com.daydvr.store.base.PersonConstant.CUT_PICKER_PHOTO_REQUEST_CODE;
import static com.daydvr.store.base.PersonConstant.HEAD_OUTPUT_FILE;
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
        File outputfile = new File(mView.getViewContext().getExternalCacheDir(), HEAD_OUTPUT_FILE);
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
    public void pickerPhoto() {
        if (mView != null) {
            mView.jumpPickerPhoto();
        }
    }

    @Override
    public void intoChangeSex() {
        if (mView != null) {
            mView.jumpChangeSex();
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
            String name = HEAD_OUTPUT_FILE;
            ((Activity) mView.getViewContext()).startActivityForResult(mPhotoUtils.cutForCamera(path, name), CUT_PICKER_PHOTO_REQUEST_CODE);
        }
    }

    @Override
    public void logout() {

    }
}
