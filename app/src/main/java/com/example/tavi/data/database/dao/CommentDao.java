package com.example.tavi.data.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import com.example.tavi.data.models.Comment;

@Dao
public interface CommentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Comment comment);
    @Update
    void update(Comment comment);
    @Delete
    void delete(Comment comment);
}
