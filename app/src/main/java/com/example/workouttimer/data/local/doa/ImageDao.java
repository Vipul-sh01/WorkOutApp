package com.example.workouttimer.data.local.doa;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.workouttimer.data.local.models.ImageEntity;

import java.util.List;

@Dao
public interface ImageDao {

    // Insert new image → older images remain in DB
    @Insert
    void insertImage(ImageEntity image);

    // Get all saved images
    @Query("SELECT * FROM images")
    LiveData<List<ImageEntity>> getAllImages();

    @Query("SELECT * FROM images WHERE id = :id LIMIT 1")
    LiveData<ImageEntity> getImageById(int id);
}
