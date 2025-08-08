package com.example.workouttimer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Welcome extends AppCompatActivity {

    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(v ->{
            Intent intent = new Intent(Welcome.this, TimerWorkout.class);
            startActivity(intent);
        });
    }
}
