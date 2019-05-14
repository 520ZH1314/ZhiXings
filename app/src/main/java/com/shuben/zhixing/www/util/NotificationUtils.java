package com.shuben.zhixing.www.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.shuben.zhixing.www.BaseApplication;
import com.shuben.zhixing.www.R;
import com.base.zhixing.www.common.P;

/**
 * Created by LaoZhao on 2017/11/19.
 */

public class NotificationUtils extends ContextWrapper {

    private NotificationManager manager;
    public static final String channel_id = "channel";
    public static final String name = "智行力渠道";
    private Context context;




    public NotificationUtils(Context context) {
        super(context);
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel() {

        NotificationChannel channel = new NotificationChannel(channel_id, name, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getChannelNotification(String title, String content, Intent intent, int res, int index) {
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), index, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        playSound(context);
        return new Notification.Builder(getApplicationContext(), channel_id)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(res)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setOngoing(false)
                .setDefaults( Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .setVisibility(Notification.VISIBILITY_PUBLIC);
//                .setSound(Uri.parse(sound));

    }
     private boolean allowMusic = true;
    private   Ringtone mRingtone;
    public synchronized void playSound(Context context) {
        if (!allowMusic) {
            return;
        }
        if (mRingtone == null) {
            String uri = "android.resource://" + context.getPackageName() + "/" + R.raw.noti;
            Uri no = Uri.parse(uri);
            mRingtone = RingtoneManager.getRingtone(context.getApplicationContext(), no);
        }
        if (!mRingtone.isPlaying()) {

            mRingtone.play();
        }
    }
    String sound = "android.resource://" + BaseApplication.application.getPackageName() + "/" + R.raw.noti;
    public NotificationCompat.Builder getNotification_25(String title, String content, Intent intent, int res, int index) {
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), index, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        playSound(context);
        return new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(res)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setOngoing(false)
                .setChannelId(channel_id)
                .setDefaults( Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .setVisibility(Notification.VISIBILITY_PUBLIC);
//                .setSound(Uri.parse(sound));

    }

    int NOTIFY = 1;

    public void sendNotification(String title, String content, Intent intent, int res, int index) {
        NOTIFY++;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            createNotificationChannel();
            Notification.Builder builder = getChannelNotification
                    (title, content, intent, res, NOTIFY);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//SDK版本大于等于21才有悬挂式通知栏
                builder.setVisibility(Notification.VISIBILITY_PUBLIC);
            }
            Notification notification = builder.build();
            getManager().notify(NOTIFY, notification);


        } else {
            NotificationCompat.Builder builder = getNotification_25(title, content, intent, res, NOTIFY);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//SDK版本大于等于21才有悬挂式通知栏
                builder.setVisibility(Notification.VISIBILITY_PUBLIC);
            }
            Notification notification = builder.build();
            getManager().notify(NOTIFY, notification);




        }
        P.c("发送的" + NOTIFY);

    }


}
