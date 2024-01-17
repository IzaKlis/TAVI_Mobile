package com.example.tavi.data.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.tavi.data.database.AppDatabase;
import com.example.tavi.data.database.dao.UserRelationDao;
import com.example.tavi.data.models.UserRelation;

import java.util.List;

public class UserRelationRepository {
    private UserRelationDao userRelationDao;
    public UserRelationRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        userRelationDao = database.userRelationDao();
    }

    public void insertRelation(UserRelation userRelation) {
        AppDatabase.databaseWriteExecutor.execute(() -> userRelationDao.insert(userRelation));
    }

    public void updateRelation(UserRelation userRelation) {
        AppDatabase.databaseWriteExecutor.execute(() -> userRelationDao.update(userRelation));
    }

    public void deleteRelation(UserRelation userRelation) {
        AppDatabase.databaseWriteExecutor.execute(() ->userRelationDao.delete(userRelation));
    }

    public LiveData<List<UserRelation>> findAllRelationsByUserId(int userId) {
        return userRelationDao.findAllRelationsByUserId(userId);
    }
    public LiveData<List<UserRelation>> getAllUserInvites(int userId) {
        return userRelationDao.getAllUserInvites(userId);
    }
}
