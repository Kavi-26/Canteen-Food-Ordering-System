package com.collegecanteen.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.collegecanteen.R;
import com.collegecanteen.models.ApiResponse;
import com.collegecanteen.models.Order;
import com.collegecanteen.network.RetrofitClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StaffOrderAdapter extends RecyclerView.Adapter<StaffOrderAdapter.OrderViewHolder> {

    private List<Order> orderList;

    public StaffOrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_staff, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.tvOrderId.setText("Order #" + order.orderId);
        holder.tvOrderTime.setText("Pickup: " + order.pickupTime);
        holder.tvTotal.setText("Total: ₹ " + order.totalAmount);
        holder.tvStatus.setText("Status: " + order.status);

        setupStatusButton(holder.btnPreparing, order, "PREPARING");
        setupStatusButton(holder.btnReady, order, "READY");
        setupStatusButton(holder.btnCompleted, order, "COMPLETED");
    }

    private void setupStatusButton(Button button, Order order, String status) {
        button.setOnClickListener(v -> {
            RetrofitClient.getInstance().getApi().updateOrderStatus(order.orderId, status).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(button.getContext(), "Status Updated", Toast.LENGTH_SHORT).show();
                        order.status = status;
                        notifyDataSetChanged();
                    }
                }
                @Override public void onFailure(Call<ApiResponse> call, Throwable t) {}
            });
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvOrderTime, tvTotal, tvStatus;
        Button btnPreparing, btnReady, btnCompleted;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvOrderTime = itemView.findViewById(R.id.tvOrderTime);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            btnPreparing = itemView.findViewById(R.id.btnPreparing);
            btnReady = itemView.findViewById(R.id.btnReady);
            btnCompleted = itemView.findViewById(R.id.btnCompleted);
        }
    }
}
