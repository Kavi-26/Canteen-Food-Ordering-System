package com.collegecanteen.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.collegecanteen.R;
import com.collegecanteen.models.ApiResponse;
import com.collegecanteen.models.User;
import com.collegecanteen.network.RetrofitClient;
import com.collegecanteen.utils.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private TextView tvName, tvEmail, tvRole, tvMobile, tvRollNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sessionManager = new SessionManager(this);

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvRole = findViewById(R.id.tvRole);
        tvMobile = findViewById(R.id.tvMobile);
        tvRollNo = findViewById(R.id.tvRollNo);
        View btnLogout = findViewById(R.id.btnLogout);

        // Load cached data first (instant display)
        tvName.setText(sessionManager.getUserName());
        tvEmail.setText(sessionManager.getUserEmail());
        tvRole.setText(sessionManager.getUserRole());
        tvMobile.setText(sessionManager.getUserMobile());
        tvRollNo.setText(sessionManager.getUserRollNo());

        // Then fetch fresh data from database
        fetchProfileFromServer();

        btnLogout.setOnClickListener(v -> {
            sessionManager.logout();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        setupBottomNavigation();
    }

    private void fetchProfileFromServer() {
        long userId = sessionManager.getUserId();

        RetrofitClient.getInstance().getApi().getUserProfile(userId).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    com.google.gson.Gson gson = new com.google.gson.Gson();
                    String json = gson.toJson(response.body().getData());
                    User user = gson.fromJson(json, User.class);

                    // Update UI with fresh data from database
                    tvName.setText(user.getName() != null ? user.getName() : "");
                    tvEmail.setText(user.getEmail() != null ? user.getEmail() : "");
                    tvRole.setText(user.getRole() != null ? user.getRole() : "");
                    tvMobile.setText(user.getMobile() != null ? user.getMobile() : "");
                    tvRollNo.setText(user.getRollNo() != null ? user.getRollNo() : "");

                    // Update session cache with fresh data
                    sessionManager.createLoginSession(
                        user.getName(),
                        user.getEmail(),
                        user.getRole(),
                        user.getUserId(),
                        user.getMobile(),
                        user.getRollNo()
                    );
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Silently fail — cached data is already shown
            }
        });
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        if (bottomNavigationView != null) {
            bottomNavigationView.setSelectedItemId(R.id.nav_profile);
            bottomNavigationView.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    overridePendingTransition(0, 0);
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
                    return true;
                }
                return false;
            });
        }
    }
}
