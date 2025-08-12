package com.example.workouttimer;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class SpinsActivity extends AppCompatActivity {

    private LuckyWheelView luckyWheel;
    private boolean isSpinning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spins);

        luckyWheel = findViewById(R.id.luckyWheel);

        luckyWheel.setOnClickListener(v -> {
            if (!isSpinning) {
                startSpin();
            }
        });
    }

    private void startSpin() {
        isSpinning = true;
        Random random = new Random();

        float startRotation = luckyWheel.getRotation();
        float endRotation = startRotation + 1440 + random.nextInt(360); // 4 spins + random

        ObjectAnimator animator = ObjectAnimator.ofFloat(luckyWheel, "rotationAngle", startRotation, endRotation);
        animator.setDuration(3000);
        animator.setInterpolator(new DecelerateInterpolator());

        animator.addListener(new android.animation.Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(android.animation.Animator animation) { }

            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                isSpinning = false;
                String offer = luckyWheel.getWinningOffer();
                new AlertDialog.Builder(SpinsActivity.this)
                        .setTitle("🎉 You Won!")
                        .setMessage("Your reward: " + offer)
                        .setPositiveButton("OK", null)
                        .show();
            }

            @Override
            public void onAnimationCancel(android.animation.Animator animation) { }

            @Override
            public void onAnimationRepeat(android.animation.Animator animation) { }
        });

        animator.start();
    }
}
