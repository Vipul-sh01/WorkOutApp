package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private UserAdapter adapter;
    private List<User> userList;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchEditText = findViewById(R.id.searchEditText);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        userList = new ArrayList<>();
        userList.add(new User("Vipul Sharma", "vipul@example.com"));
        userList.add(new User("Rahul Kumar", "rahul@example.com"));
        userList.add(new User("Priya Singh", "priya@example.com"));
        userList.add(new User("Vipul Sharma", "vipul@example.com"));
        userList.add(new User("Rahul Kumar", "rahul@example.com"));
        userList.add(new User("Priya Singh", "priya@example.com"));
        userList.add(new User("Vipul Sharma", "vipul@example.com"));
        userList.add(new User("Rahul Kumar", "rahul@example.com"));
        userList.add(new User("Priya Singh", "priya@example.com"));
        userList.add(new User("Vipul Sharma", "vipul@example.com"));
        userList.add(new User("Rahul Kumar", "rahul@example.com"));
        userList.add(new User("Priya Singh", "priya@example.com"));


        adapter = new UserAdapter(this, userList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Search listener
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }
}
