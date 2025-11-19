<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Traveler Dashboard - Your Magical Voyage</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/styles.css" rel="stylesheet">
</head>
<body>
    <div class="header">
        <div class="container">
            <nav class="navbar">
                <a href="${pageContext.request.contextPath}/traveler/dashboard" class="logo">
                    <i class="fas fa-magic"></i> Magical Voyage
                </a>
                <ul class="nav-links">
                    <li><a href="${pageContext.request.contextPath}/traveler/dashboard" class="active"><i class="fas fa-home"></i> Dashboard</a></li>
                    <li><a href="${pageContext.request.contextPath}/traveler/flights"><i class="fas fa-plane"></i> Flights</a></li>
                    <li><a href="${pageContext.request.contextPath}/traveler/hotels"><i class="fas fa-hotel"></i> Hotels</a></li>
                    <li><a href="${pageContext.request.contextPath}/traveler/cars"><i class="fas fa-car"></i> Cars</a></li>
                    <li><a href="${pageContext.request.contextPath}/traveler/bookings"><i class="fas fa-suitcase"></i> My Bookings</a></li>
                    <li><a href="${pageContext.request.contextPath}/traveler/itineraries"><i class="fas fa-route"></i> Itineraries</a></li>
                </ul>
                <div class="user-menu">
                    <div class="user-info">
                        <div class="avatar">
                            <i class="fas fa-user"></i>
                        </div>
                        <span>${sessionScope.userName}</span>
                    </div>
                    <a href="${pageContext.request.contextPath}/auth/logout" class="btn btn-outline btn-sm">
                        <i class="fas fa-sign-out-alt"></i> Logout
                    </a>
                </div>
            </nav>
        </div>
    </div>

    <div class="dashboard">
        <div class="container">
            <div class="dashboard-header">
                <h1>Welcome back, ${sessionScope.userName}!</h1>
                <p>Ready for your next magical journey?</p>
            </div>

            <c:if test="${not empty success}">
                <div class="alert alert-success">
                    <i class="fas fa-check-circle"></i> ${success}
                </div>
            </c:if>

            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    <i class="fas fa-exclamation-circle"></i> ${error}
                </div>
            </c:if>

            <div class="stats-grid">
                <div class="stat-card">
                    <i class="fas fa-suitcase"></i>
                    <h3>Total Bookings</h3>
                    <div class="value">${totalBookings}</div>
                </div>
                <div class="stat-card">
                    <i class="fas fa-plane"></i>
                    <h3>Flight Bookings</h3>
                    <div class="value">${flightBookings.size()}</div>
                </div>
                <div class="stat-card">
                    <i class="fas fa-hotel"></i>
                    <h3>Hotel Bookings</h3>
                    <div class="value">${hotelBookings.size()}</div>
                </div>
                <div class="stat-card">
                    <i class="fas fa-envelope"></i>
                    <h3>Unread Messages</h3>
                    <div class="value">${unreadMessages}</div>
                </div>
            </div>

            <div class="card">
                <div class="card-header">
                    <h2><i class="fas fa-plane"></i> Recent Flight Bookings</h2>
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${not empty flightBookings}">
                            <div class="table-responsive">
                                <table class="data-table">
                                    <thead>
                                        <tr>
                                            <th>Reference</th>
                                            <th>Flight</th>
                                            <th>Route</th>
                                            <th>Departure</th>
                                            <th>Passengers</th>
                                            <th>Amount</th>
                                            <th>Status</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="booking" items="${flightBookings}" end="4">
                                            <tr>
                                                <td>${booking.bookingReference}</td>
                                                <td>${booking.flight.flightNumber}</td>
                                                <td>${booking.flight.origin} → ${booking.flight.destination}</td>
                                                <td>${booking.flight.departureTime}</td>
                                                <td>${booking.passengersCount}</td>
                                                <td>$${booking.totalAmount}</td>
                                                <td>
                                                    <span class="badge badge-${booking.bookingStatus == 'CONFIRMED' ? 'success' : 'warning'}">
                                                        ${booking.bookingStatus}
                                                    </span>
                                                </td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/traveler/bookings?view=${booking.id}"
                                                       class="btn btn-primary btn-sm">
                                                        <i class="fas fa-eye"></i> View
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div class="text-center mt-4">
                                <a href="${pageContext.request.contextPath}/traveler/bookings" class="btn btn-primary">
                                    View All Bookings
                                </a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="text-center p-5">
                                <i class="fas fa-plane fa-3x text-muted mb-3"></i>
                                <h3>No Flight Bookings Yet</h3>
                                <p class="text-muted">Start your journey by booking a flight!</p>
                                <a href="${pageContext.request.contextPath}/traveler/flights" class="btn btn-primary">
                                    <i class="fas fa-search"></i> Search Flights
                                </a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div class="quick-actions mt-4">
                <h2 class="mb-3">Quick Actions</h2>
                <div class="d-flex justify-between" style="gap: 1rem; flex-wrap: wrap;">
                    <a href="${pageContext.request.contextPath}/traveler/flights" class="btn btn-primary">
                        <i class="fas fa-plane"></i> Book Flight
                    </a>
                    <a href="${pageContext.request.contextPath}/traveler/hotels" class="btn btn-primary">
                        <i class="fas fa-hotel"></i> Book Hotel
                    </a>
                    <a href="${pageContext.request.contextPath}/traveler/cars" class="btn btn-primary">
                        <i class="fas fa-car"></i> Rent Car
                    </a>
                    <a href="${pageContext.request.contextPath}/traveler/itineraries" class="btn btn-primary">
                        <i class="fas fa-route"></i> Create Itinerary
                    </a>
                </div>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/assets/js/app.js"></script>
</body>
</html>