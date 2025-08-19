package com.example.workouttimer.data.local.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.workouttimer.data.local.doa.NameDao;
import com.example.workouttimer.data.local.models.NameModel;

@Database(entities = {NameModel.class}, version = 1, exportSchema = false)
public abstract class NameDatabase extends RoomDatabase {

    private static volatile NameDatabase INSTANCE;

    // Abstract method for DAO
    public abstract NameDao nameDao();

    // Singleton instance
    public static NameDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NameDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    NameDatabase.class, "name_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
