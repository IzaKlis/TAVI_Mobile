package com.example.tavi.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tavi.data.models.Post;

import java.util.List;

@Dao
public interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Post post);

    @Update
    void update(Post post);

    @Delete
    void delete(Post post);

    @Query("SELECT * FROM posts ORDER BY id")
    LiveData<List<Post>> findAll();

    @Query("SELECT * FROM posts WHERE id = :postId LIMIT 1")
    LiveData<Post> findPostById(int postId);

    @Query("SELECT * FROM posts WHERE id_user = :userId")
    LiveData<List<Post>> findPostsByUserId(int userId);
}
