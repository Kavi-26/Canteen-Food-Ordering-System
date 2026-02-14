-- Create Users Table
CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    roll_no VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    mobile VARCHAR(15) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'STUDENT' -- 'STUDENT' or 'STAFF'
);

-- Create Food Items Table
CREATE TABLE IF NOT EXISTS food_items (
    food_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(50) NOT NULL, -- Breakfast, Lunch, Snacks, Drinks
    price DECIMAL(10, 2) NOT NULL,
    image_url VARCHAR(500),
    description TEXT,
    is_available BOOLEAN DEFAULT TRUE
);

-- Create Orders Table
CREATE TABLE IF NOT EXISTS orders (
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) DEFAULT 'PENDING', -- PENDING, CONFIRMED, PREPARING, READY, COMPLETED, CANCELLED
    order_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    pickup_time VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Create Order Items Table
CREATE TABLE IF NOT EXISTS order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    food_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (food_id) REFERENCES food_items(food_id)
);

-- Create OTP Table
CREATE TABLE IF NOT EXISTS otp_codes (
    otp_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    otp_code VARCHAR(6) NOT NULL,
    expiry_time TIMESTAMP NOT NULL,
    is_used BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Seed Data (Initial Menu)
INSERT IGNORE INTO food_items (name, category, price, description, is_available) VALUES
('Idli (2 pcs)', 'Breakfast', 30.00, 'Steamed rice cakes served with chutney and sambar', TRUE),
('Dosa', 'Breakfast', 50.00, 'Crispy rice crepe served with chutney and sambar', TRUE),
('Veg Biryani', 'Lunch', 80.00, 'Spiced rice with mixed vegetables', TRUE),
('Curd Rice', 'Lunch', 40.00, 'Rice mixed with yogurt and tempered spices', TRUE),
('Samosa', 'Snacks', 15.00, 'Fried pastry with spiced potato filling', TRUE),
('Tea', 'Drinks', 10.00, 'Hot milk tea', TRUE),
('Coffee', 'Drinks', 15.00, 'Hot coffee', TRUE),
('Fruit Juice', 'Drinks', 40.00, 'Fresh seasonal fruit juice', TRUE);

-- Seed Data (Test Users)
-- Using plain text passwords for demo purposes as requested
INSERT IGNORE INTO users (name, roll_no, email, mobile, password, role) VALUES
('Student User', 'STU123456', 'student@college.edu', '9876543210', '123456', 'STUDENT'),
('Gugan', 'GUGAN001', 'gugan@college.edu', '1231231234', '123456', 'STUDENT'),
('Canteen Staff', 'STAFF01', 'staff@college.edu', '9999999999', '123456', 'STAFF');
