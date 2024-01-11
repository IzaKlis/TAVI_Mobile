package com.example.tavi.data.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import com.example.tavi.data.models.Picture;

@Dao
public interface PostPictureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Picture picture);
    @Update
    void update(Picture picture);

    @Delete
    void delete(Picture picture);
}

