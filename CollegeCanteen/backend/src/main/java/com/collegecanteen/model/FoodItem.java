package com.collegecanteen.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "food_items")
@Data
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodId;

    private String name;
    private String category;
    private BigDecimal price;
    private String imageUrl;
    private String description;
    private Boolean isAvailable;
}
