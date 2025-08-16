package com.example.workouttimer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class LuckyWheelView extends View {

    private final String[] offers = {"10% OFF", "20% OFF", "50% OFF", "Free Meal", "Buy 1 Get 1", "₹100 OFF"};

    private final int[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.CYAN};

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF wheelBounds;
    private float anglePerSlice;
    private float currentRotation = 0f;

    public LuckyWheelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(36f);
        paint.setFakeBoldText(true);
        anglePerSlice = 360f / offers.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int size = Math.min(getWidth(), getHeight());
        float padding = 20f;
        wheelBounds = new RectF(padding, padding, size - padding, size - padding);

        canvas.save();
        canvas.rotate(currentRotation, size / 2f, size / 2f);

        // Outer border
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8f);
        paint.setColor(Color.BLACK);
        canvas.drawOval(wheelBounds, paint);

        // Draw slices with curved text
        paint.setStyle(Paint.Style.FILL);

        for (int i = 0; i < offers.length; i++) {
            // Slice background
            paint.setColor(colors[i % colors.length]);
            canvas.drawArc(wheelBounds, i * anglePerSlice, anglePerSlice, true, paint);

            // Draw curved text
            drawCurvedText(canvas, offers[i], i * anglePerSlice, size, padding);
        }

        // Center circle
        paint.setColor(Color.WHITE);
        canvas.drawCircle(size / 2f, size / 2f, 90f, paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(28f);
        canvas.drawText("SPIN", size / 2f, size / 2f + 10f, paint);

        canvas.restore();
    }

    private void drawCurvedText(Canvas canvas, String text, float startAngle, int size, float padding) {
        paint.setColor(Color.WHITE);
        paint.setTextSize(60f);
        paint.setFakeBoldText(true);

        // Path for the arc text
        Path path = new Path();
        RectF textArcBounds = new RectF(padding + 70, padding + 70, size - padding - 70, size - padding - 70);
        path.addArc(textArcBounds, startAngle + 5, anglePerSlice - 10);
        canvas.drawTextOnPath(text, path, 0, 0, paint);
    }

    public void setRotationAngle(float rotation) {
        this.currentRotation = rotation;
        invalidate();
    }

    public String getWinningOffer() {
        float normalizedAngle = (360 - (currentRotation % 360)) % 360;
        int winningIndex = (int) (normalizedAngle / anglePerSlice);
        return offers[winningIndex];
    }
}
