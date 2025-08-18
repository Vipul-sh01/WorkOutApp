package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private UserAdapter adapter;
    private List<User> userList;
    private EditText searchEditText;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchEditText = findViewById(R.id.searchEditText);
        recyclerView = findViewById(R.id.recyclerView);

        // ✅ Create full data (but don’t show yet)
        userList = new ArrayList<>();
        userList.add(new User("Vipul Sharma", "vipul@example.com"));
        userList.add(new User("Rahul Kumar", "rahul@example.com"));
        userList.add(new User("Priya Singh", "priya@example.com"));
        userList.add(new User("Neha Verma", "neha@example.com"));
        userList.add(new User("Rohit Mehta", "rohit@example.com"));
        userList.add(new User("Vipul Sharma", "vipul@example.com"));
        userList.add(new User("Rahul Kumar", "rahul@example.com"));
        userList.add(new User("Priya Singh", "priya@example.com"));
        userList.add(new User("Neha Verma", "neha@example.com"));
        userList.add(new User("Rohit Mehta", "rohit@example.com"));
        userList.add(new User("Vipul Sharma", "vipul@example.com"));
        userList.add(new User("Rahul Kumar", "rahul@example.com"));
        userList.add(new User("Priya Singh", "priya@example.com"));
        userList.add(new User("Neha Verma", "neha@example.com"));
        userList.add(new User("Rohit Mehta", "rohit@example.com"));
        userList.add(new User("Vipul Sharma", "vipul@example.com"));
        userList.add(new User("Rahul Kumar", "rahul@example.com"));
        userList.add(new User("Priya Singh", "priya@example.com"));
        userList.add(new User("Neha Verma", "neha@example.com"));
        userList.add(new User("Rohit Mehta", "rohit@example.com"));
        userList.add(new User("Vipul Sharma", "vipul@example.com"));
        userList.add(new User("Rahul Kumar", "rahul@example.com"));
        userList.add(new User("Priya Singh", "priya@example.com"));
        userList.add(new User("Neha Verma", "neha@example.com"));
        userList.add(new User("Rohit Mehta", "rohit@example.com"));
        userList.add(new User("Vipul Sharma", "vipul@example.com"));
        userList.add(new User("Rahul Kumar", "rahul@example.com"));
        userList.add(new User("Priya Singh", "priya@example.com"));
        userList.add(new User("Neha Verma", "neha@example.com"));
        userList.add(new User("Rohit Mehta", "rohit@example.com"));
        userList.add(new User("Vipul Sharma", "vipul@example.com"));
        userList.add(new User("Rahul Kumar", "rahul@example.com"));
        userList.add(new User("Priya Singh", "priya@example.com"));
        userList.add(new User("Neha Verma", "neha@example.com"));
        userList.add(new User("Rohit Mehta", "rohit@example.com"));
        userList.add(new User("Vipul Sharma", "vipul@example.com"));
        userList.add(new User("Rahul Kumar", "rahul@example.com"));
        userList.add(new User("Priya Singh", "priya@example.com"));
        userList.add(new User("Neha Verma", "neha@example.com"));
        userList.add(new User("Rohit Mehta", "rohit@example.com"));
        userList.add(new User("Vipul Sharma", "vipul@example.com"));
        userList.add(new User("Rahul Kumar", "rahul@example.com"));
        userList.add(new User("Priya Singh", "priya@example.com"));
        userList.add(new User("Neha Verma", "neha@example.com"));
        userList.add(new User("Rohit Mehta", "rohit@example.com"));
        userList.add(new User("Vipul Sharma", "vipul@example.com"));
        userList.add(new User("Rahul Kumar", "rahul@example.com"));
        userList.add(new User("Priya Singh", "priya@example.com"));
        userList.add(new User("Neha Verma", "neha@example.com"));
        userList.add(new User("Rohit Mehta", "rohit@example.com"));
        userList.add(new User("Vipul Sharma", "vipul@example.com"));
        userList.add(new User("Rahul Kumar", "rahul@example.com"));
        userList.add(new User("Priya Singh", "priya@example.com"));
        userList.add(new User("Neha Verma", "neha@example.com"));
        userList.add(new User("Rohit Mehta", "rohit@example.com"));
        userList.add(new User("Vipul Sharma", "vipul@example.com"));
        userList.add(new User("Rahul Kumar", "rahul@example.com"));
        userList.add(new User("Priya Singh", "priya@example.com"));
        userList.add(new User("Neha Verma", "neha@example.com"));
        userList.add(new User("Rohit Mehta", "rohit@example.com"));

        adapter = new UserAdapter(this, new ArrayList<>()); // start with empty list
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // 🔹 Search listener
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // If search box empty → clear results
                if (s.toString().trim().isEmpty()) {
                    adapter.updateList(new ArrayList<>()); // clear
                    recyclerView.setVisibility(View.GONE);
                } else {
                    adapter.updateList(getFilteredList(s.toString()));
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    // 🔹 Method to filter manually
    private List<User> getFilteredList(String query) {
        List<User> filteredList = new ArrayList<>();
        for (User user : userList) {
            if (user.getName().toLowerCase().contains(query.toLowerCase()) ||
                    user.getEmail().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(user);
            }
        }
        return filteredList;
    }
}
