package com.collegecanteen.controller;

import com.collegecanteen.dto.ApiResponse;
import com.collegecanteen.model.Order;
import com.collegecanteen.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/staff")
@CrossOrigin("*")
public class StaffController {

    @Autowired private OrderRepository orderRepository;

    @GetMapping("/orders")
    public ApiResponse getAllOrders() {
        return new ApiResponse(true, "All orders fetched", orderRepository.findAll());
    }

    @PostMapping("/update-status")
    public ApiResponse updateStatus(@RequestParam Long orderId, @RequestParam String status) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus(status);
            orderRepository.save(order);
            return new ApiResponse(true, "Order status updated to " + status, order);
        }
        return new ApiResponse(false, "Order not found", null);
    }
}
