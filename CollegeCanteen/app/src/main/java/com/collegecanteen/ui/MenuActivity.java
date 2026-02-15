package com.collegecanteen.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.collegecanteen.R;
import com.collegecanteen.models.ApiResponse;
import com.collegecanteen.models.FoodItem;
import com.collegecanteen.network.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity {

    private RecyclerView rvFoodItems;
    private TextView tvCategoryTitle;
    private android.widget.ImageButton btnGoToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        String category = getIntent().getStringExtra("CATEGORY");
        String searchQuery = getIntent().getStringExtra("SEARCH_QUERY");
        
        tvCategoryTitle = findViewById(R.id.tvCategoryTitle);
        
        rvFoodItems = findViewById(R.id.rvFoodItems);
        rvFoodItems.setLayoutManager(new LinearLayoutManager(this));
        
        btnGoToCart = findViewById(R.id.btnGoToCart);
        btnGoToCart.setOnClickListener(v -> startActivity(new Intent(this, CartActivity.class)));

        if (searchQuery != null) {
            tvCategoryTitle.setText("Results for \"" + searchQuery + "\"");
            searchFoodItems(searchQuery);
        } else if (category != null) {
            tvCategoryTitle.setText(category + " Menu");
            fetchFoodItems(category);
        }
    }

    private void searchFoodItems(String query) {
        RetrofitClient.getInstance().getApi().searchFood(query).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().success) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<FoodItem>>(){}.getType();
                    List<FoodItem> foodList = gson.fromJson(response.body().data, listType);
                    
                    if (foodList.isEmpty()) {
                        Toast.makeText(MenuActivity.this, "No items found", Toast.LENGTH_SHORT).show();
                    }
                    
                    FoodAdapter adapter = new FoodAdapter(foodList);
                    rvFoodItems.setAdapter(adapter);
                } else {
                    Toast.makeText(MenuActivity.this, "Failed to search", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(MenuActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchFoodItems(String category) {
        RetrofitClient.getInstance().getApi().getFoodByCategory(category).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().success) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<FoodItem>>(){}.getType();
                    List<FoodItem> foodList = gson.fromJson(response.body().data, listType);
                    
                    FoodAdapter adapter = new FoodAdapter(foodList);
                    rvFoodItems.setAdapter(adapter);
                } else {
                    Toast.makeText(MenuActivity.this, "Failed to load menu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(MenuActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
