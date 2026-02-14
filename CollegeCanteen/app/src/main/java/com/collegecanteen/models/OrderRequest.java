package com.collegecanteen.models;

import java.util.List;

public class OrderRequest {
    public Long userId;
    public String pickupTime;
    public List<OrderItemDto> items;

    public OrderRequest(Long userId, String pickupTime, List<OrderItemDto> items) {
        this.userId = userId;
        this.pickupTime = pickupTime;
        this.items = items;
    }
}
