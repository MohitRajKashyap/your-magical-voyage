package controllers;

import services.FlightService;
import models.Flight;
import utils.DateUtil;
import utils.ValidationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/flights/*")
public class FlightServlet extends HttpServlet {
    private FlightService flightService;

    @Override
    public void init() throws ServletException {
        flightService = new FlightService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();

        if (path == null) path = "/";

        switch (path) {
            case "/":
            case "/search":
                handleSearchFlights(request, response);
                break;
            case "/manage":
                handleManageFlights(request, response);
                break;
            case "/pending":
                handlePendingFlights(request, response);
                break;
            case "/details":
                handleFlightDetails(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();

        switch (path) {
            case "/add":
                handleAddFlight(request, response);
                break;
            case "/update":
                handleUpdateFlight(request, response);
                break;
            case "/delete":
                handleDeleteFlight(request, response);
                break;
            case "/approve":
                handleApproveFlight(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleSearchFlights(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String origin = request.getParameter("origin");
        String destination = request.getParameter("destination");
        String departureDate = request.getParameter("departureDate");

        if (origin != null && destination != null && departureDate != null) {
            var flights = flightService.searchFlights(origin, destination, departureDate);
            request.setAttribute("flights", flights);
            request.setAttribute("searchOrigin", origin);
            request.setAttribute("searchDestination", destination);
            request.setAttribute("searchDate", departureDate);
        }

        request.getRequestDispatcher("/views/flights/search.jsp").forward(request, response);
    }

    private void handleManageFlights(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"TRAVEL_AGENT".equals(session.getAttribute("userRole"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        var flights = flightService.getAllFlights();
        request.setAttribute("flights", flights);
        request.getRequestDispatcher("/views/flights/manage.jsp").forward(request, response);
    }

    private void handlePendingFlights(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("userRole"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        var pendingFlights = flightService.getPendingApprovalFlights();
        request.setAttribute("pendingFlights", pendingFlights);
        request.getRequestDispatcher("/views/admin/pending-flights.jsp").forward(request, response);
    }

    private void handleFlightDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String flightId = request.getParameter("id");
        if (flightId != null && ValidationUtil.isValidSeats(Integer.parseInt(flightId))) {
            var flightOpt = flightService.getFlightById(Integer.parseInt(flightId));
            if (flightOpt.isPresent()) {
                request.setAttribute("flight", flightOpt.get());
                request.getRequestDispatcher("/views/flights/details.jsp").forward(request, response);
                return;
            }
        }
        response.sendRedirect(request.getContextPath() + "/flights/search");
    }

    private void handleAddFlight(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"TRAVEL_AGENT".equals(session.getAttribute("userRole"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            Flight flight = new Flight();
            flight.setAirlineId(Integer.parseInt(request.getParameter("airlineId")));
            flight.setFlightNumber(request.getParameter("flightNumber"));
            flight.setOrigin(request.getParameter("origin"));
            flight.setDestination(request.getParameter("destination"));
            flight.setDepartureTime(LocalDateTime.parse(request.getParameter("departureTime")));
            flight.setArrivalTime(LocalDateTime.parse(request.getParameter("arrivalTime")));
            flight.setDurationMinutes(Integer.parseInt(request.getParameter("duration")));
            flight.setTotalSeats(Integer.parseInt(request.getParameter("totalSeats")));
            flight.setAvailableSeats(Integer.parseInt(request.getParameter("totalSeats")));
            flight.setPrice(Double.parseDouble(request.getParameter("price")));
            flight.setClassType(request.getParameter("classType"));
            flight.setStatus("SCHEDULED");
            flight.setCreatedBy((Integer) session.getAttribute("userId"));

            if (flightService.addFlight(flight)) {
                session.setAttribute("success", "Flight added successfully and pending approval");
            } else {
                session.setAttribute("error", "Failed to add flight");
            }
        } catch (Exception e) {
            session.setAttribute("error", "Invalid flight data: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/flights/manage");
    }

    private void handleApproveFlight(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("userRole"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String flightId = request.getParameter("id");
        if (flightId != null) {
            if (flightService.approveFlight(Integer.parseInt(flightId))) {
                session.setAttribute("success", "Flight approved successfully");
            } else {
                session.setAttribute("error", "Failed to approve flight");
            }
        }
        response.sendRedirect(request.getContextPath() + "/flights/pending");
    }

    private void handleUpdateFlight(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"TRAVEL_AGENT".equals(session.getAttribute("userRole"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // Implementation for updating flight
        session.setAttribute("success", "Flight updated successfully");
        response.sendRedirect(request.getContextPath() + "/flights/manage");
    }

    private void handleDeleteFlight(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"TRAVEL_AGENT".equals(session.getAttribute("userRole"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String flightId = request.getParameter("id");
        if (flightId != null) {
            if (flightService.deleteFlight(Integer.parseInt(flightId))) {
                session.setAttribute("success", "Flight deleted successfully");
            } else {
                session.setAttribute("error", "Failed to delete flight");
            }
        }
        response.sendRedirect(request.getContextPath() + "/flights/manage");
    }
}