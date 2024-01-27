package com.example.tavi.data.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.example.tavi.data.database.AppDatabase;
import com.example.tavi.data.database.dao.ReactionDao;
import com.example.tavi.data.models.Reaction;
import java.util.List;

public class ReactionRepository {
    private ReactionDao reactionDao;
    public ReactionRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        reactionDao = database.reactionDao();
    }

    public void insertReaction(Reaction reaction) {
        AppDatabase.databaseWriteExecutor.execute(() -> reactionDao.insert(reaction));
    }

    public void updateReaction(Reaction reaction) {
        AppDatabase.databaseWriteExecutor.execute(() -> reactionDao.update(reaction));
    }

    public void deleteReaction(Reaction reaction) {
        AppDatabase.databaseWriteExecutor.execute(() ->reactionDao.delete(reaction));
    }

    public LiveData<List<Reaction>> findAllReactions(int idPost) {
        return reactionDao.findAllByPostId(idPost);
    }
    public LiveData<Reaction> findPostReactionsByFkPairId(int idPost, String idUser) {
        return reactionDao.findPostReactionsByFkPairId(idPost,idUser);
    }
    public LiveData<Integer> getReactionCountForPost(int postId) {
        return reactionDao.getReactionCountForPost(postId);
    }

    public Reaction findReaction (int idPost,String idUser){
        return reactionDao.findReaction(idPost,idUser);
    }
}
