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
}
