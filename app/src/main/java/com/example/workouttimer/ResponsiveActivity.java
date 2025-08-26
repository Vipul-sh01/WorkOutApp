package com.example.workouttimer;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResponsiveActivity extends AppCompatActivity {

    private TextView tvTitle, tvDesc;
    private Button btnOne, btnTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responsive);

        tvTitle = findViewById(R.id.tvTitle);
        tvDesc = findViewById(R.id.tvDesc);
        btnOne = findViewById(R.id.btnOne);
        btnTwo = findViewById(R.id.btnTwo);

        btnOne.setOnClickListener(v -> tvDesc.setText("Action 1 Clicked"));
        btnTwo.setOnClickListener(v -> tvDesc.setText("Action 2 Clicked"));
    }
}
