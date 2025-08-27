package com.example.workouttimer.views;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.workouttimer.R;
import com.example.workouttimer.data.remote.model.UserParcelable;
import com.example.workouttimer.data.remote.model.UserSerializable;

import java.util.ArrayList;

public class SerializableSecondActivity extends AppCompatActivity {

    TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondserialable);

        textResult = findViewById(R.id.textResult);

        // Check Serializable
        ArrayList<UserSerializable> serializableList =
                (ArrayList<UserSerializable>) getIntent().getSerializableExtra("serializable_list");

        if (serializableList != null) {
            StringBuilder sb = new StringBuilder("Serializable User List:\n");
            for (UserSerializable user : serializableList) {
                sb.append(user.getName()).append(" - ").append(user.getAge()).append("\n");
            }
            textResult.setText(sb.toString());
        }


        // Check Parcelable
        ArrayList<UserParcelable> parcelableList =
                getIntent().getParcelableArrayListExtra("parcelable_list");

        if (parcelableList != null) {
            StringBuilder sb = new StringBuilder("Parcelable User List:\n");
            for (UserParcelable user : parcelableList) {
                sb.append(user.getName()).append(" - ").append(user.getAge()).append("\n");
            }
            textResult.setText(sb.toString());
        }

    }
}
