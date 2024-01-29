package com.example.tavi.data.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.tavi.data.database.AppDatabase;
import com.example.tavi.data.database.dao.CommentDao;
import com.example.tavi.data.models.Comment;

import java.util.List;

public class CommentRepository {
    private CommentDao commentDao;
    private LiveData<List<Comment>> allComments;

    public CommentRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        commentDao = database.commentDao();
    }

    public void insertComment(Comment comment) {
        AppDatabase.databaseWriteExecutor.execute(() -> commentDao.insert(comment));
    }

    public void updateComment(Comment comment) {
        AppDatabase.databaseWriteExecutor.execute(() -> commentDao.update(comment));
    }

    public void deleteComment(Comment comment) {
        AppDatabase.databaseWriteExecutor.execute(() -> commentDao.delete(comment));
    }
}
