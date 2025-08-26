package com.example.workouttimer;

import com.google.firebase.messaging.FirebaseMessaging;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationActivity extends AppCompatActivity {
    TextView tokenText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

//        tokenText = findViewById(R.id.tokenText);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("FCM", "Fetching FCM token failed", task.getException());
                        tokenText.setText("Failed to fetch token");
                        return;
                    }
                    String token = task.getResult();
                    Log.d("FCM", "Token: " + token);
                    tokenText.setText(token); // ✅ show on screen
                });
    }
}
