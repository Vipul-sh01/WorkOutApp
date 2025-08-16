package com.example.workouttimer;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class QrGenActivity extends AppCompatActivity {

    private EditText etInput;
    private Button btnGenerate, btnScan;
    private ImageView ivQRCode;
    private TextView tvResult;

    // Request camera permission
    private final ActivityResultLauncher<String> requestCameraPermission =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
                if (granted) {
                    startScan();
                } else {
                    Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
                }
            });

    // ZXing scan result handler
    private final ActivityResultLauncher<ScanOptions> scanLauncher =
            registerForActivityResult(new ScanContract(), result -> {
                if (result.getContents() != null) {
                    String contents = result.getContents();
                    if (tvResult != null) {
                        tvResult.setText("Result: " + contents);
                    }

                    // Open in browser if it's a URL
                    if (contents.startsWith("http://") || contents.startsWith("https://")) {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(contents)));
                        } catch (Exception ignored) {
                            // If no browser installed
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscreen);

        etInput = findViewById(R.id.etInput);
        btnGenerate = findViewById(R.id.btnGenerate);
        btnScan = findViewById(R.id.btnScan);
        ivQRCode = findViewById(R.id.ivQRCode);
        tvResult = findViewById(R.id.tvResult);

        btnGenerate.setOnClickListener(v -> generateQR());
        btnScan.setOnClickListener(v -> ensureCameraPermissionThenScan());
    }

    private void generateQR() {
        String text = etInput.getText().toString().trim();
        if (text.isEmpty()) {
            Toast.makeText(this, "Enter some text to generate", Toast.LENGTH_SHORT).show();
            return;
        }

        int size = 800;
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, size, size);
            Bitmap bmp = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565);

            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }

            ivQRCode.setImageBitmap(bmp);
        } catch (WriterException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to generate QR", Toast.LENGTH_SHORT).show();
        }
    }

    private void ensureCameraPermissionThenScan() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            startScan();
        } else {
            requestCameraPermission.launch(Manifest.permission.CAMERA);
        }
    }

    private void startScan() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Point camera at a QR code");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        scanLauncher.launch(options);
    }
}
