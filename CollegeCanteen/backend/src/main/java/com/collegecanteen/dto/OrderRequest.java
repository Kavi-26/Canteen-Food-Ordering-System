package com.collegecanteen.dto;

import java.util.List;

public record OrderRequest(Long userId, String pickupTime, List<OrderItemDto> items) {}
