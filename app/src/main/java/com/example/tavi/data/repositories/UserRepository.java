package com.example.tavi.data.repositories;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.example.tavi.data.database.AppDatabase;
import com.example.tavi.data.models.User;
import com.example.tavi.data.database.dao.UserDao;

import java.util.List;

public class UserRepository {

    private UserDao userDao;
    private LiveData<List<User>> allUsers;

    public UserRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        userDao = database.userDao();
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public void insertUser(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> userDao.insert(user));
    }

    public void updateUser(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> userDao.update(user));
    }

    public void deleteUser(User user) {
        AppDatabase.databaseWriteExecutor.execute(() -> userDao.delete(user));
    }

    public LiveData<User> getUserById(int userId) {
        return userDao.getUserById(userId);
    }
}
