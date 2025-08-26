package com.example.workouttimer;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.ComponentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.workouttimer.viewmodel.AuthViewModel;


public class AuthActivity extends ComponentActivity {

    private AuthViewModel viewModel;
    private EditText edtPhone, edtOtp;
    private Button btnSendOtp, btnVerify, crashButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        edtPhone = findViewById(R.id.phoneNumber);
        edtOtp = findViewById(R.id.otpCode);
        btnSendOtp = findViewById(R.id.sendOtpBtn);
        btnVerify = findViewById(R.id.verifyOtpBtn);
        crashButton = findViewById(R.id.crashButton);


        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        btnSendOtp.setOnClickListener(v -> {
            String phone = edtPhone.getText().toString().trim();
            if (!phone.isEmpty()) {
                String fullPhone = "+91" + phone; // prepend country code
                viewModel.sendOtp(fullPhone, this);
            } else {
                Toast.makeText(this, "Enter phone number", Toast.LENGTH_SHORT).show();
            }
        });

        btnVerify.setOnClickListener(v -> {
            String otp = edtOtp.getText().toString().trim();
            if (!otp.isEmpty()) {
                viewModel.verifyOtp(otp);
            }
        });

        observeViewModel();


        crashButton.setOnClickListener(view -> {
            throw new RuntimeException("Test Crash from Vipul");
        });
    }

    private void observeViewModel() {
        viewModel.getOtpSent().observe(this, msg ->
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show());

        viewModel.getLoginSuccess().observe(this, msg ->
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show());

        viewModel.getError().observe(this, err -> {
            Toast.makeText(this, "Error: " + err, Toast.LENGTH_LONG).show();
            Log.d("Error", "Error: " + err);
        });

    }
}
