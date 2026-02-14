package com.collegecanteen.models;

import java.util.List;

public class Order {
    public Long orderId;
    public Long userId;
    public Double totalAmount;
    public String status;
    public String orderTime;
    public String pickupTime;
    public List<OrderItem> orderItems;
}
