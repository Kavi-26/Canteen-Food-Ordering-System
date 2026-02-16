package com.collegecanteen.config;

import com.collegecanteen.model.FoodItem;
import com.collegecanteen.repository.FoodItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Override
    public void run(String... args) {
        if (foodItemRepository.count() == 0) {
            seed("Idli (2 pcs)", "Breakfast", 30, "Steamed rice cakes served with chutney and sambar",
                "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=400&h=300&fit=crop");
            seed("Dosa", "Breakfast", 50, "Crispy rice crepe served with chutney and sambar",
                "https://images.unsplash.com/photo-1668236543090-82bbe40680d8?w=400&h=300&fit=crop");
            seed("Pongal", "Breakfast", 40, "Traditional rice and lentil porridge with ghee",
                "https://images.unsplash.com/photo-1630383249896-424e482df921?w=400&h=300&fit=crop");
            seed("Vada (2 pcs)", "Breakfast", 30, "Crispy fried lentil donuts with chutney",
                "https://images.unsplash.com/photo-1626132647523-66068394d31f?w=400&h=300&fit=crop");

            seed("Meals (Veg)", "Lunch", 80, "Full south Indian meals with rice, sambar, rasam and sides",
                "https://images.unsplash.com/photo-1585937421612-70a008356fbe?w=400&h=300&fit=crop");
            seed("Chicken Biryani", "Lunch", 120, "Fragrant basmati rice cooked with tender chicken pieces",
                "https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=400&h=300&fit=crop");
            seed("Chapati Curry", "Lunch", 60, "Soft wheat chapatis served with mixed vegetable curry",
                "https://images.unsplash.com/photo-1565557623262-b51c2513a641?w=400&h=300&fit=crop");
            seed("Fried Rice", "Lunch", 70, "Indo-Chinese style vegetable fried rice",
                "https://images.unsplash.com/photo-1603133872878-684f208fb84b?w=400&h=300&fit=crop");

            seed("Samosa (2 pcs)", "Snacks", 20, "Crispy pastry filled with spiced potato filling",
                "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=400&h=300&fit=crop");
            seed("Puff", "Snacks", 15, "Flaky puff pastry with savory vegetable filling",
                "https://images.unsplash.com/photo-1509365390695-33aee754301f?w=400&h=300&fit=crop");
            seed("Sandwich", "Snacks", 40, "Grilled sandwich with vegetables and cheese",
                "https://images.unsplash.com/photo-1528735602780-2552fd46c7af?w=400&h=300&fit=crop");
            seed("French Fries", "Snacks", 50, "Crispy golden french fries with ketchup",
                "https://images.unsplash.com/photo-1573080496219-bb080dd4f877?w=400&h=300&fit=crop");

            seed("Tea", "Drinks", 10, "Hot masala tea brewed with fresh spices",
                "https://images.unsplash.com/photo-1564890369478-c89ca6d9cde9?w=400&h=300&fit=crop");
            seed("Coffee", "Drinks", 15, "South Indian filter coffee with frothy milk",
                "https://images.unsplash.com/photo-1509042239860-f550ce710b93?w=400&h=300&fit=crop");
            seed("Juice", "Drinks", 30, "Freshly squeezed seasonal fruit juice",
                "https://images.unsplash.com/photo-1621506289937-a8e4df240d0b?w=400&h=300&fit=crop");
            seed("Milkshake", "Drinks", 50, "Thick creamy milkshake with your choice of flavor",
                "https://images.unsplash.com/photo-1572490122747-3968b75cc699?w=400&h=300&fit=crop");

            System.out.println("✅ Database seeded with 16 food items including images!");
        }
    }

    private void seed(String name, String category, double price, String desc, String imageUrl) {
        FoodItem item = new FoodItem();
        item.setName(name);
        item.setCategory(category);
        item.setPrice(BigDecimal.valueOf(price));
        item.setDescription(desc);
        item.setImageUrl(imageUrl);
        item.setIsAvailable(true);
        foodItemRepository.save(item);
    }
}
