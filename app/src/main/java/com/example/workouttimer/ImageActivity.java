package com.example.workouttimer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.workouttimer.adapter.ImageAdapter;
import com.example.workouttimer.data.local.models.ImageEntity;
import com.example.workouttimer.viewmodel.ImageViewModel;

public class ImageActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PICK_IMAGE = 100;
    private static final int PERMISSION_REQUEST_CODE = 101;

    private ImageAdapter adapter;
    private ImageViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localimage);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new ImageAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        Button btnPickImages = findViewById(R.id.btnPickImages);

        viewModel = new ViewModelProvider(this).get(ImageViewModel.class);
        viewModel.getAllImages().observe(this, imageEntities -> adapter.setImages(imageEntities));

        // Click on image → open SingleImageActivity
        adapter.setOnImageClickListener(imageEntity -> {
            Intent intent = new Intent(ImageActivity.this, SingleImageActivity.class);
            intent.putExtra("image_id", imageEntity.getId());
            startActivity(intent);
        });

        // Pick images button
        btnPickImages.setOnClickListener(v -> {
            if (checkAndRequestPermission()) {
                openDocumentPicker();
            }
        });
    }

    /**
     * Check runtime permission
     */
    private boolean checkAndRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE);
                return false;
            }
        } else {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openDocumentPicker(); // Open picker after permission granted
            } else {
                Toast.makeText(this, "Permission is required to pick images", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Open the Storage Access Framework picker
     */
    private void openDocumentPicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                // Multiple images
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri uri = data.getClipData().getItemAt(i).getUri();
                    saveUriWithPersistablePermission(uri);
                }
            } else if (data.getData() != null) {
                // Single image
                saveUriWithPersistablePermission(data.getData());
            }
        }
    }

    /**
     * Persist URI permission and save in Room
     */
    private void saveUriWithPersistablePermission(Uri uri) {
        try {
            getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            ImageEntity entity = new ImageEntity();
            entity.setUri(uri.toString());
            viewModel.insert(entity);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save image: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
