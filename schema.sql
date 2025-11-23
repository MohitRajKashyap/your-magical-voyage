-- Create Database
CREATE DATABASE IF NOT EXISTS magical_voyage;
USE magical_voyage;

-- Users table
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'TRAVEL_AGENT', 'TRAVELER') NOT NULL,
    phone VARCHAR(20),
    address TEXT,
    date_of_birth DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status ENUM('ACTIVE', 'INACTIVE', 'SUSPENDED') DEFAULT 'ACTIVE',
    INDEX idx_email (email),
    INDEX idx_role (role)
);

-- Airlines table
CREATE TABLE airlines (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(10) UNIQUE NOT NULL,
    logo_url VARCHAR(255),
    contact_number VARCHAR(20),
    website_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Flights table
CREATE TABLE flights (
    id INT PRIMARY KEY AUTO_INCREMENT,
    airline_id INT,
    flight_number VARCHAR(20) NOT NULL,
    origin VARCHAR(100) NOT NULL,
    destination VARCHAR(100) NOT NULL,
    departure_time DATETIME NOT NULL,
    arrival_time DATETIME NOT NULL,
    duration_minutes INT,
    total_seats INT NOT NULL,
    available_seats INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    class_type ENUM('ECONOMY', 'BUSINESS', 'FIRST') DEFAULT 'ECONOMY',
    status ENUM('SCHEDULED', 'DELAYED', 'CANCELLED', 'COMPLETED') DEFAULT 'SCHEDULED',
    created_by INT,
    approved BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (airline_id) REFERENCES airlines(id) ON DELETE SET NULL,
    FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE SET NULL,
    INDEX idx_route (origin, destination),
    INDEX idx_departure_time (departure_time),
    INDEX idx_approved (approved)
);

-- Hotels table
CREATE TABLE hotels (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(200) NOT NULL,
    chain_name VARCHAR(100),
    description TEXT,
    address TEXT NOT NULL,
    city VARCHAR(100) NOT NULL,
    country VARCHAR(100) NOT NULL,
    star_rating INT CHECK (star_rating BETWEEN 1 AND 5),
    contact_email VARCHAR(100),
    contact_phone VARCHAR(20),
    total_rooms INT NOT NULL,
    available_rooms INT NOT NULL,
    price_per_night DECIMAL(10,2) NOT NULL,
    amenities TEXT,
    images_url TEXT,
    created_by INT,
    approved BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE SET NULL,
    INDEX idx_location (city, country),
    INDEX idx_approved (approved),
    INDEX idx_star_rating (star_rating)
);

-- Car rental companies table
CREATE TABLE car_rental_companies (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(10) UNIQUE NOT NULL,
    contact_number VARCHAR(20),
    website_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Rental cars table
CREATE TABLE rental_cars (
    id INT PRIMARY KEY AUTO_INCREMENT,
    company_id INT,
    model VARCHAR(100) NOT NULL,
    brand VARCHAR(100) NOT NULL,
    vehicle_type ENUM('SEDAN', 'SUV', 'HATCHBACK', 'LUXURY', 'VAN') DEFAULT 'SEDAN',
    year INT,
    transmission ENUM('MANUAL', 'AUTOMATIC') DEFAULT 'AUTOMATIC',
    fuel_type ENUM('PETROL', 'DIESEL', 'ELECTRIC', 'HYBRID') DEFAULT 'PETROL',
    seats INT,
    luggage_capacity INT,
    price_per_day DECIMAL(10,2) NOT NULL,
    available_units INT NOT NULL,
    location VARCHAR(100) NOT NULL,
    features TEXT,
    image_url VARCHAR(255),
    created_by INT,
    approved BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES car_rental_companies(id) ON DELETE SET NULL,
    FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE SET NULL,
    INDEX idx_location (location),
    INDEX idx_approved (approved),
    INDEX idx_vehicle_type (vehicle_type)
);

-- Flight bookings table
CREATE TABLE flight_bookings (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    flight_id INT,
    booking_reference VARCHAR(50) UNIQUE NOT NULL,
    passengers_count INT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    booking_status ENUM('PENDING', 'CONFIRMED', 'CANCELLED', 'COMPLETED') DEFAULT 'PENDING',
    payment_status ENUM('PENDING', 'PAID', 'FAILED', 'REFUNDED') DEFAULT 'PENDING',
    special_requests TEXT,
    booked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (flight_id) REFERENCES flights(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_booking_reference (booking_reference),
    INDEX idx_booking_status (booking_status)
);

-- Hotel bookings table
CREATE TABLE hotel_bookings (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    hotel_id INT,
    booking_reference VARCHAR(50) UNIQUE NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    rooms_count INT NOT NULL,
    guests_count INT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    booking_status ENUM('PENDING', 'CONFIRMED', 'CANCELLED', 'COMPLETED') DEFAULT 'PENDING',
    payment_status ENUM('PENDING', 'PAID', 'FAILED', 'REFUNDED') DEFAULT 'PENDING',
    special_requests TEXT,
    booked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (hotel_id) REFERENCES hotels(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_booking_reference (booking_reference),
    INDEX idx_check_in_date (check_in_date)
);

-- Car rental bookings table
CREATE TABLE car_rental_bookings (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    rental_car_id INT,
    booking_reference VARCHAR(50) UNIQUE NOT NULL,
    pickup_date DATE NOT NULL,
    dropoff_date DATE NOT NULL,
    pickup_location VARCHAR(100) NOT NULL,
    dropoff_location VARCHAR(100),
    total_amount DECIMAL(10,2) NOT NULL,
    booking_status ENUM('PENDING', 'CONFIRMED', 'CANCELLED', 'COMPLETED') DEFAULT 'PENDING',
    payment_status ENUM('PENDING', 'PAID', 'FAILED', 'REFUNDED') DEFAULT 'PENDING',
    special_requests TEXT,
    booked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (rental_car_id) REFERENCES rental_cars(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_booking_reference (booking_reference),
    INDEX idx_pickup_date (pickup_date)
);

-- Messages table for user interactions
CREATE TABLE messages (
    id INT PRIMARY KEY AUTO_INCREMENT,
    from_user_id INT,
    to_user_id INT,
    subject VARCHAR(200),
    message TEXT NOT NULL,
    message_type ENUM('INQUIRY', 'SUPPORT', 'FEEDBACK', 'GENERAL') DEFAULT 'GENERAL',
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (from_user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (to_user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_from_user (from_user_id),
    INDEX idx_to_user (to_user_id),
    INDEX idx_created_at (created_at)
);

-- System settings table
CREATE TABLE system_settings (
    id INT PRIMARY KEY AUTO_INCREMENT,
    setting_key VARCHAR(100) UNIQUE NOT NULL,
    setting_value TEXT NOT NULL,
    description TEXT,
    updated_by INT,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (updated_by) REFERENCES users(id) ON DELETE SET NULL,
    INDEX idx_setting_key (setting_key)
);

-- Travel itineraries table
CREATE TABLE travel_itineraries (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    start_date DATE,
    end_date DATE,
    total_cost DECIMAL(10,2),
    status ENUM('PLANNING', 'BOOKED', 'IN_PROGRESS', 'COMPLETED') DEFAULT 'PLANNING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_status (status)
);

-- Itinerary items table
CREATE TABLE itinerary_items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    itinerary_id INT,
    item_type ENUM('FLIGHT', 'HOTEL', 'CAR_RENTAL', 'ACTIVITY') NOT NULL,
    booking_reference VARCHAR(50),
    item_date DATE,
    item_time TIME,
    description TEXT,
    location VARCHAR(100),
    cost DECIMAL(10,2),
    FOREIGN KEY (itinerary_id) REFERENCES travel_itineraries(id) ON DELETE CASCADE,
    INDEX idx_itinerary_id (itinerary_id),
    INDEX idx_item_type (item_type)
);

-- Payments table (Optional - for detailed payment tracking)
CREATE TABLE payments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    booking_reference VARCHAR(50) NOT NULL,
    booking_type ENUM('FLIGHT', 'HOTEL', 'CAR_RENTAL') NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_method ENUM('CREDIT_CARD', 'DEBIT_CARD', 'PAYPAL', 'BANK_TRANSFER') NOT NULL,
    payment_status ENUM('PENDING', 'COMPLETED', 'FAILED', 'REFUNDED') DEFAULT 'PENDING',
    transaction_id VARCHAR(100),
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    INDEX idx_booking_reference (booking_reference),
    INDEX idx_payment_date (payment_date)
);

-- Reviews table
CREATE TABLE reviews (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    booking_reference VARCHAR(50) NOT NULL,
    booking_type ENUM('FLIGHT', 'HOTEL', 'CAR_RENTAL') NOT NULL,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_booking_reference (booking_reference),
    INDEX idx_rating (rating)
);