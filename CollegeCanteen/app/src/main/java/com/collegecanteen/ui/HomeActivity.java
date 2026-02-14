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
        
        findViewById(R.id.btnViewCart).setOnClickListener(v -> startActivity(new Intent(this, CartActivity.class)));
        
        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            sessionManager.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void openMenu(String category) {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("CATEGORY", category);
        startActivity(intent);
    }
}
