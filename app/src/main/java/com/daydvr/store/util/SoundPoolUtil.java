package com.daydvr.store.util;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * @author LoSyc
 * @version Created on 2017/12/28. 0:06
 */
public class SoundPoolUtil {
    private volatile static SoundPool SoundPool;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static SoundPool getInstance() {
        if (SoundPool == null) {
            AudioAttributes attributes = new AudioAttributes.Builder().setLegacyStreamType(AudioManager.STREAM_MUSIC).build();
            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setMaxStreams(1);
            builder.setAudioAttributes(attributes);
            SoundPool = builder.build();
        }
        return SoundPool;
    }
}
