package com.example.tavi.data.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tavi.data.models.UserRelation;
import com.example.tavi.data.repositories.UserRelationRepository;

import java.util.List;

public class UserRelationViewModel extends AndroidViewModel {
    private UserRelationRepository userRelationRepository;
    private LiveData<List<UserRelation>> allUserRelations;

    public UserRelationViewModel(Application application) {
        super(application);
        userRelationRepository = new UserRelationRepository(application);
    }

    public void insert(UserRelation userRelation) {
        userRelationRepository.insertRelation(userRelation);
    }

    public void update(UserRelation userRelation) {
        userRelationRepository.updateRelation(userRelation);
    }

    public void delete(UserRelation userRelation) {
        userRelationRepository.deleteRelation(userRelation);
    }

    public LiveData<List<UserRelation>> findAllRelationsByUserId(int userId) {
        return userRelationRepository.findAllRelationsByUserId(userId);
    }
    public LiveData<List<UserRelation>> getAllUserInvites(int userId) {
        return userRelationRepository.getAllUserInvites(userId);
    }

}
