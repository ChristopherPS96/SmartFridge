/*
 ** Erstellt von Christopher Schwandt, Anna Rochow, Jennifer Tönjes und Alina Pohl der SMIB
 */

package com.example.christopher.smartfridge;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.Objects;

class NotificationPublisher {

    public static final String NOTIFICATION_ID = "notification-id";
    public static final String NOTIFICATION = "notification";

    //erstellt die notwendigen Notifications für vorgegebenen BestandItem
    public void scheduleNotification(BestandItem bestandItem, Context context) {
        OrmDataHelper helper = new OrmDataHelper(context);
        boolean isNotification = false;
        if(helper.getSettingItem() != null && helper.getSettingItem().size() >0){
            SettingsItem settingsItem = helper.getSettingItem().get(0);
            isNotification = settingsItem.isNotifications();
        }
        //Notifications wird nur bei Erlaubnis durchs Menü erstellt
        if(isNotification){
            Intent intent = new Intent(context, NotificationReceiver.class);
            intent.putExtra(NotificationPublisher.NOTIFICATION_ID, bestandItem.getId());
            intent.putExtra(NotificationPublisher.NOTIFICATION, getNotification(bestandItem.getScanItem().getName(), context));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, bestandItem.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            try {
                Objects.requireNonNull(alarmManager).set(AlarmManager.RTC, bestandItem.getAblaufDatum().getTimeInMillis(), pendingIntent);
            } catch (NullPointerException e) {
                e.getStackTrace();
            }
        }
    }

    //löscht die Notifications für das BestandItem
    public void deleteNotification(BestandItem bestandItem, Context context) {
        Intent intent = new Intent(context, NotificationPublisher.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, bestandItem.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        try {
            Objects.requireNonNull(alarmManager).cancel(pendingIntent);
        } catch (NullPointerException e) {
            e.getStackTrace();
        }
    }

    @TargetApi(16)
    //gibt eine funktionierende Notifications mit gewünschtem Text zurück
    private Notification getNotification(String content, Context context) {
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
