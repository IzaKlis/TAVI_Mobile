package com.example.tavi.data.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "Posts")
@TypeConverters({Converters.class})
public class Post {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name =  "content")
    private String content;
    @ColumnInfo(name = "date_created")
    private Date dateCreated;
    @ColumnInfo(name = "id_user")
    private String userId;

    @ColumnInfo(name = "post_picture")
    private String picture;

    @ColumnInfo(name = "likes_counter")
    private int likesCounter;


    public Post() {
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getLikesCounter() {
        return likesCounter;
    }

    public void setLikesCounter(int likesCounter) {
        this.likesCounter = likesCounter;
    }
}