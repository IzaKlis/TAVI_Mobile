package com.example.tavi.data.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tavi.data.models.Reaction;
import com.example.tavi.data.repositories.ReactionRepository;

import java.util.List;

public class ReactionViewModel extends AndroidViewModel {
    private ReactionRepository reactionRepository;

    public ReactionViewModel(Application application) {
        super(application);
        reactionRepository = new ReactionRepository(application);
    }

    public void insert(Reaction reaction) {
        reactionRepository.insertReaction(reaction);
    }

    public void update(Reaction reaction) {
        reactionRepository.updateRelation(reaction);
    }

    public void delete(Reaction reaction) {
        reactionRepository.deleteRelation(reaction);
    }

    public LiveData<List<Reaction>> getAllReactions() {
        return reactionRepository.findAllReactions();
    }

    public LiveData<Reaction> getPostReactionsByPostIdAndUserId(int idPost, int idUser) {
        return reactionRepository.findPostReactionsByFkPairId(idPost, idUser);
    }

}
