package com.example.workouttimer;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class SpinActivity extends AppCompatActivity {

    private ImageView imgOffer;
    private boolean isSpinning = false;

    private void showOfferDialog() {
        new AlertDialog.Builder(this)
                .setTitle("🎉 You Won!")
                .setMessage("You got 50% OFF on your next order!")
                .setPositiveButton("Awesome!", null)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin);

        imgOffer = findViewById(R.id.imgOffer);

        imgOffer.setOnClickListener(v -> {
            if (!isSpinning) {
                startSpin();
            }
        });
    }

    private void startSpin() {
        isSpinning = true;
        Random random = new Random();

        // 4 full spins + random stop (0–359 degrees)
        int spinDegrees = 1440 + random.nextInt(360);

        ObjectAnimator spinAnim = ObjectAnimator.ofFloat(imgOffer, "rotation", 0f, spinDegrees);
        spinAnim.setDuration(3000); // 3 seconds
        spinAnim.setInterpolator(new DecelerateInterpolator());

        spinAnim.addListener(new android.animation.Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(android.animation.Animator animator) { }

            @Override
            public void onAnimationEnd(android.animation.Animator animator) {
                isSpinning = false;
                showOfferDialog();
            }

            @Override
            public void onAnimationCancel(android.animation.Animator animator) { }

            @Override
            public void onAnimationRepeat(android.animation.Animator animator) { }
        });

        spinAnim.start();
    }

}
