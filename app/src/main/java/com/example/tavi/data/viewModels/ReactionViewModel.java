package com.example.tavi.data.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.tavi.data.models.Post;
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
        reactionRepository.updateReaction(reaction);
    }

    public void delete(Reaction reaction) {
        reactionRepository.deleteReaction(reaction);
    }

    public LiveData<List<Reaction>> getAllReactions(int idPost) {
        return reactionRepository.findAllReactions(idPost);
    }

    public void likePost(int postId, String userId) {
        Reaction reaction = new Reaction();
        reaction.setPostId(postId);
        reaction.setUserId(userId);
        reaction.setType("like");
        Log.d("MainActivity", reaction.getUserId());
        Log.d("MainActivity", String.valueOf(reaction.getPostId()));
        Log.d("MainActivity", reaction.getType());
        Log.d("MainActivity", String.valueOf(reaction.getId()));

        try {
            reactionRepository.insertReaction(reaction);
            Log.d("MainActivity", "Reaction inserted: " + postId + " and user: " + userId);
        } catch (Exception e) {
            Log.e("MainActivity", "Failed to insert reaction: " + e.getMessage());
        }
    }

    public LiveData<Reaction> findPostReactionsByFkPairId(int idPost, String idUser) {
        return reactionRepository.findPostReactionsByFkPairId(idPost,idUser);
    }


    public void unlikePost(Reaction reaction) {
        if (reaction != null) {
            reactionRepository.deleteReaction(reaction);
        } else {
            Log.d("MainActivity", "Reaction not found");
        }
    }

    public LiveData<Integer> getReactionCountForPost(int postId) {
        return reactionRepository.getReactionCountForPost(postId);
    }
}
