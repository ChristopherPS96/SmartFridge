package com.example.christopher.smartfridge;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            NotificationChannel channel;
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                channel = new NotificationChannel("123", "Channel", NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }
            Notification notification = intent.getParcelableExtra(NotificationPublisher.NOTIFICATION);
            int id = intent.getIntExtra(NotificationPublisher.NOTIFICATION_ID, 0);
            notificationManager.notify(id, notification);
        } catch (NullPointerException e) {
            e.getStackTrace();
        }
    }
}
