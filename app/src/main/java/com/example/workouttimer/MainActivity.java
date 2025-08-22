package com.example.workouttimer;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workouttimer.adapter.NameAdapter;
import com.example.workouttimer.data.local.models.NameModel;
import com.example.workouttimer.viewmodel.NameViewModel;

public class MainActivity extends AppCompatActivity {

    private NameViewModel nameViewModel;
    private NameAdapter adapter;
    private EditText editTextName, editTextEmail, editTextAge;
    private Button buttonAdd;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextAge = findViewById(R.id.editTextAge);
        buttonAdd = findViewById(R.id.buttonAdd);
        recyclerView = findViewById(R.id.recyclerViewNames);

        adapter = new NameAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        nameViewModel = new ViewModelProvider(this).get(NameViewModel.class);

        // Observe LiveData
        nameViewModel.getAllNames().observe(this, names -> {
            adapter.setNames(names);
        });

        // Add name on button click
        buttonAdd.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String ageText = editTextAge.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(ageText)) {
                Toast.makeText(this, "Enter a name, email and age", Toast.LENGTH_SHORT).show();
                return;
            }

            int age;
            try {
                age = Integer.parseInt(ageText);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid age", Toast.LENGTH_SHORT).show();
                return;
            }
            nameViewModel.insert(new NameModel(name, email, age));

            // Clear input fields
            editTextName.setText("");
            editTextEmail.setText("");
            editTextAge.setText("");
        });

    }
}
