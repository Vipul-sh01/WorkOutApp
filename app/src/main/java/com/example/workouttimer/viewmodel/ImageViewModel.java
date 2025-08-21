package com.example.workouttimer.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.workouttimer.data.local.models.ImageEntity;
import com.example.workouttimer.data.repository.ImageRepository;

import java.util.List;

public class ImageViewModel extends AndroidViewModel {

    private final ImageRepository repository;
    private final LiveData<List<ImageEntity>> allImages;

    public ImageViewModel(@NonNull Application application) {
        super(application);
        repository = new ImageRepository(application);
        allImages = repository.getAllImages();
    }

    public LiveData<List<ImageEntity>> getAllImages() {
        return allImages;
    }

    public LiveData<ImageEntity> getImageById(int id) {
        return repository.getImageById(id);
    }

    public void insert(ImageEntity image) {
        repository.insert(image);
    }

    // 🔹 New: Save multiple images
    public void saveImages(List<Uri> uris) {
        for (Uri uri : uris) {
            ImageEntity entity = new ImageEntity();
            entity.setUri(uri.toString()); // ✅ correct way
            // Save URI as String
            repository.insert(entity);
        }
    }
}
