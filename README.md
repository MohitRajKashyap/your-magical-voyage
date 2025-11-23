# ✨ **YOUR MAGICAL VOYAGE – ONLINE TRAVEL BOOKING PLATFORM** ✈️🌍

Your Magical Voyage is a complete travel booking system that enables users to book **flights**, **hotels**, and **rental cars**.
It includes role-based dashboards for **Admins**, **Travel Agents**, and **Travelers**, offering a seamless and professional travel experience.

---

# 🌟 **1. PROJECT OVERVIEW**

Your Magical Voyage supports three main user types:

* 🛡️ **Admin** – Manages users, listings, and system settings
* 🧳 **Travel Agent** – Manages travel listings and interacts with users
* ✈️ **Traveler** – Books flights, hotels, and rental cars

---

# 🚀 **2. CORE FEATURES**

## 🛡️ **Admin Features**

* 👤 Manage user accounts
* ✔ Approve or reject travel listings
* ⚙ Adjust system configurations
* 📈 View booking statistics

---

## 🧳 **Travel Agent Features**

* 📝 Create and manage listings
* 💬 Interact with travelers
* 📦 Track bookings

---

## ✈️ **Traveler Features**

* 🛫 Book flights
* 🏨 Reserve hotels
* 🚗 Rent cars
* 📑 Manage “My Bookings”
* 🗺 View travel itineraries

---

# 🖥️ **3. DASHBOARDS**

### 🛡️ **Admin Dashboard**

* User management table
* Approval panel for listings
* System settings
* Booking analytics

### 🧳 **Travel Agent Dashboard**

* Manage flight / hotel / car listings
* Communication panel
* Booking tracker

### ✈️ **Traveler Dashboard**

* Search & book flights, hotels, cars
* View bookings
* Travel itineraries

---

# 📂 **4. PROJECT STRUCTURE (FULL JAVA FILE LIST + EMOJIS)**

```
your-magical-voyage/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── config/
│   │   │   │   ├── DatabaseConfig.java
│   │   │   │   └── ApplicationConfig.java
│   │   │   ├── controllers/
│   │   │   │   ├── AuthServlet.java
│   │   │   │   ├── FlightServlet.java
│   │   │   │   ├── HotelServlet.java
│   │   │   │   ├── CarRentalServlet.java
│   │   │   │   ├── BookingServlet.java
│   │   │   │   ├── AdminDashboardServlet.java
│   │   │   │   ├── TravelerDashboardServlet.java
│   │   │   │   └── TravelAgentDashboardServlet.java
│   │   │   ├── dao/
│   │   │   │   ├── UserDAO.java
│   │   │   │   ├── UserDAOImpl.java
│   │   │   │   ├── FlightDAO.java
│   │   │   │   ├── FlightDAOImpl.java
│   │   │   │   ├── HotelDAO.java
│   │   │   │   ├── HotelDAOImpl.java
│   │   │   │   ├── RentalCarDAO.java
│   │   │   │   ├── RentalCarDAOImpl.java
│   │   │   │   ├── BookingDAO.java
│   │   │   │   ├── BookingDAOImpl.java
│   │   │   │   ├── MessageDAO.java
│   │   │   │   └── MessageDAOImpl.java
│   │   │   ├── models/
│   │   │   │   ├── User.java
│   │   │   │   ├── Flight.java
│   │   │   │   ├── Hotel.java
│   │   │   │   ├── RentalCar.java
│   │   │   │   ├── FlightBooking.java
│   │   │   │   ├── HotelBooking.java
│   │   │   │   ├── CarRentalBooking.java
│   │   │   │   ├── Message.java
│   │   │   │   ├── TravelItinerary.java
│   │   │   │   └── ItineraryItem.java
│   │   │   ├── services/
│   │   │   │   ├── UserService.java
│   │   │   │   ├── FlightService.java
│   │   │   │   ├── HotelService.java
│   │   │   │   ├── RentalCarService.java
│   │   │   │   ├── BookingService.java
│   │   │   │   └── MessageService.java
│   │   │   ├── utils/
│   │   │   │   ├── PasswordUtil.java
│   │   │   │   ├── DateUtil.java
│   │   │   │   ├── ValidationUtil.java
│   │   │   │   ├── EmailUtil.java
│   │   │   │   ├── IdGenerator.java
│   │   │   │   └── ConcurrentBookingSimulator.java   <-- NEW FILE ADDED
│   │   │   ├── filters/
│   │   │   │   ├── AuthFilter.java
│   │   │   │   ├── RoleFilter.java
│   │   │   │   └── CharacterEncodingFilter.java
│   │   │   ├── exceptions/
│   │   │   │   ├── BookingException.java
│   │   │   │   ├── ValidationException.java
│   │   │   │   └── DatabaseException.java
│   │   │   └── factory/
│   │   │       └── ServiceFactory.java
│   │   ├── webapp/
│   │   │   ├── WEB-INF/
│   │   │   │   └── web.xml
│   │   │   ├── assets/
│   │   │   │   ├── css/
│   │   │   │   ├── js/
│   │   │   │   ├── images/
│   │   │   │   └── fonts/
│   │   │   ├── views/
│   │   │   │   ├── login.jsp
│   │   │   │   ├── register.jsp
│   │   │   │   ├── admin/
│   │   │   │   ├── traveler/
│   │   │   │   ├── agent/
│   │   │   │   └── errors/
│   │   │   └── index.jsp
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           ├── services/
│           └── dao/
├── pom.xml
└── README.md
```

---

# 🛠️ **5. TECHNOLOGIES USED**

* Java
* Servlets & JSP
* JDBC
* DAO Pattern
* MySQL
* HTML / CSS / JavaScript
* Maven
* Apache Tomcat

---

# ⚙️ **6. SETUP INSTRUCTIONS**

### **Step 1 – Clone the Repository**

```
git clone https://github.com/your-magical-voyage.git
```

### **Step 2 – Configure Database**

Edit:

```
src/main/resources/application.properties
```

### **Step 3 – Build Project**

```
mvn clean install
```

### **Step 4 – Deploy WAR to Tomcat**

### **Step 5 – Run Application**

```
http://localhost:8080/your-magical-voyage/
```

---

# 🎯 **7. FUTURE ENHANCEMENTS**

* Payment Integration
* Real-Time Flight & Hotel APIs
* Automated Email/SMS Alerts
* AI Travel Recommendations
* Android / iOS App

---

# 📜 **8. LICENSE**

© 2024 GUVI Geek Network Pvt. Ltd.
All Rights Reserved.

---
