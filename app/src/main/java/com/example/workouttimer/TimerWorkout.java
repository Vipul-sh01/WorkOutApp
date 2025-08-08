package com.example.workouttimer;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TimerWorkout extends AppCompatActivity {
    private TextView tvText;
    private Button btnPause, btnReset;
    private Handler handler;
    private int seconds = 30;
    private boolean isRunning = true;

    private Runnable runnable = new Runnable(){
        @Override
        public void run() {
            if(isRunning && seconds>0){
                seconds--;
                tvText.setText("Time Left: " + seconds + "s");
                handler.postDelayed(this,1000);
            }
            else if (seconds == 0){
                Intent intent = new Intent(TimerWorkout.this, ResultActivity.class);
                startActivity(intent);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        tvText = findViewById(R.id.tvText);
        btnPause = findViewById(R.id.btnPause);
        btnReset = findViewById(R.id.btnReset);

        handler = new Handler();
        handler.postDelayed(runnable,1000);

        btnPause.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                isRunning = !isRunning;
                btnPause.setText(isRunning? "Pause" : "Resume");
                if(isRunning)handler.postDelayed(runnable, 1000);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seconds = 30;
                tvText.setText("Time Left: 30s");
                isRunning = true;
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 1000);
            }
        });

    }
    @Override
    protected void onPause(){
        super.onPause();
        isRunning = false;
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(seconds>0){
            isRunning = true;
            handler.postDelayed(runnable, 1000);
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}