package com.example.tavi.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tavi.data.models.UserRelation;

import java.util.List;

@Dao
public interface UserRelationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserRelation userRelation);

    @Update
    void update(UserRelation userRelation);

    @Delete
    void delete(UserRelation userRelation);
    @Query("SELECT * FROM users_relations WHERE id_user_from = :userId OR id_user_to = :userId")
    LiveData<List<UserRelation>> findAllRelationsByUserId(int userId);
    @Query("SELECT * FROM users_relations WHERE id_user_to = :userId")
    LiveData<List<UserRelation>> getAllUserInvites(Long userId);

}
