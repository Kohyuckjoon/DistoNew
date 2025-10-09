package com.terra.terradisto.distosdkapp.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.terra.terradisto.ProjectCreate;
import com.terra.terradisto.ProjectDao;

@Database(entities = {ProjectCreate.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract ProjectDao projectDao();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "project_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
