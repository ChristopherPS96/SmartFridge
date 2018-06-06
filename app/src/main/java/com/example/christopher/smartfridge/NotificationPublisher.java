package com.example.christopher.smartfridge;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;
import java.util.Date;

public class NotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            NotificationChannel channel;
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                channel = new NotificationChannel("123", "Channel", NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }
            Notification notification = intent.getParcelableExtra(NOTIFICATION);
            int id = intent.getIntExtra(NOTIFICATION_ID, 0);
            notificationManager.notify(id, notification);
        } catch (NullPointerException e) {
            e.getStackTrace();
        }
    }

    public void scheduleNotification(BestandItem bestandItem, Context context) {
        OrmDataHelper helper = new OrmDataHelper(context);
        boolean isNotification = false;
        if(helper.getSettingItem() != null && helper.getSettingItem().size() >0){
            SettingsItem settingsItem = helper.getSettingItem().get(0);
            isNotification = settingsItem.isNotifications();
        }
        if(isNotification){
            Intent intent = new Intent(context, NotificationPublisher.class);
            intent.putExtra(NotificationPublisher.NOTIFICATION_ID, bestandItem.getId());
            intent.putExtra(NotificationPublisher.NOTIFICATION, getNotification(bestandItem.getScanItem().getName(), context));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, bestandItem.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            try {
                alarmManager.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 2000, pendingIntent);
            } catch (NullPointerException e) {
                e.getStackTrace();
            }
        }
    }

    public void deleteNotification(BestandItem bestandItem, Context context) {
        Intent intent = new Intent(context, NotificationPublisher.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, bestandItem.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        try {
            alarmManager.cancel(pendingIntent);
        } catch (NullPointerException e) {
            e.getStackTrace();
        }
    }

    public long setTimeInMillis(Calendar calendar) {
       return calendar.getTimeInMillis();
    }

    @TargetApi(16)
    public Notification getNotification(String content, Context context) {
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            NotificationCompat.Builder nbuilder = new NotificationCompat.Builder(context, "123");
            nbuilder.setContentTitle("Abgelaufen!");
            nbuilder.setSmallIcon(R.mipmap.fridge_icon_round);
            nbuilder.setContentText(content + " ist abgelaufen!");
            nbuilder.setSmallIcon(R.drawable.ic_launcher_foreground);
            return nbuilder.build();
        }
        else {
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle("Abgelaufen!");
            builder.setContentText(content + " ist abgelaufen!");
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
            return builder.build();
        }

    }
}
