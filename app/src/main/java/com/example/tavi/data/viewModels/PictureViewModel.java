package com.example.tavi.data.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.tavi.data.models.Picture;
import com.example.tavi.data.repositories.PictureRepository;

public class PictureViewModel extends AndroidViewModel {
    private PictureRepository pictureRepository;
    public PictureViewModel(Application application) {
        super(application);
        pictureRepository = new PictureRepository(application);
    }

    public void insert(Picture picture) {
        pictureRepository.insertPicture(picture);
    }

    public void update(Picture picture) {
        pictureRepository.updatePicture(picture);
    }

    public void delete(Picture picture) {
        pictureRepository.deletePicture(picture);
    }
}
