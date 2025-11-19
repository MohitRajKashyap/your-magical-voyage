package dao;

import config.DatabaseConfig;
import models.Flight;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDAOImpl implements FlightDAO {

    @Override
    public boolean createFlight(Flight flight) {
        String sql = "INSERT INTO flights (airline_id, flight_number, origin, destination, departure_time, " +
                "arrival_time, duration_minutes, total_seats, available_seats, price, class_type, status, created_by) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, flight.getAirlineId());
            stmt.setString(2, flight.getFlightNumber());
            stmt.setString(3, flight.getOrigin());
            stmt.setString(4, flight.getDestination());
            stmt.setTimestamp(5, Timestamp.valueOf(flight.getDepartureTime()));
            stmt.setTimestamp(6, Timestamp.valueOf(flight.getArrivalTime()));
            stmt.setInt(7, flight.getDurationMinutes());
            stmt.setInt(8, flight.getTotalSeats());
            stmt.setInt(9, flight.getAvailableSeats());
            stmt.setDouble(10, flight.getPrice());
            stmt.setString(11, flight.getClassType());
            stmt.setString(12, flight.getStatus());
            stmt.setInt(13, flight.getCreatedBy());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error creating flight: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<Flight> getFlightById(int id) {
        String sql = "SELECT f.*, a.name as airline_name FROM flights f " +
                "LEFT JOIN airlines a ON f.airline_id = a.id WHERE f.id = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToFlight(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting flight by ID: " + e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<Flight> getAllFlights() {
        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT f.*, a.name as airline_name FROM flights f " +
                "LEFT JOIN airlines a ON f.airline_id = a.id ORDER BY f.departure_time DESC";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                flights.add(mapResultSetToFlight(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting all flights: " + e.getMessage());
        }

        return flights;
    }

    @Override
    public List<Flight> getFlightsByRoute(String origin, String destination) {
        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT f.*, a.name as airline_name FROM flights f " +
                "LEFT JOIN airlines a ON f.airline_id = a.id " +
                "WHERE f.origin LIKE ? AND f.destination LIKE ? AND f.approved = TRUE " +
                "ORDER BY f.departure_time";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + origin + "%");
            stmt.setString(2, "%" + destination + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                flights.add(mapResultSetToFlight(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting flights by route: " + e.getMessage());
        }

        return flights;
    }

    @Override
    public List<Flight> getPendingApprovalFlights() {
        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT f.*, a.name as airline_name FROM flights f " +
                "LEFT JOIN airlines a ON f.airline_id = a.id " +
                "WHERE f.approved = FALSE ORDER BY f.created_at DESC";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                flights.add(mapResultSetToFlight(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting pending approval flights: " + e.getMessage());
        }

        return flights;
    }

    @Override
    public boolean updateFlight(Flight flight) {
        String sql = "UPDATE flights SET airline_id = ?, flight_number = ?, origin = ?, destination = ?, " +
                "departure_time = ?, arrival_time = ?, duration_minutes = ?, total_seats = ?, " +
                "available_seats = ?, price = ?, class_type = ?, status = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, flight.getAirlineId());
            stmt.setString(2, flight.getFlightNumber());
            stmt.setString(3, flight.getOrigin());
            stmt.setString(4, flight.getDestination());
            stmt.setTimestamp(5, Timestamp.valueOf(flight.getDepartureTime()));
            stmt.setTimestamp(6, Timestamp.valueOf(flight.getArrivalTime()));
            stmt.setInt(7, flight.getDurationMinutes());
            stmt.setInt(8, flight.getTotalSeats());
            stmt.setInt(9, flight.getAvailableSeats());
            stmt.setDouble(10, flight.getPrice());
            stmt.setString(11, flight.getClassType());
            stmt.setString(12, flight.getStatus());
            stmt.setInt(13, flight.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating flight: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteFlight(int id) {
        String sql = "DELETE FROM flights WHERE id = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting flight: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean approveFlight(int flightId) {
        String sql = "UPDATE flights SET approved = TRUE WHERE id = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, flightId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error approving flight: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateFlightSeats(int flightId, int seatsBooked) {
        String sql = "UPDATE flights SET available_seats = available_seats - ? WHERE id = ? AND available_seats >= ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, seatsBooked);
            stmt.setInt(2, flightId);
            stmt.setInt(3, seatsBooked);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating flight seats: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Flight> searchFlights(String origin, String destination, String departureDate) {
        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT f.*, a.name as airline_name FROM flights f " +
                "LEFT JOIN airlines a ON f.airline_id = a.id " +
                "WHERE f.origin LIKE ? AND f.destination LIKE ? AND DATE(f.departure_time) = ? " +
                "AND f.approved = TRUE AND f.available_seats > 0 " +
                "ORDER BY f.price, f.departure_time";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + origin + "%");
            stmt.setString(2, "%" + destination + "%");
            stmt.setString(3, departureDate);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                flights.add(mapResultSetToFlight(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error searching flights: " + e.getMessage());
        }

        return flights;
    }

    private Flight mapResultSetToFlight(ResultSet rs) throws SQLException {
        Flight flight = new Flight();
        flight.setId(rs.getInt("id"));
        flight.setAirlineId(rs.getInt("airline_id"));
        flight.setAirlineName(rs.getString("airline_name"));
        flight.setFlightNumber(rs.getString("flight_number"));
        flight.setOrigin(rs.getString("origin"));
        flight.setDestination(rs.getString("destination"));
        flight.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
        flight.setArrivalTime(rs.getTimestamp("arrival_time").toLocalDateTime());
        flight.setDurationMinutes(rs.getInt("duration_minutes"));
        flight.setTotalSeats(rs.getInt("total_seats"));
        flight.setAvailableSeats(rs.getInt("available_seats"));
        flight.setPrice(rs.getDouble("price"));
        flight.setClassType(rs.getString("class_type"));
        flight.setStatus(rs.getString("status"));
        flight.setCreatedBy(rs.getInt("created_by"));
        flight.setApproved(rs.getBoolean("approved"));
        flight.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return flight;
    }
}