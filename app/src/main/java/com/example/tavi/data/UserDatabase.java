package com.example.tavi.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    private static UserDatabase userInstance;
    static final ExecutorService databaseWriteExecutor = Executors.newSingleThreadExecutor();


    public abstract UserDao userDao();
    static UserDatabase getDatabase(final Context context) {
        if (userInstance == null)
            userInstance = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, "user_database")
                    .build();
        return userInstance;
    }
}
