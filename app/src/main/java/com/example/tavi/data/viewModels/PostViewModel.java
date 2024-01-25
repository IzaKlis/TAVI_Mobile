package com.example.tavi.data.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tavi.data.models.Post;
import com.example.tavi.data.models.Reaction;
import com.example.tavi.data.repositories.PostRepository;
import java.util.List;

public class PostViewModel extends AndroidViewModel {
    private PostRepository postRepository;
    public PostViewModel(@NonNull Application application) {
        super(application);
        postRepository = new PostRepository(application);
    }

    public LiveData<List<Post>> findAllPosts() {
        return postRepository.getAllPosts();
    }

    public void insert(Post post) {
        postRepository.insertPost(post);
    }

    public void update(Post post) {
        postRepository.updatePost(post);
    }

    public void delete(Post post) {
        postRepository.updatePost(post);
    }

    public LiveData<Post> findPostById(int postId) {
        return postRepository.findPostById(postId);
    }

    public LiveData<List<Post>> findPostsByUserId(String userId) {
        return postRepository.findPostsByUserId(userId);
    }
}
