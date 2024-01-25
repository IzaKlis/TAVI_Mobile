package com.example.tavi.data.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
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

    public LiveData<List<Reaction>> getAllReactions() {
        return reactionRepository.findAllReactions();
    }

    public LiveData<Reaction> getPostReactionsByPostIdAndUserId(int idPost, String idUser) {
        return reactionRepository.findPostReactionsByFkPairId(idPost, idUser);
    }

//    public void likePost(Post post, String userId){
//        LiveData<Reaction> existingReaction = getPostReactionsByPostIdAndUserId(post.getId(), userId);
//        existingReaction.observeForever(new Observer<Reaction>() {
//            @Override
//            public void onChanged(Reaction reaction) {
//                existingReaction.removeObserver(this);
//
//                if (reaction != null) {
//                    delete(reaction);
//                } else {
//                    Reaction likeReaction = new Reaction();
//                    likeReaction.setType("like");
//                    likeReaction.setPostId(post.getId());
//                    likeReaction.setUserId(userId);
//                    insert(likeReaction);
//                }
//            }
//        });
//    }
}
