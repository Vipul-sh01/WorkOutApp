package com.example.workouttimer.data.local.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.workouttimer.data.local.doa.ImageDao;
import com.example.workouttimer.data.local.models.ImageEntity;

@Database(entities = {ImageEntity.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ImageDao imageDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "image-db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
