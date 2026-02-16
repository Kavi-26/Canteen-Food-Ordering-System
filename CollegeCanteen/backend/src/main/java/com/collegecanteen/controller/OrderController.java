package com.collegecanteen.controller;

import com.collegecanteen.dto.ApiResponse;
import com.collegecanteen.dto.OrderRequest;
import com.collegecanteen.dto.OtpVerificationRequest;
import com.collegecanteen.model.*;
import com.collegecanteen.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.collegecanteen.service.SmsService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("*")
public class OrderController {

    @Autowired private OrderRepository orderRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private FoodItemRepository foodItemRepository;
    @Autowired private OtpCodeRepository otpCodeRepository;
    @Autowired private SmsService smsService;

    @PostMapping("/place")
    public ApiResponse placeOrder(@RequestBody OrderRequest request) {
        Optional<User> userOpt = userRepository.findById(request.userId());
        if (userOpt.isEmpty()) return new ApiResponse(false, "User not found", null);
        User user = userOpt.get();

        Order order = new Order();
        order.setUser(user);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus("PENDING_OTP"); // Status before OTP verification
        order.setPickupTime(request.pickupTime());

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (var itemDto : request.items()) {
            Optional<FoodItem> foodOpt = foodItemRepository.findById(itemDto.foodId());
            if (foodOpt.isPresent()) {
                FoodItem food = foodOpt.get();
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setFoodItem(food);
                orderItem.setQuantity(itemDto.quantity());
                orderItem.setPrice(food.getPrice());
                
                total = total.add(food.getPrice().multiply(BigDecimal.valueOf(itemDto.quantity())));
                orderItems.add(orderItem);
            }
        }
        order.setTotalAmount(total);
        order.setOrderItems(orderItems);

        orderRepository.save(order);

        // Generate OTP
        String otp = String.format("%04d", new Random().nextInt(10000));
        OtpCode otpCode = new OtpCode();
        otpCode.setUser(user);
        otpCode.setOtpCode(otp);
        otpCode.setExpiryTime(LocalDateTime.now().plusMinutes(2)); // 2 mins expiry
        otpCode.setIsUsed(false);
        otpCodeRepository.save(otpCode);

        // Send OTP via SMS to user's mobile number
        smsService.sendOtp(user.getMobile(), otp);

        // Return masked phone number in the message for the UI
        String maskedPhone = maskPhoneNumber(user.getMobile());
        return new ApiResponse(true, "OTP sent to " + maskedPhone, order.getOrderId());
    }

    private String maskPhoneNumber(String phone) {
        if (phone == null || phone.length() < 4) return "****";
        return "******" + phone.substring(phone.length() - 4);
    }

    @PostMapping("/verify-otp")
    public ApiResponse verifyOtp(@RequestBody OtpVerificationRequest request) {
        Optional<User> userOpt = userRepository.findById(request.userId());
        if (userOpt.isEmpty()) return new ApiResponse(false, "User not found", null);

        // Find items that match the user and the OTP code, and are not used
        Optional<OtpCode> otpOpt = otpCodeRepository.findByOtpCodeAndUserAndIsUsedFalse(request.otp(), userOpt.get());

        if (otpOpt.isPresent()) {
             OtpCode otpCode = otpOpt.get();
             if (otpCode.getExpiryTime().isBefore(LocalDateTime.now())) {
                 return new ApiResponse(false, "OTP Expired", null);
             }

             // OTP Valid. Update OTP usage
             otpCode.setIsUsed(true);
             otpCodeRepository.save(otpCode);

             // Confirm the latest PENDING_OTP order for this user
             List<Order> orders = orderRepository.findByUser(userOpt.get());
             // Simple logic: find the most recent one with status PENDING_OTP
             Optional<Order> pendingOrder = orders.stream()
                     .filter(o -> "PENDING_OTP".equals(o.getStatus()))
                     .sorted((o1, o2) -> o2.getOrderTime().compareTo(o1.getOrderTime())) // desc order
                     .findFirst();

             if (pendingOrder.isPresent()) {
                 Order order = pendingOrder.get();
                 order.setStatus("CONFIRMED");
                 orderRepository.save(order);
                 return new ApiResponse(true, "Order Confirmed!", order);
             } else {
                 return new ApiResponse(false, "No pending order found to confirm", null);
             }
        }
        return new ApiResponse(false, "Invalid OTP", null);
    }
    
    @GetMapping("/history/{userId}")
    public ApiResponse getOrderHistory(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) return new ApiResponse(false, "User not found", null);
        
        return new ApiResponse(true, "History fetched", orderRepository.findByUser(user.get()));
    }

    @GetMapping("/all")
    public ApiResponse getAllOrders() {
        // In a real app, check for STAFF role/permission here
        return new ApiResponse(true, "All orders fetched", orderRepository.findAll());
    }

    @PostMapping("/update-status")
    public ApiResponse updateOrderStatus(@RequestParam Long orderId, @RequestParam String status) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus(status);
            orderRepository.save(order);
            return new ApiResponse(true, "Order status updated", null);
        }
        return new ApiResponse(false, "Order not found", null);
    }
}
