package com.example.tavi.data.models.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.tavi.data.models.Comment;
import com.example.tavi.data.models.Post;
import com.example.tavi.data.models.Reaction;

import java.util.Set;

public class PostDetails {
    @Embedded
    private Post post;
    @Relation(parentColumn = "id", entityColumn = "id_post")
    private Set<Comment> postComments;
    @Relation(parentColumn = "id", entityColumn = "id_post")
    private Set<Reaction> postReactions;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Set<Comment> getPostComments() {
        return postComments;
    }

    public void setPostComments(Set<Comment> postComments) {
        this.postComments = postComments;
    }

    public Set<Reaction> getPostReactions() {
        return postReactions;
    }

    public void setPostReactions(Set<Reaction> postReactions) {
        this.postReactions = postReactions;
    }
}
