package com.example.tavi.data.repositories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tavi.data.models.User;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private LiveData<List<User>> users;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        users = userRepository.getAllUsers();
    }

    public LiveData<List<User>> findAll() {
        return users;
    }

    public void insert(User user) {
        userRepository.insertUser(user);
    }

    public void update(User user) {
        userRepository.updateUser(user);
    }

    public void delete(User user) {
        userRepository.deleteUser(user);
    }

    public LiveData<User> getUserById(int userId) {
        return userRepository.getUserById(userId);
    }
}
