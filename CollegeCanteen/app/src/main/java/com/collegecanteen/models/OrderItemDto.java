package com.collegecanteen.models;

public class OrderItemDto {
    public Long foodId;
    public Integer quantity;

    public OrderItemDto(Long foodId, Integer quantity) {
        this.foodId = foodId;
        this.quantity = quantity;
    }
}
