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
                    .addCallback(roomDatabaseCallback)
                    .build();
        }
        return instance;
    }
    private static final RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                PostDao postDao = instance.postDao();
                Post post1 = new Post();
                post1.setContent("SampleContent1");
                post1.setUserId("1234");
                post1.setDateCreated(new Date());
                post1.setPicture("sample_picture_3.jpg");
                postDao.insert(post1);

                Post post2 = new Post();
                post2.setContent("SampleContent2");
                post2.setUserId("asasas");
                post2.setPicture("sample_picture_3.jpg");
                post2.setDateCreated(new Date());
                postDao.insert(post2);

                Post post3 = new Post();
                post3.setUserId("asdas");
                post3.setContent("SampleContent2");
                post3.setPicture("sample_picture_3.jpg");
                post3.setDateCreated(new Date());
                postDao.insert(post3);


                Log.d("MainActivity", "----------------inserty sie zrobiły---------------------");
            });
        }
    };
}
