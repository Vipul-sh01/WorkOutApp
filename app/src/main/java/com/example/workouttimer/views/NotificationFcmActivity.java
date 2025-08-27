package com.example.workouttimer.views;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.workouttimer.R;
import com.example.workouttimer.utils.NotificationHelperFcm;
import com.example.workouttimer.viewmodel.NotificationFcmViewModel;

public class NotificationFcmActivity extends AppCompatActivity {
    private NotificationFcmViewModel viewModel;
    private NotificationHelperFcm notificationHelper;

    // Request launcher for notification permission
    private ActivityResultLauncher<String> requestPermissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificationfcm);

        notificationHelper = new NotificationHelperFcm(this);
        viewModel = new ViewModelProvider(this).get(NotificationFcmViewModel.class);

        TextView txtToken = findViewById(R.id.txtToken);
        Button btnSimple = findViewById(R.id.btnSimple);
        Button btnCustom = findViewById(R.id.btnCustom);

        // Permission request setup
        requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (!isGranted) {
                        txtToken.setText("Notifications permission denied!");
                    }
                });

        // Ask for permission if needed
        askNotificationPermission();

        // Observe token
        viewModel.getToken().observe(this, token -> {
            txtToken.setText("FCM Token:\n" + token);
        });

        // Fetch FCM token
        viewModel.fetchFcmToken();

        // Local demo notifications
        btnSimple.setOnClickListener(v ->
                notificationHelper.showSimpleNotification("Hello", "This is a simple local notification"));

        btnCustom.setOnClickListener(v ->
                notificationHelper.showCustomNotification("Vipul Sharma", "Message from Vipul Sharma"));
    }

    private void askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }
}
