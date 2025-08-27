package com.example.workouttimer.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.workouttimer.R;
import com.example.workouttimer.viewmodel.NotificationViewModel;


public class NotificationActivity extends AppCompatActivity {

    private NotificationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_builder);

        viewModel = new ViewModelProvider(this).get(NotificationViewModel.class);

        // Create channel
        viewModel.createChannel(this);

        // Ask permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                    != getPackageManager().PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        Button btnShow = findViewById(R.id.btnShowNotification);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.sendNotification(NotificationActivity.this,
                        "Important Notification",
                        "I’ll walk you through step by step and then provide a complete working code.");
            }
        });
    }
}
