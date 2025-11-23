package controllers;

import services.FlightService;
import services.HotelService;
import services.BookingService;
import services.MessageService;
import factory.ServiceFactory;
import models.Flight;
import models.Hotel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/agent/*")
public class TravelAgentDashboardServlet extends HttpServlet {
    private FlightService flightService;
    private HotelService hotelService;
    private BookingService bookingService;
    private MessageService messageService;

    @Override
    public void init() throws ServletException {
        ServiceFactory factory = ServiceFactory.getInstance();
        flightService = factory.getFlightService();
        hotelService = factory.getHotelService();
        bookingService = factory.getBookingService();
        messageService = factory.getMessageService(); // FIXED: Correct method name
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"TRAVEL_AGENT".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp");
            return;
        }

        String path = request.getPathInfo();
        if (path == null) path = "/dashboard";

        try {
            switch (path) {
                case "/dashboard":
                    showDashboard(request, response);
                    break;
                case "/flights":
                    showFlightManagement(request, response);
                    break;
                case "/hotels":
                    showHotelManagement(request, response);
                    break;
                case "/cars":
                    showCarManagement(request, response);
                    break;
                case "/bookings":
                    showBookingManagement(request, response);
                    break;
                case "/messages":
                    showMessages(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error: " + e.getMessage());
        }
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int userId = (Integer) request.getSession().getAttribute("userId");

            // Get flights created by this agent
            List<Flight> allFlights = flightService.getAllFlights();
            List<Flight> myFlights = new ArrayList<>();
            List<Flight> pendingFlights = new ArrayList<>();

            for (Flight flight : allFlights) {
                if (flight.getCreatedBy() == userId) {
                    myFlights.add(flight);
                    if (!flight.isApproved()) {
                        pendingFlights.add(flight);
                    }
                }
            }

            // Get hotels created by this agent
            List<Hotel> allHotels = hotelService.getAllHotels();
            List<Hotel> myHotels = new ArrayList<>();
            List<Hotel> pendingHotels = new ArrayList<>();

            for (Hotel hotel : allHotels) {
                if (hotel.getCreatedBy() == userId) {
                    myHotels.add(hotel);
                    if (!hotel.isApproved()) {
                        pendingHotels.add(hotel);
                    }
                }
            }

            int unreadMessages = messageService.getUnreadMessageCount(userId);

            request.setAttribute("myFlights", myFlights.size());
            request.setAttribute("myHotels", myHotels.size());
            request.setAttribute("pendingFlights", pendingFlights.size());
            request.setAttribute("pendingHotels", pendingHotels.size());
            request.setAttribute("unreadMessages", unreadMessages);

            request.getRequestDispatcher("/views/agent/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading dashboard: " + e.getMessage());
            request.getRequestDispatcher("/views/agent/dashboard.jsp").forward(request, response);
        }
    }

    private void showFlightManagement(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int userId = (Integer) request.getSession().getAttribute("userId");
            List<Flight> allFlights = flightService.getAllFlights();
            List<Flight> myFlights = new ArrayList<>();

            for (Flight flight : allFlights) {
                if (flight.getCreatedBy() == userId) {
                    myFlights.add(flight);
                }
            }

            request.setAttribute("flights", myFlights);
            request.getRequestDispatcher("/views/agent/manage-flights.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading flights: " + e.getMessage());
            request.getRequestDispatcher("/views/agent/manage-flights.jsp").forward(request, response);
        }
    }

    private void showBookingManagement(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            var flightBookings = bookingService.getAllFlightBookings();
            request.setAttribute("flightBookings", flightBookings);
            request.getRequestDispatcher("/views/agent/manage-bookings.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading bookings: " + e.getMessage());
            request.getRequestDispatcher("/views/agent/manage-bookings.jsp").forward(request, response);
        }
    }

    private void showHotelManagement(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int userId = (Integer) request.getSession().getAttribute("userId");
            List<Hotel> allHotels = hotelService.getAllHotels();
            List<Hotel> myHotels = new ArrayList<>();

            for (Hotel hotel : allHotels) {
                if (hotel.getCreatedBy() == userId) {
                    myHotels.add(hotel);
                }
            }

            request.setAttribute("hotels", myHotels);
            request.getRequestDispatcher("/views/agent/manage-hotels.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading hotels: " + e.getMessage());
            request.getRequestDispatcher("/views/agent/manage-hotels.jsp").forward(request, response);
        }
    }

    private void showCarManagement(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("message", "Car management feature coming soon!");
        request.getRequestDispatcher("/views/agent/manage-cars.jsp").forward(request, response);
    }

    private void showMessages(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int userId = (Integer) request.getSession().getAttribute("userId");
            var messages = messageService.getMessagesForUser(userId);
            request.setAttribute("messages", messages);
            request.getRequestDispatcher("/views/agent/messages.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading messages: " + e.getMessage());
            request.getRequestDispatcher("/views/agent/messages.jsp").forward(request, response);
        }
    }
}