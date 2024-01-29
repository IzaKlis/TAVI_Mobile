package com.example.tavi.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.tavi.data.database.dao.PictureDao;
import com.example.tavi.data.database.dao.UserDao;
import com.example.tavi.data.database.dao.PostDao;
import com.example.tavi.data.database.dao.CommentDao;
import com.example.tavi.data.database.dao.ReactionDao;
import com.example.tavi.data.database.dao.UserRelationDao;
import com.example.tavi.data.models.User;
import com.example.tavi.data.models.Post;
import com.example.tavi.data.models.Comment;
import com.example.tavi.data.models.Reaction;
import com.example.tavi.data.models.Picture;
import com.example.tavi.data.models.UserRelation;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Post.class, Comment.class, Reaction.class, Picture.class, UserRelation.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "tavi_database";
    private static AppDatabase instance;

    public abstract UserDao userDao();

    public abstract PostDao postDao();

    public abstract UserRelationDao userRelationDao();

    public abstract CommentDao commentDao();

    public abstract ReactionDao reactionDao();

    public abstract PictureDao pictureDao();

    public static final ExecutorService databaseWriteExecutor = Executors.newSingleThreadExecutor();

    public static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                    .build();
        }
        return instance;
    }
    private static final RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(SupportSQLiteDatabase db) {
            super.onCreate(db);
            // Add your sample data here using databaseWriteExecutor
            databaseWriteExecutor.execute(() -> {
                // Example data for User
                PostDao postDao = instance.postDao();
                Post post1 = new Post();
                post1.setTitle("SampleTitle1");
                post1.setContent("SampleContent1");
                post1.setDateCreated(new Date());
                postDao.insert(post1);

                Post post2 = new Post();
                post2.setTitle("SampleTitle2");
                post2.setContent("SampleContent2");
                post2.setDateCreated(new Date());
                postDao.insert(post2);

                Post post3 = new Post();
                post2.setTitle("SampleTitle2");
                post2.setContent("SampleContent2");
                post2.setDateCreated(new Date());
                postDao.insert(post3);

                // Add more sample data for other entities as needed
            });
        }
    };

}
