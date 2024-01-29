package com.example.tavi.data.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.tavi.data.database.dao.PostDao;
import com.example.tavi.data.database.dao.CommentDao;
import com.example.tavi.data.database.dao.ReactionDao;
import com.example.tavi.data.models.Post;
import com.example.tavi.data.models.Comment;
import com.example.tavi.data.models.Reaction;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = { Post.class, Comment.class, Reaction.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "tavi_database";
    private static AppDatabase instance;

    public abstract PostDao postDao();

    public abstract CommentDao commentDao();

    public abstract ReactionDao reactionDao();

    public static final ExecutorService databaseWriteExecutor = Executors.newSingleThreadExecutor();

    public static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                    .build();
        }
        return instance;
    }
}
