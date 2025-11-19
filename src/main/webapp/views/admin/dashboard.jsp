<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Your Magical Voyage</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/styles.css" rel="stylesheet">
</head>
<body>
    <div class="header">
        <div class="container">
            <nav class="navbar">
                <a href="${pageContext.request.contextPath}/admin/dashboard" class="logo">
                    <i class="fas fa-magic"></i> Magical Voyage
                </a>
                <ul class="nav-links">
                    <li><a href="${pageContext.request.contextPath}/admin/dashboard" class="active"><i class="fas fa-home"></i> Dashboard</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/users"><i class="fas fa-users"></i> Users</a></li>
                    <li><a href="${pageContext.request.contextPath}/flights/pending"><i class="fas fa-plane"></i> Pending Flights</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/hotels"><i class="fas fa-hotel"></i> Hotels</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/bookings"><i class="fas fa-suitcase"></i> Bookings</a></li>
                </ul>
                <div class="user-menu">
                    <div class="user-info">
                        <div class="avatar">
                            <i class="fas fa-user-shield"></i>
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
                <h1>Admin Dashboard</h1>
                <p>Manage your travel platform efficiently</p>
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
                    <i class="fas fa-users"></i>
                    <h3>Total Users</h3>
                    <div class="value">${totalUsers}</div>
                </div>
                <div class="stat-card">
                    <i class="fas fa-plane"></i>
                    <h3>Total Flights</h3>
                    <div class="value">${totalFlights}</div>
                </div>
                <div class="stat-card">
                    <i class="fas fa-hotel"></i>
                    <h3>Total Hotels</h3>
                    <div class="value">${totalHotels}</div>
                </div>
                <div class="stat-card">
                    <i class="fas fa-suitcase"></i>
                    <h3>Total Bookings</h3>
                    <div class="value">${totalBookings}</div>
                </div>
                <div class="stat-card">
                    <i class="fas fa-dollar-sign"></i>
                    <h3>Total Revenue</h3>
                    <div class="value">$${totalRevenue}</div>
                </div>
                <div class="stat-card">
                    <i class="fas fa-clock"></i>
                    <h3>Pending Approvals</h3>
                    <div class="value">${pendingFlights + pendingHotels}</div>
                </div>
            </div>

            <div class="card mt-4">
                <div class="card-header">
                    <h2><i class="fas fa-tachometer-alt"></i> Quick Statistics</h2>
                </div>
                <div class="card-body">
                    <div class="d-flex justify-between" style="flex-wrap: wrap; gap: 2rem;">
                        <div class="stat-item">
                            <h3>Pending Flight Approvals</h3>
                            <div class="value">${pendingFlights}</div>
                            <a href="${pageContext.request.contextPath}/flights/pending" class="btn btn-primary btn-sm">
                                Review Now
                            </a>
                        </div>
                        <div class="stat-item">
                            <h3>Pending Hotel Approvals</h3>
                            <div class="value">${pendingHotels}</div>
                            <a href="${pageContext.request.contextPath}/admin/hotels?filter=pending" class="btn btn-primary btn-sm">
                                Review Now
                            </a>
                        </div>
                        <div class="stat-item">
                            <h3>Platform Revenue</h3>
                            <div class="value">$${totalRevenue}</div>
                            <span class="text-success">+12% this month</span>
                        </div>
                    </div>
                </div>
            </div>

            <div class="quick-actions mt-4">
                <h2 class="mb-3">Management Tools</h2>
                <div class="d-flex justify-between" style="gap: 1rem; flex-wrap: wrap;">
                    <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-primary">
                        <i class="fas fa-user-cog"></i> Manage Users
                    </a>
                    <a href="${pageContext.request.contextPath}/flights/pending" class="btn btn-warning">
                        <i class="fas fa-plane"></i> Review Flights
                    </a>
                    <a href="${pageContext.request.contextPath}/admin/hotels" class="btn btn-primary">
                        <i class="fas fa-hotel"></i> Manage Hotels
                    </a>
                    <a href="${pageContext.request.contextPath}/admin/bookings" class="btn btn-primary">
                        <i class="fas fa-chart-bar"></i> View Analytics
                    </a>
                </div>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/assets/js/app.js"></script>
</body>
</html>