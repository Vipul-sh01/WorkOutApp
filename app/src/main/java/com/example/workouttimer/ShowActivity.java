package com.example.workouttimer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowActivity extends AppCompatActivity {

    private Button btnShowGreet;
    private TextView tvName;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round);

        tvName = findViewById(R.id.tvName);
        btnShowGreet = findViewById(R.id.btnShowGreet);
        btnShowGreet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvName.setText("hello vipul sharma");
            }
        });
    }
}