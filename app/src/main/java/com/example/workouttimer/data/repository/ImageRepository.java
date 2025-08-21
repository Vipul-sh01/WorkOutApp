package com.example.workouttimer.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.workouttimer.data.local.db.AppDatabase;
import com.example.workouttimer.data.local.doa.ImageDao;
import com.example.workouttimer.data.local.models.ImageEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageRepository {

    private final ImageDao imageDao;
    private final LiveData<List<ImageEntity>> allImages;
    private final ExecutorService executorService;

    public ImageRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        imageDao = db.imageDao();
        allImages = imageDao.getAllImages();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<ImageEntity>> getAllImages() {
        return allImages;
    }

    public LiveData<ImageEntity> getImageById(int id) {
        return imageDao.getImageById(id);
    }

    public void insert(ImageEntity image) {
        executorService.execute(() -> imageDao.insertImage(image));
    }
}
