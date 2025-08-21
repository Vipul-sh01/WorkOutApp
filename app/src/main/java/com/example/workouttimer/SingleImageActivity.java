package com.example.workouttimer;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.workouttimer.data.local.models.ImageEntity;
import com.example.workouttimer.viewmodel.ImageViewModel;

import java.util.List;

public class SingleImageActivity extends AppCompatActivity {

    private ImageView imageView;
    private ImageViewModel imageViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_image);

        imageView = findViewById(R.id.imageViewById);

        // Get image id from intent
        int imageId = getIntent().getIntExtra("image_id", -1);

        if (imageId == -1) {
            Toast.makeText(this, "No image id provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Setup ViewModel
        imageViewModel = new ViewModelProvider(this).get(ImageViewModel.class);

        // Observe the list of images
        imageViewModel.getAllImages().observe(this, images -> {
            loadImageById(images, imageId);
        });
    }

    private void loadImageById(List<ImageEntity> images, int imageId) {
        for (ImageEntity entity : images) {
            if (entity.getId() == imageId) {
                try {
                    Log.d("SingleImageActivity", "Loading image URL: " + entity.getUri());

                    Glide.with(this)
                            .load(entity.getUri())
                            .into(imageView);

                } catch (Exception e) {
                    Toast.makeText(this, "Error loading image: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("Error", "Error loading image " + e.getMessage());
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
