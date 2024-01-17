package com.example.tavi.data.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.tavi.data.models.Comment;
import com.example.tavi.data.repositories.CommentRepository;

public class CommentViewModel extends AndroidViewModel {
    private CommentRepository commentRepository;

    public CommentViewModel(Application application) {
        super(application);
        commentRepository = new CommentRepository(application);
    }

    public void insert(Comment comment) {
        commentRepository.insertComment(comment);
    }

    public void update(Comment comment) {
        commentRepository.updateComment(comment);
    }

    public void delete(Comment comment) {
        commentRepository.deleteComment(comment);
    }
}
