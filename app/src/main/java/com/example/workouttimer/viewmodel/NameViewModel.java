package com.example.workouttimer.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.workouttimer.data.local.models.NameModel;
import com.example.workouttimer.data.repository.NameRepository;


import java.util.List;

public class NameViewModel extends AndroidViewModel {

    private NameRepository repository;
    private LiveData<List<NameModel>> allNames;

    public NameViewModel(@NonNull Application application) {
        super(application);
        repository = new NameRepository(application);
        allNames = repository.getAllNames();
    }

    // Method to get all names
    public LiveData<List<NameModel>> getAllNames() {
        return allNames;
    }

    // Method to insert a name
    public void insert(NameModel nameModel) {
        repository.insert(nameModel);
    }
}
