package com.example.tavi.data.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

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

    public void updateRelation(Reaction reaction) {
        AppDatabase.databaseWriteExecutor.execute(() -> reactionDao.update(reaction));
    }

    public void deleteRelation(Reaction reaction) {
        AppDatabase.databaseWriteExecutor.execute(() ->reactionDao.delete(reaction));
    }

    public LiveData<List<Reaction>> findAllReactions() {
        return reactionDao.findAll();
    }
    public LiveData<Reaction> findPostReactionsByFkPairId(int idPost, int idUser) {
        return reactionDao.findPostReactionsByFkPairId(idPost,idUser);
    }
}
