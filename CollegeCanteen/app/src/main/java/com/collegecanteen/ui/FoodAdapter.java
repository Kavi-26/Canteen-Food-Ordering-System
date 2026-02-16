package com.collegecanteen.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.collegecanteen.R;
import com.collegecanteen.models.FoodItem;
import com.collegecanteen.utils.CartManager;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<FoodItem> foodList;

    public FoodAdapter(List<FoodItem> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodItem food = foodList.get(position);
        holder.name.setText(food.name);
        holder.desc.setText(food.description);
        holder.price.setText("₹ " + food.price);
        
        // Load food image with Glide
        Glide.with(holder.itemView.getContext())
             .load(food.imageUrl)
             .transform(new CenterCrop())
             .placeholder(R.drawable.ic_lunch)
             .error(R.drawable.ic_lunch)
             .into(holder.image);

        holder.btnAdd.setOnClickListener(v -> {
            CartManager.getInstance().addToCart(food);
            Toast.makeText(holder.itemView.getContext(), food.name + " added to cart", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView name, desc, price;
        ImageView image;
        View btnAdd;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvFoodName);
            desc = itemView.findViewById(R.id.tvFoodDesc);
            price = itemView.findViewById(R.id.tvFoodPrice);
             image = itemView.findViewById(R.id.imgFood);
             btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }
}
