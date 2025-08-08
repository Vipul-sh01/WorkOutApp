package com.example.workouttimer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity{
    private TextView tvResult;
    Button btnRestart;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_result);

        tvResult = findViewById(R.id.tvResult);
        btnRestart = findViewById(R.id.btnRestart);
        tvResult.setText("Workout Complete");
        btnRestart.setOnClickListener(view -> {
            Intent intent = new Intent(ResultActivity.this, Welcome.class);
            startActivity(intent);
        });
    }
}