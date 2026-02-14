package com.collegecanteen.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.collegecanteen.R;
import com.collegecanteen.models.Order;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder> {

    private List<Order> orderList;

    public OrderHistoryAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_history, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.tvOrderId.setText("Order #" + order.orderId);
        holder.tvOrderDate.setText(order.orderTime != null ? order.orderTime.replace("T", " ").substring(0, 16) : "Date N/A");
        holder.tvOrderTotal.setText("Total: ₹" + order.totalAmount);
        holder.tvOrderStatus.setText(order.status);
        
        // Basic status coloring
        if ("CONFIRMED".equals(order.status) || "COMPLETED".equals(order.status)) {
             holder.tvOrderStatus.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.holo_green_dark));
             holder.tvOrderStatus.setBackgroundColor(0xFFE8F5E9); // Light Green
        } else if ("PENDING".equals(order.status) || "PENDING_OTP".equals(order.status)) {
             holder.tvOrderStatus.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.holo_orange_dark));
             holder.tvOrderStatus.setBackgroundColor(0xFFFFF3E0); // Light Orange
        } else {
             holder.tvOrderStatus.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.darker_gray));
             holder.tvOrderStatus.setBackgroundColor(0xFFEEEEEE); // Light Grey
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvOrderStatus, tvOrderDate, tvOrderTotal;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvOrderTotal = itemView.findViewById(R.id.tvOrderTotal);
        }
    }
}
