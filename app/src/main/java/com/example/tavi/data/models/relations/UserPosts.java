package com.example.tavi.data.models.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.tavi.data.models.Post;
import com.example.tavi.data.models.User;

import java.util.Set;

public class UserPosts {
    @Embedded
    private User user;

    @Relation(parentColumn = "id", entityColumn = "id_user")
    private Set<Post> posts;
}
