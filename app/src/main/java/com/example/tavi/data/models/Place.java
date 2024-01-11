package com.example.tavi.data.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Places")
public class Place {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "country")
    private String country;
}
