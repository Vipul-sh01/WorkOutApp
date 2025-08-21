package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MusicActivity extends AppCompatActivity {

    Button startBtn, pauseBtn, stopBtn;
    SeekBar musicSeekBar;
    TextView tvCurrentTime, tvTotalTime;

    MediaPlayer mediaPlayer;
    Handler handler = new Handler();
    Runnable updateSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        startBtn = findViewById(R.id.startBtn);
        pauseBtn = findViewById(R.id.pauseBtn);
        stopBtn = findViewById(R.id.stopBtn);
        musicSeekBar = findViewById(R.id.musicSeekBar);
        tvCurrentTime = findViewById(R.id.tvCurrentTime);
        tvTotalTime = findViewById(R.id.tvTotalTime);

        // Load music from raw folder
        mediaPlayer = MediaPlayer.create(this, R.raw.sample_music);

        // Set max SeekBar
        musicSeekBar.setMax(mediaPlayer.getDuration());

        // Show total duration
        tvTotalTime.setText(formatTime(mediaPlayer.getDuration()));

        // Update SeekBar every second
        updateSeekBar = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    int currentPos = mediaPlayer.getCurrentPosition();
                    musicSeekBar.setProgress(currentPos);
                    tvCurrentTime.setText(formatTime(currentPos));
                    handler.postDelayed(this, 1000);
                }
            }
        };

        // Start Button
        startBtn.setOnClickListener(v -> {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                handler.post(updateSeekBar);
            }
        });

        // Pause Button
        pauseBtn.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
        });

        // Stop Button
        stopBtn.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                handler.removeCallbacks(updateSeekBar);
                mediaPlayer = MediaPlayer.create(this, R.raw.sample_music); // Reset
                musicSeekBar.setProgress(0);
                tvCurrentTime.setText("0:00");
            }
        });

        // SeekBar change listener
        musicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    private String formatTime(int millis) {
        return String.format("%d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) % 60);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        handler.removeCallbacks(updateSeekBar);
    }
}
