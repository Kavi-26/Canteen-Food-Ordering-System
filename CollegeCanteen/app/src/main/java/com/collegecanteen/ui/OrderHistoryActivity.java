package com.collegecanteen.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.collegecanteen.R;
import com.collegecanteen.models.ApiResponse;
import com.collegecanteen.models.Order;
import com.collegecanteen.network.RetrofitClient;
import com.collegecanteen.utils.SessionManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryActivity extends AppCompatActivity {

    private RecyclerView rvOrderHistory;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        sessionManager = new SessionManager(this);
        rvOrderHistory = findViewById(R.id.rvOrderHistory);
        rvOrderHistory.setLayoutManager(new LinearLayoutManager(this));

        fetchOrderHistory();
    }

    private void fetchOrderHistory() {
        long userId = sessionManager.getUserId();
        RetrofitClient.getInstance().getApi().getUserOrders(userId).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().success) {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Order>>(){}.getType();
                    List<Order> orderList = gson.fromJson(response.body().data, listType);

                    if (orderList.isEmpty()) {
                        Toast.makeText(OrderHistoryActivity.this, "No past orders found", Toast.LENGTH_SHORT).show();
                    } else {
                        // Sort by latest first (optional since backend doesn't sorting explicitly for this endpoint)
                        orderList.sort((o1, o2) -> o2.orderId.compareTo(o1.orderId));
                        OrderHistoryAdapter adapter = new OrderHistoryAdapter(orderList);
                        rvOrderHistory.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(OrderHistoryActivity.this, "Failed to load history", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(OrderHistoryActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
