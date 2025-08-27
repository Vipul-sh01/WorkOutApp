package com.example.workouttimer.views;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.workouttimer.R;
import com.example.workouttimer.data.remote.model.UserParcelable;
import com.example.workouttimer.data.remote.model.UserSerializable;

import java.util.ArrayList;

public class SerializableActivity extends AppCompatActivity {

    Button btnSerializable, btnParcelable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainserialable);

        btnSerializable = findViewById(R.id.btnSerializable);
        btnParcelable = findViewById(R.id.btnParcelable);

        btnSerializable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<UserSerializable> userList = new ArrayList<>();
                userList.add(new UserSerializable("Vipul", 23));
                userList.add(new UserSerializable("Rahul", 30));
                userList.add(new UserSerializable("Sneha", 22));

                Intent intent = new Intent(SerializableActivity.this, SerializableSecondActivity.class);
                intent.putExtra("serializable_list", userList);
                startActivity(intent);
            }
        });

        btnParcelable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<UserParcelable> parcelableList = new ArrayList<>();
                parcelableList.add(new UserParcelable("Vipul", 23));
                parcelableList.add(new UserParcelable("Rahul", 30));
                parcelableList.add(new UserParcelable("Sneha", 22));

                Intent intent = new Intent(SerializableActivity.this, SerializableSecondActivity.class);
                intent.putParcelableArrayListExtra("parcelable_list", parcelableList);
                startActivity(intent);
            }
        });
    }
}
