package com.example.workouttimer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.workouttimer.fragment.CartFragment;
import com.example.workouttimer.fragment.HomeFragment;
import com.example.workouttimer.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // Load HomeFragment by default
       if(savedInstanceState == null){
           getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
       }

        // Handle navigation item clicks
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (item.getItemId() == R.id.nav_cart) {
                selectedFragment = new CartFragment();
            } else if (item.getItemId() == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();

            return true;
        });
    }
}
