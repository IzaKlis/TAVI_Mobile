package com.example.tavi.data.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Posts_Reactions")
public class Reaction {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "id_post")
    private int postId;
    @ColumnInfo(name = "id_user")
    private String userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

