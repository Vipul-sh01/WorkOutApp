package com.example.workouttimer;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;

import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;

public class CaptureAct extends CaptureActivity {
    private DecoratedBarcodeView barcodeView;
    private View laser;
    private View frameBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        barcodeView = findViewById(R.id.zxing_barcode_scanner);
        laser = findViewById(R.id.laser);
        frameBox = findViewById(R.id.frameBox);

        frameBox.getViewTreeObserver().addOnGlobalLayoutListener(() -> startLaserAnimation());

        barcodeView.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                if (result == null || result.getText() == null) return;

                getIntent().putExtra("SCAN_RESULT", result.getText());
                setResult(RESULT_OK, getIntent());
                finish();
            }
        });
    }

    private void startLaserAnimation() {
        if (frameBox == null || laser == null) return;

        int topPadding = dp(12);
        int bottomPadding = dp(12);
        int availableHeight = frameBox.getHeight() - laser.getHeight() - topPadding - bottomPadding;

        TranslateAnimation anim = new TranslateAnimation(
                0, 0,
                topPadding, availableHeight
        );
        anim.setDuration(1500);
        anim.setRepeatCount(TranslateAnimation.INFINITE);
        anim.setRepeatMode(TranslateAnimation.REVERSE);
        laser.startAnimation(anim);
    }


    private int dp(float v) {
        return Math.round(v * getResources().getDisplayMetrics().density);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (barcodeView != null) barcodeView.resume();
    }

    @Override
    protected void onPause() {
        if (barcodeView != null) barcodeView.pause();
        super.onPause();
    }
}
