package com.collegecanteen.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.collegecanteen.R;
import com.collegecanteen.models.ApiResponse;
import com.collegecanteen.models.FoodItem;
import com.collegecanteen.models.OrderItemDto;
import com.collegecanteen.models.OrderRequest;
import com.collegecanteen.network.RetrofitClient;
import com.collegecanteen.utils.CartManager;
import com.collegecanteen.utils.SessionManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    private RecyclerView rvCartItems;
    private TextView tvTotalPrice, tvSubtotal, tvItemCount;
    private View btnPlaceOrder;
    private View orderSummaryCard;
    private LinearLayout emptyCartView;
    private CartAdapter adapter;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        sessionManager = new SessionManager(this);
        rvCartItems = findViewById(R.id.rvCartItems);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvItemCount = findViewById(R.id.tvItemCount);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        orderSummaryCard = findViewById(R.id.orderSummaryCard);
        emptyCartView = findViewById(R.id.emptyCartView);

        rvCartItems.setLayoutManager(new LinearLayoutManager(this));
        
        adapter = new CartAdapter(this::updateTotal);
        rvCartItems.setAdapter(adapter);
        
        updateTotal();

        btnPlaceOrder.setOnClickListener(v -> placeOrder());

        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        com.google.android.material.bottomnavigation.BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        if (bottomNavigationView != null) {
            bottomNavigationView.setSelectedItemId(R.id.nav_cart);
            bottomNavigationView.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.nav_cart) {
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

    private void updateTotal() {
        double total = CartManager.getInstance().getTotalPrice();
        int itemCount = CartManager.getInstance().getCartItems().size();

        tvTotalPrice.setText(String.format("₹%.2f", total));
        tvSubtotal.setText(String.format("₹%.2f", total));
        tvItemCount.setText(itemCount + (itemCount == 1 ? " item in cart" : " items in cart"));

        boolean hasItems = itemCount > 0;
        rvCartItems.setVisibility(hasItems ? View.VISIBLE : View.GONE);
        orderSummaryCard.setVisibility(hasItems ? View.VISIBLE : View.GONE);
        emptyCartView.setVisibility(hasItems ? View.GONE : View.VISIBLE);
        btnPlaceOrder.setEnabled(hasItems);
    }

    private void placeOrder() {
        if (CartManager.getInstance().getCartItems().isEmpty()) {
            Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        long userId = sessionManager.getUserId();
        String pickupTime = java.time.LocalDateTime.now().plusMinutes(15).toString(); 

        List<OrderItemDto> items = new ArrayList<>();
        for (Map.Entry<FoodItem, Integer> entry : CartManager.getInstance().getCartItems().entrySet()) {
            items.add(new OrderItemDto(entry.getKey().foodId, entry.getValue()));
        }

        OrderRequest request = new OrderRequest(userId, pickupTime, items);

        RetrofitClient.getInstance().getApi().placeOrder(request).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().success) {
                    Toast.makeText(CartActivity.this, "Order Placed!", Toast.LENGTH_SHORT).show();
                    
                    CartManager.getInstance().clearCart();
                    
                    Intent intent = new Intent(CartActivity.this, OtpVerificationActivity.class);
                    intent.putExtra("MESSAGE", response.body().message);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CartActivity.this, "Failed to place order", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
