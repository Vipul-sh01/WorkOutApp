package com.example.workouttimer.reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.workouttimer.data.local.models.NotificationModel;
import com.example.workouttimer.utils.NotificationHelper;


public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String message = intent.getStringExtra("message");

        NotificationModel model = new NotificationModel(title, message);
        NotificationHelper.showNotification(context, model);
    }
}
