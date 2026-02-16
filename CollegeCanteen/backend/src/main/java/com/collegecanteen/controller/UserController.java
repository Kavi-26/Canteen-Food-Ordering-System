package com.collegecanteen.controller;

import com.collegecanteen.dto.ApiResponse;
import com.collegecanteen.dto.LoginRequest;
import com.collegecanteen.dto.RegisterRequest;
import com.collegecanteen.model.User;
import com.collegecanteen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ApiResponse register(@RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            return new ApiResponse(false, "Email already exists", null);
        }
        if (userRepository.findByRollNo(request.rollNo()).isPresent()) {
            return new ApiResponse(false, "Roll Number already exists", null);
        }

        User user = new User();
        user.setName(request.name());
        user.setRollNo(request.rollNo());
        user.setEmail(request.email());
        user.setMobile(request.mobile());
        user.setPassword(request.password()); // In real app, hash this!
        user.setRole("STUDENT");

        userRepository.save(user);
        return new ApiResponse(true, "Registration successful", user);
    }

    @PostMapping("/login")
    public ApiResponse login(@RequestBody LoginRequest request) {
        Optional<User> user = userRepository.findByEmailAndPassword(request.email(), request.password());
        return user.map(value -> new ApiResponse(true, "Login successful", value))
                   .orElseGet(() -> new ApiResponse(false, "Invalid credentials", null));
    }

    @GetMapping("/profile/{id}")
    public ApiResponse getUserProfile(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> new ApiResponse(true, "Profile fetched", value))
                   .orElseGet(() -> new ApiResponse(false, "User not found", null));
    }
}
