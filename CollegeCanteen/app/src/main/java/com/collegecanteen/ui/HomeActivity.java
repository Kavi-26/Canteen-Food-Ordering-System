package com.collegecanteen.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.collegecanteen.R;
import com.collegecanteen.utils.SessionManager;

public class HomeActivity extends AppCompatActivity {

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        sessionManager = new SessionManager(this);
        if (!sessionManager.isLoggedIn()) {
             startActivity(new Intent(this, LoginActivity.class));
             finish();
             return;
        }

        TextView tvWelcome = findViewById(R.id.tvWelcome);
        String name = sessionManager.getUserName();
        tvWelcome.setText("Good Morning, " + name + "!"); 

        findViewById(R.id.cardBreakfast).setOnClickListener(v -> openMenu("Breakfast"));
        findViewById(R.id.cardLunch).setOnClickListener(v -> openMenu("Lunch"));
        findViewById(R.id.cardSnacks).setOnClickListener(v -> openMenu("Snacks"));
        findViewById(R.id.cardDrinks).setOnClickListener(v -> openMenu("Drinks"));
        
        findViewById(R.id.cardDrinks).setOnClickListener(v -> openMenu("Drinks"));

        androidx.appcompat.widget.SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null && !query.trim().isEmpty()) {
                    Intent intent = new Intent(HomeActivity.this, MenuActivity.class);
                    intent.putExtra("SEARCH_QUERY", query.trim());
                    startActivity(intent);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        com.google.android.material.bottomnavigation.BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        if (bottomNavigationView != null) {
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
            bottomNavigationView.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    return true;
                } else if (itemId == R.id.nav_cart) {
                    startActivity(new Intent(getApplicationContext(), CartActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.nav_history) {
                    startActivity(new Intent(getApplicationContext(), OrderHistoryActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.nav_profile) {
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            });
        }
    }

    private void openMenu(String category) {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("CATEGORY", category);
        startActivity(intent);
    }
}
