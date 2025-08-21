package com.example.workouttimer.data.local.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

@Entity(tableName = "images")
public class ImageEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String uri;

    public ImageEntity() { }

    @Ignore
    public ImageEntity(String uri) {
        this.uri = uri;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }
}
