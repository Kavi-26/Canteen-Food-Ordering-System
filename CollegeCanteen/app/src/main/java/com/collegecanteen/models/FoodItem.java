package com.collegecanteen.models;

import java.util.Objects;

public class FoodItem {
    public Long foodId;
    public String name;
    public String category;
    public Double price;
    public String imageUrl;
    public String description;
    public Boolean isAvailable;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodItem foodItem = (FoodItem) o;
        return Objects.equals(foodId, foodItem.foodId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foodId);
    }
}
