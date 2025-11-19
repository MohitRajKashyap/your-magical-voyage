package controllers;

import services.BookingService;
import services.FlightService;
import services.HotelService;
import services.MessageService;
import factory.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/traveler/*")
public class TravelerDashboardServlet extends HttpServlet {
    private BookingService bookingService;
    private FlightService flightService;
    private HotelService hotelService;
    private MessageService messageService;

    @Override
    public void init() throws ServletException {
        ServiceFactory factory = ServiceFactory.getInstance();
        bookingService = factory.getBookingService();
        flightService = factory.getFlightService();
        hotelService = factory.getHotelService();
        messageService = factory.getMessageService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"TRAVELER".equals(session.getAttribute("userRole"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String path = request.getPathInfo();
        if (path == null) path = "/dashboard";

        switch (path) {
            case "/dashboard":
                showDashboard(request, response);
                break;
            case "/flights":
                showFlightBooking(request, response);
                break;
            case "/hotels":
                showHotelBooking(request, response);
                break;
            case "/cars":
                showCarBooking(request, response);
                break;
            case "/bookings":
                showMyBookings(request, response);
                break;
            case "/itineraries":
                showItineraries(request, response);
                break;
            case "/profile":
                showProfile(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = (Integer) request.getSession().getAttribute("userId");

        var flightBookings = bookingService.getUserFlightBookings(userId);
        var hotelBookings = bookingService.getUserHotelBookings(userId);
        var carBookings = bookingService.getUserCarRentalBookings(userId);
        int unreadMessages = messageService.getUnreadMessageCount(userId);

        request.setAttribute("flightBookings", flightBookings);
        request.setAttribute("hotelBookings", hotelBookings);
        request.setAttribute("carBookings", carBookings);
        request.setAttribute("unreadMessages", unreadMessages);
        request.setAttribute("totalBookings", flightBookings.size() + hotelBookings.size() + carBookings.size());

        request.getRequestDispatcher("/views/traveler/dashboard.jsp").forward(request, response);
    }

    private void showFlightBooking(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String origin = request.getParameter("origin");
        String destination = request.getParameter("destination");
        String departureDate = request.getParameter("departureDate");

        if (origin != null && destination != null && departureDate != null) {
            var flights = flightService.searchFlights(origin, destination, departureDate);
            request.setAttribute("flights", flights);
            request.setAttribute("searchParams", true);
        }

        request.getRequestDispatcher("/views/traveler/book-flights.jsp").forward(request, response);
    }

    private void showMyBookings(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = (Integer) request.getSession().getAttribute("userId");

        var flightBookings = bookingService.getUserFlightBookings(userId);
        var hotelBookings = bookingService.getUserHotelBookings(userId);
        var carBookings = bookingService.getUserCarRentalBookings(userId);

        request.setAttribute("flightBookings", flightBookings);
        request.setAttribute("hotelBookings", hotelBookings);
        request.setAttribute("carBookings", carBookings);

        request.getRequestDispatcher("/views/traveler/my-bookings.jsp").forward(request, response);
    }

    private void showHotelBooking(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Implementation for hotel booking
        request.getRequestDispatcher("/views/traveler/book-hotels.jsp").forward(request, response);
    }

    private void showCarBooking(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Implementation for car booking
        request.getRequestDispatcher("/views/traveler/book-cars.jsp").forward(request, response);
    }

    private void showItineraries(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Implementation for itineraries
        request.getRequestDispatcher("/views/traveler/itineraries.jsp").forward(request, response);
    }

    private void showProfile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Implementation for profile
        request.getRequestDispatcher("/views/traveler/profile.jsp").forward(request, response);
    }
}