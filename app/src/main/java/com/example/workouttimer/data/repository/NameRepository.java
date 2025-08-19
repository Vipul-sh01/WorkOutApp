package com.example.workouttimer.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.workouttimer.data.local.db.NameDatabase;

import com.example.workouttimer.data.local.doa.NameDao;
import com.example.workouttimer.data.local.models.NameModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NameRepository {

    private NameDao nameDao;
    private LiveData<List<NameModel>> allNames;
    private ExecutorService executorService;

    public NameRepository(Application application) {
        NameDatabase database = NameDatabase.getDatabase(application);
        nameDao = database.nameDao();
        allNames = nameDao.getAllNames();
        executorService = Executors.newSingleThreadExecutor(); // For background operations
    }

    // Get all names
    public LiveData<List<NameModel>> getAllNames() {
        return allNames;
    }

    // Insert a name
    public void insert(NameModel nameModel) {
        executorService.execute(() -> nameDao.insertName(nameModel));
    }
}
