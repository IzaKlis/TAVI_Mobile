package com.example.tavi.Utills;

public class Friends {
    private String hobby, profileImageUrl, username;
    public Friends(String hobby, String profileImageUrl, String username){
        this.hobby = hobby;
        this.profileImageUrl = profileImageUrl;
        this.username = username;
    }
    public Friends(){

    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
