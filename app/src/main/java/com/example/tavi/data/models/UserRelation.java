package com.example.tavi.data.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "Users_Relations")
@TypeConverters({Converters.class})
public class UserRelation {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "date_created")
    private Date dateCreated;
    @ColumnInfo(name = "id_user_from")
    private int userFromId;
    @ColumnInfo(name = "id_user_to")
    private int userToId;

    public UserRelation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getUserFromId() {
        return userFromId;
    }

    public void setUserFromId(int userFromId) {
        this.userFromId = userFromId;
    }

    public int getUserToId() {
        return userToId;
    }

    public void setUserToId(int userToId) {
        this.userToId = userToId;
    }
}
