package services;

import dao.FlightDAO;
import dao.FlightDAOImpl;
import models.Flight;
import java.util.List;
import java.util.Optional;

public class FlightService {
    private FlightDAO flightDAO;

    public FlightService() {
        this.flightDAO = new FlightDAOImpl();
    }

    public boolean addFlight(Flight flight) {
        return flightDAO.createFlight(flight);
    }

    public Optional<Flight> getFlightById(int flightId) {
        return flightDAO.getFlightById(flightId);
    }

    public List<Flight> getAllFlights() {
        return flightDAO.getAllFlights();
    }

    public List<Flight> searchFlights(String origin, String destination, String departureDate) {
        return flightDAO.searchFlights(origin, destination, departureDate);
    }

    public List<Flight> getPendingApprovalFlights() {
        return flightDAO.getPendingApprovalFlights();
    }

    public boolean approveFlight(int flightId) {
        return flightDAO.approveFlight(flightId);
    }

    public boolean updateFlight(Flight flight) {
        return flightDAO.updateFlight(flight);
    }

    public boolean deleteFlight(int flightId) {
        return flightDAO.deleteFlight(flightId);
    }

    public boolean updateFlightSeats(int flightId, int seatsBooked) {
        return flightDAO.updateFlightSeats(flightId, seatsBooked);
    }

    public List<Flight> getFlightsByRoute(String origin, String destination) {
        return flightDAO.getFlightsByRoute(origin, destination);
    }
}