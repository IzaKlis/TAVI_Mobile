package com.example.tavi.data.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.tavi.data.database.AppDatabase;
import com.example.tavi.data.database.dao.PictureDao;
import com.example.tavi.data.models.Picture;

import java.util.List;

public class PictureRepository {
    private PictureDao pictureDao;
    private LiveData<List<Picture>> allPictures;

    public PictureRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        pictureDao = database.pictureDao();
    }

    public void insertPicture(Picture picture) {
        AppDatabase.databaseWriteExecutor.execute(() -> pictureDao.insert(picture));
    }

    public void updatePicture(Picture picture) {
        AppDatabase.databaseWriteExecutor.execute(() -> pictureDao.update(picture));
    }

    public void deletePicture(Picture picture) {
        AppDatabase.databaseWriteExecutor.execute(() -> pictureDao.delete(picture));
    }
}
