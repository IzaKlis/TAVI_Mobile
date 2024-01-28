package com.example.tavi.data.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "Comments")
@TypeConverters({Converters.class})
public class Comment {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "content")
    private String content;
    @ColumnInfo(name = "date_created")
    private Date dateCreated;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
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
