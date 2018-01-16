package com.daydvr.store.view.setting;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.widget.Toast;
import com.daydvr.store.R;
import com.daydvr.store.base.BaseConstant;
import com.daydvr.store.view.custom.ControllerPreference;

/*
 * Copyright (C) 2018 3ivr. All rights reserved.
 *
 * @author: Jason(Liu ZhiCheng)
 * @mail  : jason@3ivr.cn
 * @date  : 2018/01/08 16:03
 */

public class ControllerFragment extends PreferenceFragment {

    private ControllerPreference mControllerPreference;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.controller_setting);
        initPreference(getResources());
    }

    private void initPreference(Resources resources) {
        SharedPreferences mSharedPreferences = getPreferenceScreen()
                .getSharedPreferences();
        mControllerPreference = (ControllerPreference) findPreference(resources.getString(R.string.setting_controller_key));
        mControllerPreference.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(getActivity(),"intoAnOtherActivity",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (BaseConstant.SETTING_CHECK_VERSION_KEY.equals(preference.getKey())) {
            // TODO 检查版本是否有更新
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}
