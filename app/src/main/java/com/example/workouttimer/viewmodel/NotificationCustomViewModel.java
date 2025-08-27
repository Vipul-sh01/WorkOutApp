package com.example.workouttimer.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.workouttimer.data.repository.NotificationRepository;


public class NotificationCustomViewModel extends AndroidViewModel {

    private final NotificationRepository repository;

    public NotificationCustomViewModel(@NonNull Application application) {
        super(application);
        repository = new NotificationRepository(application);
    }

    public void sendCustomNotification(String title, String message) {
        repository.sendCustomNotification(title, message);
    }
}
