package com.collegecanteen.controller;

import com.collegecanteen.dto.ApiResponse;
import com.collegecanteen.model.FoodItem;
import com.collegecanteen.repository.FoodItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
@CrossOrigin("*")
public class FoodController {

    @Autowired
    private FoodItemRepository foodItemRepository;

    @GetMapping
    public ApiResponse getAllFood() {
        return new ApiResponse(true, "Menu fetched", foodItemRepository.findAll());
    }

    @GetMapping("/category/{category}")
    public ApiResponse getFoodByCategory(@PathVariable String category) {
        return new ApiResponse(true, "Category fetched", foodItemRepository.findByCategory(category));
    }
}
