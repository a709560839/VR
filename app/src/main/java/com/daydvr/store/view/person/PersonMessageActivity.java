package com.daydvr.store.view.person;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daydvr.store.R;
import com.daydvr.store.base.BaseActivity;
import com.daydvr.store.presenter.person.PersonMessageContract;
import com.daydvr.store.presenter.person.PersonMessagePresenter;
import com.daydvr.store.util.GlideImageLoader;
import com.daydvr.store.view.custom.CommonToolbar;
import com.daydvr.store.view.custom.CustomPopupWindow;
import com.daydvr.store.view.custom.RoundImageView;

import java.io.FileNotFoundException;

import static com.daydvr.store.base.PersonConstant.CHANGE_BIRTHDAY_REQUEST_CODE;
import static com.daydvr.store.base.PersonConstant.CHANGE_OK;
import static com.daydvr.store.base.PersonConstant.CHANGE_PHONE_REQUEST_CODE;
import static com.daydvr.store.base.PersonConstant.CHANGE_GENDER_REQUEST_CODE;
import static com.daydvr.store.base.PersonConstant.CUT_PICKER_PHOTO_REQUEST_CODE;
import static com.daydvr.store.base.PersonConstant.PERSON_MSG_AVATAR;
import static com.daydvr.store.base.PersonConstant.PERSON_MSG_BIRTHDAY;
import static com.daydvr.store.base.PersonConstant.PERSON_MSG_GENDER;
import static com.daydvr.store.base.PersonConstant.PERSON_MSG_TELEPHONE;
import static com.daydvr.store.base.PersonConstant.PICKER_CAMERA_REQUEST_CODE;
import static com.daydvr.store.base.PersonConstant.PICKER_PHOTO_REQUEST_CODE;
import static com.daydvr.store.base.PersonConstant.cutedPhotoUri;

/**
 * @author LoSyc
 * @version Created on 2018/1/15. 19:06
 */

public class PersonMessageActivity extends BaseActivity implements PersonMessageContract.View, View.OnClickListener {

    private PersonMessagePresenter mPresenter;

