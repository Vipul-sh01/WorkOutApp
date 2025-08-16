package com.example.workouttimer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_GALLERY = 2;
    private static final int PERMISSION_REQUEST = 100;

    private Button btnSelectImage;
    private ImageView imageView;
    private Uri photoURI;
    private boolean userSelectedCamera = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        btnSelectImage = findViewById(R.id.btnSelectImage);
        imageView = findViewById(R.id.imageView);

        btnSelectImage.setOnClickListener(v -> showImagePickerDialog());
    }

    private void showImagePickerDialog() {
        String[] options = {"Camera", "Gallery"};

        new AlertDialog.Builder(this)
                .setTitle("Select Image From")
                .setItems(options, (dialog, which) -> {
                    userSelectedCamera = (which == 0);
                    if (checkPermissions()) {
                        if (userSelectedCamera) {
                            openCamera();
                        } else {
                            openGallery();
                        }
                    }
                })
                .show();
    }

    private boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ requires CAMERA + READ_MEDIA_IMAGES
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_MEDIA_IMAGES},
                        PERMISSION_REQUEST);
                return false;
            }
        } else {
            // Android 6 - 12 requires CAMERA + READ_EXTERNAL_STORAGE
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST);
                return false;
            }
        }
        return true;
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            File photoFile = createImageFile();
            if (photoFile != null) {
                String authority = getApplicationContext().getPackageName() + ".provider"; // ✅ matches manifest
                photoURI = FileProvider.getUriForFile(this, authority, photoFile);

                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                startActivityForResult(cameraIntent, REQUEST_CAMERA);
            } else {
                Toast.makeText(this, "Failed to create image file", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException ex) {
            Toast.makeText(this, "Error creating file: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                imageView.setImageURI(photoURI);
            } else if (requestCode == REQUEST_GALLERY && data != null) {
                Uri selectedImage = data.getData();
                imageView.setImageURI(selectedImage);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST) {
            boolean granted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                    break;
                }
            }
            if (granted) {
                if (userSelectedCamera) {
                    openCamera();
                } else {
                    openGallery();
                }
            } else {
                Toast.makeText(this, "Permissions are required to continue", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
