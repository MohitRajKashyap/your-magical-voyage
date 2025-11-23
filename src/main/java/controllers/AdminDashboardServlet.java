package controllers;

import services.UserService;
import services.FlightService;
import services.HotelService;
import services.BookingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/admin/*")
public class AdminDashboardServlet extends HttpServlet {
    private UserService userService;
    private FlightService flightService;
    private HotelService hotelService;
    private BookingService bookingService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
        flightService = new FlightService();
        hotelService = new HotelService();
        bookingService = new BookingService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("userRole"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String path = request.getPathInfo();
        if (path == null) path = "/dashboard";

        switch (path) {
            case "/dashboard":
                showDashboard(request, response);
                break;
            case "/users":
                showUserManagement(request, response);
                break;
            case "/flights":
                showFlightManagement(request, response);
                break;
            case "/hotels":
                showHotelManagement(request, response);
                break;
            case "/bookings":
                showBookingManagement(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int totalUsers = userService.getAllUsers().size();
        int totalFlights = flightService.getAllFlights().size();
        int totalHotels = hotelService.getAllHotels().size();
        int totalBookings = bookingService.getTotalBookingsCount();
        double totalRevenue = bookingService.getTotalRevenue();
        int pendingFlights = flightService.getPendingApprovalFlights().size();
        int pendingHotels = hotelService.getPendingApprovalHotels().size();

        request.setAttribute("totalUsers", totalUsers);
        request.setAttribute("totalFlights", totalFlights);
        request.setAttribute("totalHotels", totalHotels);
        request.setAttribute("totalBookings", totalBookings);
        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("pendingFlights", pendingFlights);
        request.setAttribute("pendingHotels", pendingHotels);

        request.getRequestDispatcher("/views/admin/dashboard.jsp").forward(request, response);
    }

    private void showUserManagement(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        var users = userService.getAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/views/admin/users.jsp").forward(request, response);
    }

    private void showFlightManagement(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        var flights = flightService.getAllFlights();
        request.setAttribute("flights", flights);
        request.getRequestDispatcher("/views/admin/flights.jsp").forward(request, response);
    }

    private void showHotelManagement(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        var hotels = hotelService.getAllHotels();
        request.setAttribute("hotels", hotels);
        request.getRequestDispatcher("/views/admin/hotels.jsp").forward(request, response);
    }

    private void showBookingManagement(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Implementation for booking management
        request.getRequestDispatcher("/views/admin/bookings.jsp").forward(request, response);
    }
}