package com.example.inspiron.mysample.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.inspiron.mysample.Database.AppDatabase;

/**
 * Created by Harris on 28/2/2018
 * Project Name MySample.
 */

public class UserViewModel extends AndroidViewModel {

    private AppDatabase mDb;

    public UserViewModel(@NonNull Application application) {
        super(application);
        createDb();
    }

    public void createDb() {
        mDb = AppDatabase.getDatabaseInstance(this.getApplication());
    }
}
