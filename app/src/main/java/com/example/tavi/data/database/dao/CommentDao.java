package com.example.tavi.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tavi.data.models.Comment;
import com.example.tavi.data.models.Reaction;

import java.util.List;

@Dao
public interface CommentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Comment comment);
    @Update
    void update(Comment comment);
    @Delete
    void delete(Comment comment);

    @Query("SELECT * FROM Comments WHERE id_post= :idPost")
    LiveData<List<Comment>> findAllByPostId(int idPost);
}
