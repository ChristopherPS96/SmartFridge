/*
 ** Erstellt von Christopher Schwandt, Anna Rochow, Jennifer Tönjes und Alina Pohl der SMIB
 */

package com.example.christopher.smartfridge;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Objects;

public class NotificationReceiver extends BroadcastReceiver {

   //erstellt Notifiaction und schrebt den Inhalt auf den Bildschirm des Handys
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            NotificationChannel channel;
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                channel = new NotificationChannel("123", "SmartFridge", NotificationManager.IMPORTANCE_DEFAULT);
                Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
            }
            Notification notification = intent.getParcelableExtra(NotificationPublisher.NOTIFICATION);
            int id = intent.getIntExtra(NotificationPublisher.NOTIFICATION_ID, 0);
            Objects.requireNonNull(notificationManager).notify(id, notification);
        } catch (NullPointerException e) {
            e.getStackTrace();
        }
    }
}
