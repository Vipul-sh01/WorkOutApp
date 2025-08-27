package com.example.workouttimer.views;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.workouttimer.R;
import com.example.workouttimer.viewmodel.NotificationCustomViewModel;

public class NotificationCustomActivity extends AppCompatActivity {

    private NotificationCustomViewModel notificationCustomViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_notification);

        notificationCustomViewModel = new ViewModelProvider(this).get(NotificationCustomViewModel.class);

        // Automatically trigger notification after 5 seconds
        new Handler().postDelayed(() -> {
            notificationCustomViewModel.sendCustomNotification("Special Offer 🎉", "Flat 50% discount!");
        }, 5000);

        // Handle Actions
        String action = getIntent().getStringExtra("action");
        if (action != null) {
            switch (action) {
                case "ACCEPT":
                    break;
                case "DECLINE":
                    break;
            }
        }
    }
}
