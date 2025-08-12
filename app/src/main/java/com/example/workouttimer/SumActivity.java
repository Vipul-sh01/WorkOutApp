package com.example.workouttimer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SumActivity extends AppCompatActivity {
    private EditText etNum1, etNum2;
    private Button btnSum;
    private TextView tvResults;

    private int sum(int a, int b){
        return a+b;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum);

        etNum1 = findViewById(R.id.etNum1);
        etNum2 = findViewById(R.id.etNum2);
        btnSum = findViewById(R.id.btnSum);
        tvResults = findViewById(R.id.tvResults);

        btnSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num1 = Integer.parseInt(etNum1.getText().toString());
                int num2 = Integer.parseInt(etNum2.getText().toString());
                int result = sum(num1,num2);
                tvResults.setText("Sum: " + result);
            }
        });
    }
}