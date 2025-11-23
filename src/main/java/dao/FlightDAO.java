package dao;

import models.Flight;
import java.util.List;
import java.util.Optional;

public interface FlightDAO {
    boolean createFlight(Flight flight);
    Optional<Flight> getFlightById(int id);
    List<Flight> getAllFlights();
    List<Flight> getFlightsByRoute(String origin, String destination);
    List<Flight> getFlightsByAirline(int airlineId);
    List<Flight> getPendingApprovalFlights();
    boolean updateFlight(Flight flight);
    boolean deleteFlight(int id);
    boolean approveFlight(int flightId);
    boolean updateFlightSeats(int flightId, int seatsBooked);
    List<Flight> searchFlights(String origin, String destination, String departureDate);
}