package com.example.christopher.smartfridge;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.util.Calendar;

public class NotificationPublisher {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    public void scheduleNotification(BestandItem bestandItem, Context context) {
        OrmDataHelper helper = new OrmDataHelper(context);
        boolean isNotification = false;
        if(helper.getSettingItem() != null && helper.getSettingItem().size() >0){
            SettingsItem settingsItem = helper.getSettingItem().get(0);
            isNotification = settingsItem.isNotifications();
        }
        if(isNotification){
            Intent intent = new Intent(context, NotificationReceiver.class);
            intent.putExtra(NotificationPublisher.NOTIFICATION_ID, bestandItem.getId());
            intent.putExtra(NotificationPublisher.NOTIFICATION, getNotification(bestandItem.getScanItem().getName(), context));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, bestandItem.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            try {
                alarmManager.set(AlarmManager.RTC, bestandItem.getAblaufDatum().getTimeInMillis(), pendingIntent);
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
            nbuilder.setSmallIcon(R.mipmap.fridge_icon);
            nbuilder.setContentText(content + " ist abgelaufen!");
            return nbuilder.build();
        }
        else {
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle("Abgelaufen!");
            builder.setContentText(content + " ist abgelaufen!");
            builder.setSmallIcon(R.mipmap.fridge_icon);
            return builder.build();
        }

    }
}
