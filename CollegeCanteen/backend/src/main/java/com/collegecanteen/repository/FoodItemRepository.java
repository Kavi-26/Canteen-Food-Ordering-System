package com.collegecanteen.repository;

import com.collegecanteen.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
    List<FoodItem> findByCategory(String category);
    List<FoodItem> findByNameContainingIgnoreCase(String name);
    List<FoodItem> findByIsAvailableTrue();
}