    private CommonToolbar mCommonToolbar;
    private View mPickerPhoto;
    private View mSex;
    private View mBirthday;
    private View mPhone;
    private View mPassword;
    private View mLogout;
    private RoundImageView mAvatarRoundImageView;
    private TextView mGenderTextView;
    private TextView mBirthdayTextView;
    private TextView mTelephoneTextView;
    private CustomPopupWindow mPopupWindow;
    private TextView mOpenCameraTextView;
    private TextView mOpenGalleryTextView;
    private TextView mCancelTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_message);
        mPresenter = new PersonMessagePresenter(this);
        mPresenter.initUtils(this);

        initView();

        initDatas();
    }

    private void initView() {
        mCommonToolbar = findViewById(R.id.toolbar);
        mPickerPhoto = findViewById(R.id.v_person_msg_picker_head);
        mSex = findViewById(R.id.v_person_msg_change_gender);
        mBirthday = findViewById(R.id.v_person_msg_change_birthday);
        mPhone = findViewById(R.id.v_person_msg_change_phone);
        mPassword = findViewById(R.id.v_person_msg_change_password);
        mLogout = findViewById(R.id.v_person_msg_logout);
        mAvatarRoundImageView = findViewById(R.id.riv_person_msg_head);
        mGenderTextView = findViewById(R.id.tv_person_msg_gender);
        mBirthdayTextView = findViewById(R.id.tv_person_msg_birthday);
        mTelephoneTextView = findViewById(R.id.tv_person_msg_phone);

        mPopupWindow = new CustomPopupWindow.Builder()
                .setContext(this)
                .setContentView(R.layout.popup_picker_photo)
                .setwidth(LinearLayout.LayoutParams.MATCH_PARENT)
                .setheight(LinearLayout.LayoutParams.WRAP_CONTENT)
                .setFouse(true)
                .setOutSideCancel(true)
                .setAnimationStyle(R.style.popup_anim_style)
                .builder();
        mOpenCameraTextView = (TextView) mPopupWindow.getItemView(R.id.tv_camera);
        mOpenGalleryTextView = (TextView) mPopupWindow.getItemView(R.id.tv_gallery);
        mCancelTextView = (TextView) mPopupWindow.getItemView(R.id.tv_cancel);

        configComponent();
    }

    private void configComponent() {
        mCommonToolbar.setCenterTitle(getString(R.string.person_message));
        mCommonToolbar.initmToolBar(this, false);

        mPickerPhoto.setOnClickListener(this);
        mSex.setOnClickListener(this);
        mBirthday.setOnClickListener(this);
        mPhone.setOnClickListener(this);
        mPassword.setOnClickListener(this);
        mLogout.setOnClickListener(this);

        mOpenCameraTextView.setOnClickListener(this);
        mOpenGalleryTextView.setOnClickListener(this);
        mCancelTextView.setOnClickListener(this);
    }

    private void initDatas() {
        mPresenter.loadDatas();
    }

    @Override
    public void setPresenter(PersonMessageContract.Presenter presenter) {
        mPresenter = (PersonMessagePresenter) presenter;
    }

    @Override
    public void showAvatar(Intent data, boolean isLocalImage) {
        if (isLocalImage) {
            Uri imageUri = cutedPhotoUri;
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            mAvatarRoundImageView.setImageBitmap(bitmap);
        } else {
            GlideImageLoader.commonLoader(this, data.getStringExtra(PERSON_MSG_AVATAR), mAvatarRoundImageView);
        }
    }

    @Override
    public void showGender(Intent data) {
        if (data != null) {
            mGenderTextView.setText(data.getStringExtra(PERSON_MSG_GENDER));
        }
    }

    @Override
    public void showBirthday(Intent data) {
        if (data != null) {
            mBirthdayTextView.setText(data.getStringExtra(PERSON_MSG_BIRTHDAY));
        }
    }

    @Override
    public void showTelephone(Intent data) {
        if (data != null) {
            String originNum = data.getStringExtra(PERSON_MSG_TELEPHONE);
            String phoneStart = originNum.substring(0, 3);
            String phoneEnd = originNum.substring(7, 11);
            String phone = phoneStart + "****" + phoneEnd;
            mTelephoneTextView.setText(phone);
        }
    }

    @Override
    public void jumpPickerPhoto() {
        mPopupWindow.showAtLocation(R.layout.activity_person_message, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void jumpChangeGender() {
        Intent intent = new Intent(this, SelectGenderActivity.class);
        startActivityForResult(intent, CHANGE_GENDER_REQUEST_CODE);
    }

    @Override
    public void jumpChangeBirthday() {
        Intent intent = new Intent(this, SelectBirthdayActivity.class);
        intent.putExtra(PERSON_MSG_BIRTHDAY, mBirthdayTextView.getText());
        startActivityForResult(intent, CHANGE_BIRTHDAY_REQUEST_CODE);
    }

    @Override
    public void jumpChangePhone() {
        Intent intent = new Intent(this, VerifyNumActivity.class);
        startActivityForResult(intent, CHANGE_PHONE_REQUEST_CODE);
    }

    @Override
    public void jumpChangePassword() {
        Intent intent = new Intent(this, ChangePwdActivity.class);
        startActivity(intent);
    }

    @Override
    public void jumpGallery(Uri imageUri) {
        Intent galleryIntent = new Intent("android.intent.action.PICK");
        galleryIntent.setType("image/*");
        galleryIntent.putExtra("crop", true);
        galleryIntent.putExtra("scale", true);
        galleryIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(galleryIntent, PICKER_PHOTO_REQUEST_CODE);
    }

    @Override
    public void jumpCamera(Uri imageUri) {
        Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, PICKER_CAMERA_REQUEST_CODE);
    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICKER_PHOTO_REQUEST_CODE:
                    if (data != null) {
                        mPresenter.cutPhoto(PICKER_PHOTO_REQUEST_CODE, data);
                    }
                    break;

                case PICKER_CAMERA_REQUEST_CODE:
                    mPresenter.cutPhoto(PICKER_CAMERA_REQUEST_CODE, data);
                    break;

                case CUT_PICKER_PHOTO_REQUEST_CODE:
                    if (data != null) {
                        showAvatar(data, true);
                    }
                    break;
                default:
                    break;
            }
        }
        if (resultCode == CHANGE_OK) {
            switch (requestCode) {
                case CHANGE_GENDER_REQUEST_CODE:
                    if (data != null) {
                        showGender(data);
                    }
                    break;

                case CHANGE_BIRTHDAY_REQUEST_CODE:
                    if (data != null) {
                        showBirthday(data);
                    }
                    break;

                case CHANGE_PHONE_REQUEST_CODE:
                    if (data != null) {
                        showTelephone(data);
                    }
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.v_person_msg_picker_head:
                mPresenter.pickerPhoto();
                break;

            case R.id.v_person_msg_change_gender:
                mPresenter.intoChangeGender();
                break;

            case R.id.v_person_msg_change_birthday:
                mPresenter.intoChangeBirthday();
                break;

            case R.id.v_person_msg_change_phone:
                mPresenter.intoChangePhone();
                break;

            case R.id.v_person_msg_change_password:
                mPresenter.intoChangePassword();
                break;

            case R.id.v_person_msg_logout:
                mPresenter.logout();
                break;

            case R.id.tv_camera:
                mPresenter.openCamera();
                break;

            case R.id.tv_gallery:
                mPresenter.openGallery();
                break;

            case R.id.tv_cancel:
                mPopupWindow.dismiss();
                break;

            default:
                break;
        }
    }
}
