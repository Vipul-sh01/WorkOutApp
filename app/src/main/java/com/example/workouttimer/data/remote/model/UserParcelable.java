package com.example.workouttimer.data.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserParcelable implements Parcelable {
    private String name;
    private int age;

    public UserParcelable(String name, int age) {
        this.name = name;
        this.age = age;
    }

    protected UserParcelable(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    public static final Creator<UserParcelable> CREATOR = new Creator<UserParcelable>() {
        @Override
        public UserParcelable createFromParcel(Parcel in) {
            return new UserParcelable(in);
        }

        @Override
        public UserParcelable[] newArray(int size) {
            return new UserParcelable[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(age);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
}
