package com.example.tavi.data;

import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

public class Post {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String body;
    private LocalDateTime dateCreated;

}