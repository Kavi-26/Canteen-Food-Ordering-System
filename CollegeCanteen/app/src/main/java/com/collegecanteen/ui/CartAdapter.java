package com.collegecanteen.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.collegecanteen.R;
import com.collegecanteen.models.FoodItem;
import com.collegecanteen.utils.CartManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<FoodItem> cartItems;
    private Map<FoodItem, Integer> itemQuantities;
    private Runnable onCartUpdated;

    public CartAdapter(Runnable onCartUpdated) {
        this.onCartUpdated = onCartUpdated;
        refreshData();
    }
    
    public void refreshData() {
        this.itemQuantities = CartManager.getInstance().getCartItems();
        this.cartItems = new ArrayList<>(itemQuantities.keySet());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        FoodItem food = cartItems.get(position);
        int quantity = itemQuantities.get(food);
        
        holder.name.setText(food.name);
        holder.price.setText("₹ " + food.price);
        holder.quantity.setText(String.valueOf(quantity));
        
        Glide.with(holder.itemView.getContext())
             .load(food.imageUrl)
             .placeholder(R.drawable.ic_lunch)
             .into(holder.image);

        holder.btnAdd.setOnClickListener(v -> {
            CartManager.getInstance().addToCart(food);
            refreshData();
            onCartUpdated.run();
        });

        holder.btnRemove.setOnClickListener(v -> {
            CartManager.getInstance().removeFromCart(food);
            refreshData();
            onCartUpdated.run();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, quantity;
        ImageView image;
        ImageButton btnAdd, btnRemove;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvFoodName);
            price = itemView.findViewById(R.id.tvFoodPrice);
            quantity = itemView.findViewById(R.id.tvQuantity);
            image = itemView.findViewById(R.id.imgFood);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}
