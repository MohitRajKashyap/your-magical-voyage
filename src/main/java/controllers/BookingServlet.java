package controllers;

import services.BookingService;
import services.FlightService;
import models.FlightBooking;
import utils.PasswordUtil;
import factory.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/bookings/*")
public class BookingServlet extends HttpServlet {
    private BookingService bookingService;
    private FlightService flightService;

    @Override
    public void init() throws ServletException {
        ServiceFactory factory = ServiceFactory.getInstance();
        bookingService = factory.getBookingService();
        flightService = factory.getFlightService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();

        switch (path) {
            case "/flight":
                handleFlightBooking(request, response);
                break;
            case "/hotel":
                handleHotelBooking(request, response);
                break;
            case "/car":
                handleCarBooking(request, response);
                break;
            case "/cancel":
                handleCancelBooking(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleFlightBooking(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/views/login.jsp");
            return;
        }

        try {
            int flightId = Integer.parseInt(request.getParameter("flightId"));
            int passengers = Integer.parseInt(request.getParameter("passengers"));
            String specialRequests = request.getParameter("specialRequests");
            int userId = (Integer) session.getAttribute("userId");

            var flightOpt = flightService.getFlightById(flightId);
            if (flightOpt.isEmpty()) {
                session.setAttribute("error", "Flight not found");
                response.sendRedirect(request.getContextPath() + "/traveler/flights");
                return;
            }

            var flight = flightOpt.get();
            if (flight.getAvailableSeats() < passengers) {
                session.setAttribute("error", "Not enough seats available");
                response.sendRedirect(request.getContextPath() + "/traveler/flights");
                return;
            }

            FlightBooking booking = new FlightBooking();
            booking.setUserId(userId);
            booking.setFlightId(flightId);
            booking.setBookingReference(PasswordUtil.generateBookingReference());
            booking.setPassengersCount(passengers);
            booking.setTotalAmount(flight.getPrice() * passengers);
            booking.setBookingStatus("PENDING");
            booking.setPaymentStatus("PENDING");
            booking.setSpecialRequests(specialRequests);
            booking.setBookedAt(LocalDateTime.now());

            if (bookingService.bookFlight(booking)) {
                flightService.updateFlightSeats(flightId, passengers);
                session.setAttribute("success", "Flight booked successfully! Reference: " + booking.getBookingReference());
                response.sendRedirect(request.getContextPath() + "/traveler/bookings");
            } else {
                session.setAttribute("error", "Failed to book flight. Please try again.");
                response.sendRedirect(request.getContextPath() + "/traveler/flights");
            }

        } catch (Exception e) {
            session.setAttribute("error", "Invalid booking data: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/traveler/flights");
        }
    }

    private void handleHotelBooking(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Implementation for hotel booking
        HttpSession session = request.getSession();
        session.setAttribute("success", "Hotel booking feature coming soon!");
        response.sendRedirect(request.getContextPath() + "/traveler/hotels");
    }

    private void handleCarBooking(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Implementation for car booking
        HttpSession session = request.getSession();
        session.setAttribute("success", "Car rental booking feature coming soon!");
        response.sendRedirect(request.getContextPath() + "/traveler/cars");
    }

    private void handleCancelBooking(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String bookingId = request.getParameter("id");
        String type = request.getParameter("type");
        HttpSession session = request.getSession();

        if (bookingId != null && type != null) {
            try {
                boolean success = false;
                int id = Integer.parseInt(bookingId);

                switch (type) {
                    case "flight":
                        success = bookingService.updateFlightBookingStatus(id, "CANCELLED");
                        break;
                    case "hotel":
                        success = bookingService.updateHotelBookingStatus(id, "CANCELLED");
                        break;
                    case "car":
                        success = bookingService.updateCarRentalBookingStatus(id, "CANCELLED");
                        break;
                }

                if (success) {
                    session.setAttribute("success", "Booking cancelled successfully");
                } else {
                    session.setAttribute("error", "Failed to cancel booking");
                }
            } catch (NumberFormatException e) {
                session.setAttribute("error", "Invalid booking ID");
            }
        }

        response.sendRedirect(request.getContextPath() + "/traveler/bookings");
    }
}