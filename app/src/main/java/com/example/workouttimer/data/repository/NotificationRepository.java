package com.example.workouttimer.data.repository;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.workouttimer.R;
import com.example.workouttimer.TimerWorkout;
import com.example.workouttimer.views.NotificationCustomActivity;

public class NotificationRepository {

    private static final String CHANNEL_ID = "custom_channel";
    private static final int NOTIFICATION_ID = 1002;
    private final Context context;

    public NotificationRepository(Context context) {
        this.context = context.getApplicationContext();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Custom Notification Channel";
            String description = "Channel for Custom Notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager =
                    context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void sendCustomNotification(String title, String message) {
        // Accept Action
        Intent acceptIntent = new Intent(context, TimerWorkout.class);
        acceptIntent.putExtra("action", "ACCEPT");
        PendingIntent acceptPendingIntent = PendingIntent.getActivity(
                context, 0, acceptIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Decline Action
        Intent declineIntent = new Intent(context, NotificationCustomActivity.class);
        declineIntent.putExtra("action", "DECLINE");
        PendingIntent declinePendingIntent = PendingIntent.getActivity(
                context, 1, declineIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Build Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_my_logo)
                .setContentTitle(title)
                .setContentText(message)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_my_logo))
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_my_logo))
                        .bigLargeIcon((Bitmap) null))
                .addAction(R.drawable.ic_my_logo, "Accept", acceptPendingIntent)
                .addAction(R.drawable.ic_my_logo, "Decline", declinePendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
