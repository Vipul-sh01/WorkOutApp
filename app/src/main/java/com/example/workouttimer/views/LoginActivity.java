package com.example.workouttimer.views;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.workouttimer.R;
import com.example.workouttimer.data.sharedpref.sessionmanger.SessionManager;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etEmail;
    Button btnLogin;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        btnLogin = findViewById(R.id.btnLogin);

        session = new SessionManager(this);

        // If already logged in → move to Home
        if (session.isLoggedIn()) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String email = etEmail.getText().toString();

            if (!username.isEmpty() && !email.isEmpty()) {
                // Save session
                session.createLoginSession(username, email);

                // Move to Home
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Please enter details", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
