package com.daydvr.store.view.setting;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import com.daydvr.store.R;
import com.daydvr.store.base.BaseConstant;

/*
 * Copyright (C) 2018 3ivr. All rights reserved.
 *
 * @author: Jason(Liu ZhiCheng)
 * @mail  : jason@3ivr.cn
 * @date  : 2018/01/08 16:03
 */

public class SettingFragment extends PreferenceFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.perference_setting);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (BaseConstant.SETTING_CHECK_VERSION_KEY.equals(preference.getKey())) {
            // TODO 检查版本是否有更新
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}
