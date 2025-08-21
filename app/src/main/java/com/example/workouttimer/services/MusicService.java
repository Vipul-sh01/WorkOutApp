package com.example.workouttimer.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.example.workouttimer.R;

public class MusicService extends Service {

    private static final String TAG = "MusicService";

    private MediaPlayer mediaPlayer;
    private final IBinder binder = new MusicBinder();
    private Handler handler = new Handler();
    private Runnable progressRunnable;
    private OnProgressUpdateListener progressListener;

    public interface OnProgressUpdateListener {
        void onProgress(int currentPosition, int duration);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Service Created");

        mediaPlayer = MediaPlayer.create(this, R.raw.sample_music); // put mp3 in res/raw
        mediaPlayer.setOnPreparedListener(mp -> Log.d(TAG, "MediaPlayer Prepared"));
        mediaPlayer.setOnCompletionListener(mp -> Log.d(TAG, "Music Completed"));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service Started");
        return START_STICKY;
    }

    public void startMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            Log.d(TAG, "Music Started 🎵");
            startProgressUpdates();
        }
    }

    public void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            Log.d(TAG, "Music Paused ⏸️");
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.prepareAsync(); // prepare for next start
            Log.d(TAG, "Music Stopped ⏹️");
            stopProgressUpdates();
        }
    }

    private void startProgressUpdates() {
        stopProgressUpdates(); // avoid duplicates
        progressRunnable = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null && mediaPlayer.isPlaying() && progressListener != null) {
                    progressListener.onProgress(mediaPlayer.getCurrentPosition(), mediaPlayer.getDuration());
                }
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(progressRunnable);
    }

    private void stopProgressUpdates() {
        if (progressRunnable != null) {
            handler.removeCallbacks(progressRunnable);
        }
    }

    public void setOnProgressUpdateListener(OnProgressUpdateListener listener) {
        this.progressListener = listener;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopProgressUpdates();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        Log.d(TAG, "Service Destroyed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }
}
