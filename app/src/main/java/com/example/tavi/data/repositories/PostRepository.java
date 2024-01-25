package com.example.tavi.data.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.tavi.data.database.AppDatabase;
import com.example.tavi.data.database.dao.PostDao;
import com.example.tavi.data.models.Post;


import java.util.List;

public class PostRepository {

    private PostDao postDao;
    public PostRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        postDao = database.postDao();
    }

    public void insertPost(Post post) {
        AppDatabase.databaseWriteExecutor.execute(() -> postDao.insert(post));
    }

    public void updatePost(Post post) {
        AppDatabase.databaseWriteExecutor.execute(() -> postDao.update(post));
    }

    public void deletePost(Post post) {
        AppDatabase.databaseWriteExecutor.execute(() -> postDao.delete(post));
    }

    public LiveData<Post> findPostById(int postId){
        return postDao.findPostById(postId);
    }
    public LiveData<List<Post>> findPostsByUserId(String userId) {
        return postDao.findPostsByUserId(userId);
    }
    public LiveData<List<Post>> getAllPosts() {
       return postDao.findAll();
    }

}
