package com.daydvr.store.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.widget.RemoteViews;
import android.widget.Toast;
import java.util.ArrayList;

@SuppressLint("NewApi")
public class NotifyUtil {

    private static final int FLAG = Notification.FLAG_INSISTENT;
    private int requestCode = (int) SystemClock.uptimeMillis();
    private int NOTIFICATION_ID;
    private NotificationManager nm;
    private Notification notification;
    private NotificationCompat.Builder cBuilder;
    private NotificationCompat.Builder nBuilder;
    private NotificationChannel mChannl;
    private Context mContext;


    public NotifyUtil(Context context, int id) {
        this.NOTIFICATION_ID = id;
        mContext = context;
        // 获取系统服务来初始化对象
        nm = (NotificationManager) mContext
                .getSystemService(Activity.NOTIFICATION_SERVICE);
        cBuilder = new Builder(mContext, "vrdof_" + String.valueOf(id));
        if (VERSION.SDK_INT >= VERSION_CODES.O) {
            mChannl = new NotificationChannel("vrdof_" + String.valueOf(id), "游戏下载",
                    NotificationManager.IMPORTANCE_HIGH);
            nm.createNotificationChannel(mChannl);
        }
    }

    public Builder getBuilder() {
        return cBuilder;
    }

    /**
     * 设置在顶部通知栏中的各种信息
     */
    private void setCompatBuilder(PendingIntent pendingIntent, int smallIcon, String ticker,
            String title, String content, boolean sound, boolean vibrate, boolean lights) {

        cBuilder.setContentIntent(pendingIntent);
        cBuilder.setSmallIcon(smallIcon);
        cBuilder.setTicker(ticker);
        cBuilder.setContentTitle(title);
        cBuilder.setContentText(content);
        cBuilder.setOngoing(true);
        cBuilder.setWhen(System.currentTimeMillis());
        cBuilder.setAutoCancel(true);
        cBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        int defaults = 0;

        if (sound) {
            defaults |= Notification.DEFAULT_SOUND;
        }
        if (vibrate) {
            defaults |= Notification.DEFAULT_VIBRATE;
        }
        if (lights) {
            defaults |= Notification.DEFAULT_LIGHTS;
        }

        cBuilder.setDefaults(defaults);
    }



    /**
     * 有进度条的通知，可以设置为模糊进度或者精确进度
     */
    public void notify_progress(PendingIntent pendingIntent, int smallIcon,
            String ticker, String title, String content, boolean sound, boolean vibrate,
            boolean lights) {

        setCompatBuilder(pendingIntent, smallIcon, ticker, title, content, sound, vibrate, lights);
        send();
    }

    /**
     * 发送通知
     */
    public void send() {
        notification = cBuilder.build();
        // 发送该通知
        nm.notify(NOTIFICATION_ID, notification);
    }

    /**
     * 根据id清除通知
     */
    public void clear() {
        // 取消通知
        nm.cancelAll();
    }

    public void cancelById(){
        nm.cancel(NOTIFICATION_ID);
    }

    public void cancelById(int id){
        nm.cancel(id);
    }
}
