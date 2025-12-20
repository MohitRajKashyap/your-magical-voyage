# 🌟 Your Magical Voyage 🌟

Welcome to **Your Magical Voyage**, a **Java web-based travel booking platform** where users can book **flights ✈️, hotels 🏨, and rental cars 🚗**, while administrators 🛡️ manage users, travel listings, and system settings.
The project demonstrates **Servlets, JSP, JDBC, and OOP principles** in a real-world application.

---

## 📚 Table of Contents

* 🌐 Overview
* ✨ Features
* 👥 User Roles
* 🛠️ Functionalities
* 🗂️ Project Structure
* 💻 Tech Stack
* ⚙️ Setup & Installation
* 🏃‍♂️ Usage
* 📜 License

---

## 🌐 Overview

Your Magical Voyage provides a **complete travel booking solution**:

* Book **Flights ✈️, Hotels 🏨, and Rental Cars 🚗**
* Generate **Travel Itineraries 🗺️**
* Role-based dashboards for **Admin 🛡️, Travel Agent 🧑‍💼, and Traveler 🧳**
* Messaging system 💬 between users and agents
* Approval workflow ✅ for travel listings

---

## ✨ Features

* Multi-user system with **role-based access** 👥
* Book **Flights ✈️, Hotels 🏨, and Cars 🚗**
* Manage **Travel Itineraries 🗺️**
* Messaging between users and agents 💬
* Admin approval system ✅ for listings
* Dashboard with booking overview 📊

---

## 👥 User Roles

* 🛡️ **Admin**: Manages users, travel listings, system settings, and approvals
* 🧑‍💼 **Travel Agent**: Manages travel listings, communicates with travelers, tracks bookings
* 🧳 **Traveler**: Books flights, hotels, rental cars, and manages itineraries

---

## 🛠️ Functionalities

### 🛡️ Admin

1. User Management (Add / Update / Delete users)
2. Travel Listings Approval (Flights, Hotels, Cars)
3. System Configuration and Monitoring

### 🧑‍💼 Travel Agent

1. Manage travel listings (Flights, Hotels, Cars)
2. Communicate with travelers
3. Track booking status

### 🧳 Traveler

1. Book Flights, Hotels, and Rental Cars
2. View complete travel itineraries
3. Track confirmed and pending bookings

---

## 🗂️ Project Structure

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
│   │   │   │   ├── BookingService.java
│   │   │   │   └── MessageService.java
│   │   │   ├── utils/
│   │   │   │   ├── PasswordUtil.java
│   │   │   │   ├── DateUtil.java
│   │   │   │   └── ValidationUtil.java
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
│   │   │   │   │   └── styles.css
│   │   │   │   ├── js/
│   │   │   │   │   └── app.js
│   │   │   │   ├── images/
│   │   │   │   └── fonts/
│   │   │   ├── views/
│   │   │   │   ├── login.jsp
│   │   │   │   ├── register.jsp
│   │   │   │   ├── admin/
│   │   │   │   │   ├── dashboard.jsp
│   │   │   │   │   ├── users.jsp
│   │   │   │   │   ├── flights.jsp
│   │   │   │   │   ├── hotels.jsp
│   │   │   │   │   ├── bookings.jsp
│   │   │   │   │   └── pending-flights.jsp
│   │   │   │   ├── traveler/
│   │   │   │   │   ├── dashboard.jsp
│   │   │   │   │   ├── book-flights.jsp
│   │   │   │   │   ├── book-hotels.jsp
│   │   │   │   │   ├── book-cars.jsp
│   │   │   │   │   ├── my-bookings.jsp
│   │   │   │   │   └── itineraries.jsp
│   │   │   │   ├── agent/
│   │   │   │   │   ├── dashboard.jsp
│   │   │   │   │   ├── manage-flights.jsp
│   │   │   │   │   ├── manage-hotels.jsp
│   │   │   │   │   ├── manage-cars.jsp
│   │   │   │   │   ├── manage-bookings.jsp
│   │   │   │   │   └── messages.jsp
│   │   │   │   └── errors/
│   │   │   │       ├── 404.jsp
│   │   │   │       ├── 500.jsp
│   │   │   │       └── 403.jsp
│   │   │   └── index.jsp
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           ├── services/
│           └── dao/
├── pom.xml
└── README.md
---

## 💻 Tech Stack

* **Language**: Java 11+ ☕
* **Web**: Servlets, JSP 🌐
* **Database**: MySQL with JDBC 💾
* **Build Tool**: Maven ⚙️
* **Frontend**: HTML, CSS, JavaScript 🎨
* **Version Control**: Git & GitHub 🐙
* **OOP Concepts**: Inheritance, Polymorphism, Interfaces, Exception Handling

---

## ⚙️ Setup & Installation

1. Clone the repository
   git clone [https://github.com/MohitRajKashyap/your-magical-voyage.git](https://github.com/MohitRajKashyap/your-magical-voyage.git)

2. Import the project into **IntelliJ IDEA** as a **Maven project**

3. Configure database in `application.properties`
   db.url=jdbc:mysql://localhost:3306/travel_db
   db.username=root
   db.password=your_password

4. Build and deploy on **Apache Tomcat**

5. Open in browser
   [http://localhost:8080/your-magical-voyage](http://localhost:8080/your-magical-voyage)

---

## 🏃‍♂️ Usage

* **Admin 🛡️**: Manage users, listings, approvals
* **Travel Agent 🧑‍💼**: Manage travel content and bookings
* **Traveler 🧳**: Book and manage trips

---

## 📜 License

© 2024 GUVI Geek Network Pvt. Ltd.
All rights reserved. No part of this document may be reproduced or distributed without prior written permission.

---
