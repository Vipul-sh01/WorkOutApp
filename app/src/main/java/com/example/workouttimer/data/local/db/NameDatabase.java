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

@Database(entities = {NameModel.class, ImageEntity.class}, version = 3, exportSchema = false)
public abstract class NameDatabase extends RoomDatabase {

    private static volatile NameDatabase INSTANCE;

    // DAOs
    public abstract NameDao nameDao();

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE users ADD COLUMN age NUMBER");
        }
    };

    // Singleton instance
    public static NameDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NameDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    NameDatabase.class, "name_database")
                            .addMigrations(MIGRATION_2_3)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
