package com.example.workouttimer.data.local.doa;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.workouttimer.data.local.models.NameModel;

import java.util.List;

@Dao
public interface NameDao {

    // Insert a new name
    @Insert
    void insertName(NameModel nameModel);

    @Query("UPDATE users SET email = :email WHERE id = :id")
    void updateEmail(int id, String email);

    // Get all names
    @Query("SELECT * FROM users") // use the same table name as in NameModel
    LiveData<List<NameModel>> getAllNames();
}
