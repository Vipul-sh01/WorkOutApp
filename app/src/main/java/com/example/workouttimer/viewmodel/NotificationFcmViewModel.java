package com.example.workouttimer.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.messaging.FirebaseMessaging;

public class NotificationFcmViewModel extends ViewModel {
    private final MutableLiveData<String> tokenLiveData = new MutableLiveData<>();

    public LiveData<String> getToken() {
        return tokenLiveData;
    }

    public void fetchFcmToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                tokenLiveData.setValue(task.getResult());
            } else {
                tokenLiveData.setValue("Failed to fetch token");
            }
        });
    }
}
