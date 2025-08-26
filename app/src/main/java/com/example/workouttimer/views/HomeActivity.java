package com.example.workouttimer.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.workouttimer.R;
import com.example.workouttimer.data.sharedpref.sessionmanger.SessionManager;

public class HomeActivity extends AppCompatActivity {

    TextView tvUserDetails;
    Button btnLogout;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeshared);

        tvUserDetails = findViewById(R.id.tvUserDetails);
        btnLogout = findViewById(R.id.btnLogout);

        session = new SessionManager(this);

        // If not logged in → go back to Login
        if (!session.isLoggedIn()) {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        }

        // Show user details
        String username = session.getUsername();
        String email = session.getEmail();

        tvUserDetails.setText("Welcome " + username + "\nEmail: " + email);

        // Logout
        btnLogout.setOnClickListener(v -> {
            session.logout();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        });
    }
}
