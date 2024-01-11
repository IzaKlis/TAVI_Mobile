package com.example.tavi.data.models.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.tavi.data.models.UserRelation;
import com.example.tavi.data.models.User;

import java.util.Set;

public class UserRelations {
    @Embedded
    private User user;
    @Relation(parentColumn = "id", entityColumn = "id_user_from")
    private Set<UserRelation> userRelationsFrom;
    @Relation(parentColumn = "id", entityColumn = "id_user_to")
    private Set<UserRelation> userRelationsTo;
}
