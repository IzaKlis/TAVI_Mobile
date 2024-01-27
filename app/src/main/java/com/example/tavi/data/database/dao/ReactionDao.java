package com.example.tavi.data.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tavi.data.models.Reaction;

import java.util.List;

@Dao
public interface ReactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Reaction reaction);

    @Update
    void update(Reaction reaction);

    @Delete
    void delete(Reaction reaction);
    @Query("SELECT * FROM Posts_reactions WHERE id_post= :idPost")
    LiveData<List<Reaction>> findAllByPostId(int idPost);

    @Query("SELECT * FROM Posts_reactions WHERE id_post = :idPost AND  id_user = :idUser")
    LiveData<Reaction> findPostReactionsByFkPairId(int idPost, String idUser);

    @Query("SELECT COUNT(*) FROM Posts_reactions WHERE id_post = :postId")
    LiveData<Integer> getReactionCountForPost(int postId);

    @Query("SELECT * FROM Posts_reactions WHERE id_post = :idPost AND  id_user = :idUser")
    Reaction findReaction (int idPost,String idUser);

}
