package com.example.workouttimer;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;



public class AnimationActivity extends AppCompatActivity {
    private Button btnAnimate;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        btnAnimate = findViewById(R.id.btnAnimate);

        // Bounce in when activity starts
        Animation bounceIn = AnimationUtils.loadAnimation(this, R.anim.anim_bounce_in);
        btnAnimate.startAnimation(bounceIn);

        btnAnimate.setOnClickListener(v -> {
            Animation fadeSlideOut = AnimationUtils.loadAnimation(this, R.anim.anim_fade_slide_out);
            btnAnimate.startAnimation(fadeSlideOut);

            fadeSlideOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) { }

                @Override
                public void onAnimationEnd(Animation animation) {
                    btnAnimate.setVisibility(v.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) { }
            });
        });
    }
}