package com.example.workouttimer.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.workouttimer.data.local.models.NotificationModel;
import com.example.workouttimer.utils.NotificationHelper;


public class NotificationViewModel extends ViewModel {

    public void createChannel(Context context) {
        NotificationHelper.createNotificationChannel(context);
    }

    public void sendNotification(Context context, String title, String message) {
        NotificationModel model = new NotificationModel(title, message);
        NotificationHelper.showNotification(context, model);
    }
}
