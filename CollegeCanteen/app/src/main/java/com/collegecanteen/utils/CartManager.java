package com.collegecanteen.utils;

import com.collegecanteen.models.FoodItem;
import java.util.HashMap;
import java.util.Map;

public class CartManager {
    private static CartManager instance;
    private Map<FoodItem, Integer> cartItems;

    private CartManager() {
        cartItems = new HashMap<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(FoodItem item) {
        if (cartItems.containsKey(item)) {
            cartItems.put(item, cartItems.get(item) + 1);
        } else {
            cartItems.put(item, 1);
        }
    }

    public void removeFromCart(FoodItem item) {
        if (cartItems.containsKey(item)) {
            int count = cartItems.get(item);
            if (count > 1) {
                cartItems.put(item, count - 1);
            } else {
                cartItems.remove(item);
            }
        }
    }
    
    public Map<FoodItem, Integer> getCartItems() {
        return cartItems;
    }
    
    public void clearCart() {
        cartItems.clear();
    }
    
    public double getTotalPrice() {
        double total = 0;
        for (Map.Entry<FoodItem, Integer> entry : cartItems.entrySet()) {
            total += entry.getKey().price * entry.getValue();
        }
        return total;
    }
}
