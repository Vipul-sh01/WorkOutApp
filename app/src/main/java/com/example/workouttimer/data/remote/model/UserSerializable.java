package com.example.workouttimer.data.remote.model;

import java.io.Serializable;

public class UserSerializable implements Serializable {
    private String name;
    private int age;

    public UserSerializable(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
}
