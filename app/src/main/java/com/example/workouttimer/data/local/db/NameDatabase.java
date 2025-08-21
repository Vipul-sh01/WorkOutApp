package com.example.workouttimer.data.local.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.workouttimer.data.local.doa.NameDao;
import com.example.workouttimer.data.local.doa.ImageDao;
import com.example.workouttimer.data.local.models.NameModel;
import com.example.workouttimer.data.local.models.ImageEntity;

@Database(entities = {NameModel.class, ImageEntity.class}, version = 2, exportSchema = false)
public abstract class NameDatabase extends RoomDatabase {

    private static volatile NameDatabase INSTANCE;

    // DAOs
    public abstract NameDao nameDao();

    // Migration: Add 'email' column to users (NameModel table)
    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE users ADD COLUMN email TEXT");
        }
    };

    // Singleton instance
    public static NameDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NameDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    NameDatabase.class, "name_database")
                            .addMigrations(MIGRATION_1_2) // ✅ preserve old data
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
