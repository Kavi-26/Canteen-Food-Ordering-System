package com.collegecanteen.models;

public class OrderItem {
    public Long id;
    public Long foodId;
    public Integer quantity;
    public Double price;
    // We might need food name here from backend, but for now backend sends nested structure?
    // Actually Backend OrderItem entity has FoodItem relation.
    // Let's assume Backend sends full object or we might need to adjust based on JSON response.
    // For simplicity, let's look at Backend Order Entity. It has List<OrderItem>.
}
