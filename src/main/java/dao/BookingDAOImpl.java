package dao;

import config.DatabaseConfig;
import models.FlightBooking;
import models.HotelBooking;
import models.CarRentalBooking;
import models.Flight;
import models.Hotel;
import models.RentalCar;
import models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookingDAOImpl implements BookingDAO {

    @Override
    public boolean createFlightBooking(FlightBooking booking) {
        String sql = "INSERT INTO flight_bookings (user_id, flight_id, booking_reference, " +
                "passengers_count, total_amount, booking_status, payment_status, special_requests) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = DatabaseConfig.getInstance().getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, booking.getUserId());
                stmt.setInt(2, booking.getFlightId());
                stmt.setString(3, booking.getBookingReference());
                stmt.setInt(4, booking.getPassengersCount());
                stmt.setDouble(5, booking.getTotalAmount());
                stmt.setString(6, booking.getBookingStatus());
                stmt.setString(7, booking.getPaymentStatus());
                stmt.setString(8, booking.getSpecialRequests());

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            }
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error during rollback: " + ex.getMessage());
                }
            }
            System.err.println("Error creating flight booking: " + e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public Optional<FlightBooking> getFlightBookingById(int id) {
        String sql = "SELECT fb.*, f.*, u.name as user_name, u.email as user_email " +
                "FROM flight_bookings fb " +
                "JOIN flights f ON fb.flight_id = f.id " +
                "JOIN users u ON fb.user_id = u.id " +
                "WHERE fb.id = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToFlightBooking(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting flight booking by ID: " + e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<FlightBooking> getFlightBookingsByUser(int userId) {
        List<FlightBooking> bookings = new ArrayList<>();
        String sql = "SELECT fb.*, f.*, u.name as user_name " +
                "FROM flight_bookings fb " +
                "JOIN flights f ON fb.flight_id = f.id " +
                "JOIN users u ON fb.user_id = u.id " +
                "WHERE fb.user_id = ? ORDER BY fb.booked_at DESC";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bookings.add(mapResultSetToFlightBooking(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting flight bookings by user: " + e.getMessage());
        }

        return bookings;
    }

    @Override
    public List<FlightBooking> getAllFlightBookings() {
        List<FlightBooking> bookings = new ArrayList<>();
        String sql = "SELECT fb.*, f.*, u.name as user_name " +
                "FROM flight_bookings fb " +
                "JOIN flights f ON fb.flight_id = f.id " +
                "JOIN users u ON fb.user_id = u.id " +
                "ORDER BY fb.booked_at DESC";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                bookings.add(mapResultSetToFlightBooking(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting all flight bookings: " + e.getMessage());
        }

        return bookings;
    }

    @Override
    public boolean updateFlightBookingStatus(int bookingId, String status) {
        String sql = "UPDATE flight_bookings SET booking_status = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, bookingId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating flight booking status: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateFlightPaymentStatus(int bookingId, String paymentStatus) {
        String sql = "UPDATE flight_bookings SET payment_status = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paymentStatus);
            stmt.setInt(2, bookingId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating flight payment status: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<FlightBooking> getFlightBookingByReference(String reference) {
        String sql = "SELECT fb.*, f.*, u.name as user_name, u.email as user_email " +
                "FROM flight_bookings fb " +
                "JOIN flights f ON fb.flight_id = f.id " +
                "JOIN users u ON fb.user_id = u.id " +
                "WHERE fb.booking_reference = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, reference);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToFlightBooking(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting flight booking by reference: " + e.getMessage());
        }

        return Optional.empty();
    }

    // Hotel Booking Methods
    @Override
    public boolean createHotelBooking(HotelBooking booking) {
        String sql = "INSERT INTO hotel_bookings (user_id, hotel_id, booking_reference, " +
                "check_in_date, check_out_date, rooms_count, guests_count, total_amount, " +
                "booking_status, payment_status, special_requests) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getHotelId());
            stmt.setString(3, booking.getBookingReference());
            stmt.setDate(4, Date.valueOf(booking.getCheckInDate()));
            stmt.setDate(5, Date.valueOf(booking.getCheckOutDate()));
            stmt.setInt(6, booking.getRoomsCount());
            stmt.setInt(7, booking.getGuestsCount());
            stmt.setDouble(8, booking.getTotalAmount());
            stmt.setString(9, booking.getBookingStatus());
            stmt.setString(10, booking.getPaymentStatus());
            stmt.setString(11, booking.getSpecialRequests());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error creating hotel booking: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<HotelBooking> getHotelBookingById(int id) {
        String sql = "SELECT hb.*, h.*, u.name as user_name " +
                "FROM hotel_bookings hb " +
                "JOIN hotels h ON hb.hotel_id = h.id " +
                "JOIN users u ON hb.user_id = u.id " +
                "WHERE hb.id = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToHotelBooking(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting hotel booking by ID: " + e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public Optional<HotelBooking> getHotelBookingByReference(String reference) {
        String sql = "SELECT hb.*, h.*, u.name as user_name " +
                "FROM hotel_bookings hb " +
                "JOIN hotels h ON hb.hotel_id = h.id " +
                "JOIN users u ON hb.user_id = u.id " +
                "WHERE hb.booking_reference = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, reference);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToHotelBooking(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting hotel booking by reference: " + e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<HotelBooking> getHotelBookingsByUser(int userId) {
        List<HotelBooking> bookings = new ArrayList<>();
        String sql = "SELECT hb.*, h.*, u.name as user_name " +
                "FROM hotel_bookings hb " +
                "JOIN hotels h ON hb.hotel_id = h.id " +
                "JOIN users u ON hb.user_id = u.id " +
                "WHERE hb.user_id = ? ORDER BY hb.booked_at DESC";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bookings.add(mapResultSetToHotelBooking(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting hotel bookings by user: " + e.getMessage());
        }

        return bookings;
    }

    @Override
    public List<HotelBooking> getAllHotelBookings() {
        List<HotelBooking> bookings = new ArrayList<>();
        String sql = "SELECT hb.*, h.*, u.name as user_name " +
                "FROM hotel_bookings hb " +
                "JOIN hotels h ON hb.hotel_id = h.id " +
                "JOIN users u ON hb.user_id = u.id " +
                "ORDER BY hb.booked_at DESC";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                bookings.add(mapResultSetToHotelBooking(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting all hotel bookings: " + e.getMessage());
        }

        return bookings;
    }

    @Override
    public boolean updateHotelBookingStatus(int bookingId, String status) {
        String sql = "UPDATE hotel_bookings SET booking_status = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, bookingId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating hotel booking status: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateHotelPaymentStatus(int bookingId, String paymentStatus) {
        String sql = "UPDATE hotel_bookings SET payment_status = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paymentStatus);
            stmt.setInt(2, bookingId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating hotel payment status: " + e.getMessage());
            return false;
        }
    }

    // Car Rental Booking Methods
    @Override
    public boolean createCarRentalBooking(CarRentalBooking booking) {
        String sql = "INSERT INTO car_rental_bookings (user_id, rental_car_id, booking_reference, " +
                "pickup_date, dropoff_date, pickup_location, dropoff_location, total_amount, " +
                "booking_status, payment_status, special_requests) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, booking.getUserId());
            stmt.setInt(2, booking.getRentalCarId());
            stmt.setString(3, booking.getBookingReference());
            stmt.setDate(4, Date.valueOf(booking.getPickupDate()));
            stmt.setDate(5, Date.valueOf(booking.getDropoffDate()));
            stmt.setString(6, booking.getPickupLocation());
            stmt.setString(7, booking.getDropoffLocation());
            stmt.setDouble(8, booking.getTotalAmount());
            stmt.setString(9, booking.getBookingStatus());
            stmt.setString(10, booking.getPaymentStatus());
            stmt.setString(11, booking.getSpecialRequests());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error creating car rental booking: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<CarRentalBooking> getCarRentalBookingById(int id) {
        // Implementation similar to hotel/flight methods
        return Optional.empty();
    }

    @Override
    public Optional<CarRentalBooking> getCarRentalBookingByReference(String reference) {
        // Implementation similar to hotel/flight methods
        return Optional.empty();
    }

    @Override
    public List<CarRentalBooking> getCarRentalBookingsByUser(int userId) {
        // Implementation similar to hotel/flight methods
        return new ArrayList<>();
    }

    @Override
    public List<CarRentalBooking> getAllCarRentalBookings() {
        // Implementation similar to hotel/flight methods
        return new ArrayList<>();
    }

    @Override
    public boolean updateCarRentalBookingStatus(int bookingId, String status) {
        String sql = "UPDATE car_rental_bookings SET booking_status = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, bookingId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating car rental booking status: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateCarRentalPaymentStatus(int bookingId, String paymentStatus) {
        String sql = "UPDATE car_rental_bookings SET payment_status = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paymentStatus);
            stmt.setInt(2, bookingId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating car rental payment status: " + e.getMessage());
            return false;
        }
    }

    // Statistics Methods
    @Override
    public int getTotalBookingsCount() {
        String sql = "SELECT COUNT(*) as total FROM (" +
                "SELECT id FROM flight_bookings UNION ALL " +
                "SELECT id FROM hotel_bookings UNION ALL " +
                "SELECT id FROM car_rental_bookings) as all_bookings";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (SQLException e) {
            System.err.println("Error getting total bookings count: " + e.getMessage());
        }

        return 0;
    }

    @Override
    public double getTotalRevenue() {
        String sql = "SELECT SUM(total_amount) as revenue FROM (" +
                "SELECT total_amount FROM flight_bookings WHERE payment_status = 'PAID' UNION ALL " +
                "SELECT total_amount FROM hotel_bookings WHERE payment_status = 'PAID' UNION ALL " +
                "SELECT total_amount FROM car_rental_bookings WHERE payment_status = 'PAID') as paid_bookings";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble("revenue");
            }

        } catch (SQLException e) {
            System.err.println("Error getting total revenue: " + e.getMessage());
        }

        return 0.0;
    }

    @Override
    public int getPendingBookingsCount() {
        String sql = "SELECT COUNT(*) as pending FROM (" +
                "SELECT id FROM flight_bookings WHERE booking_status = 'PENDING' UNION ALL " +
                "SELECT id FROM hotel_bookings WHERE booking_status = 'PENDING' UNION ALL " +
                "SELECT id FROM car_rental_bookings WHERE booking_status = 'PENDING') as pending_bookings";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("pending");
            }

        } catch (SQLException e) {
            System.err.println("Error getting pending bookings count: " + e.getMessage());
        }

        return 0;
    }

    // Helper Methods for Mapping ResultSets
    private FlightBooking mapResultSetToFlightBooking(ResultSet rs) throws SQLException {
        FlightBooking booking = new FlightBooking();
        booking.setId(rs.getInt("id"));
        booking.setUserId(rs.getInt("user_id"));
        booking.setFlightId(rs.getInt("flight_id"));
        booking.setBookingReference(rs.getString("booking_reference"));
        booking.setPassengersCount(rs.getInt("passengers_count"));
        booking.setTotalAmount(rs.getDouble("total_amount"));
        booking.setBookingStatus(rs.getString("booking_status"));
        booking.setPaymentStatus(rs.getString("payment_status"));
        booking.setSpecialRequests(rs.getString("special_requests"));
        booking.setBookedAt(rs.getTimestamp("booked_at").toLocalDateTime());

        Flight flight = new Flight();
        flight.setId(rs.getInt("flight_id"));
        flight.setFlightNumber(rs.getString("flight_number"));
        flight.setOrigin(rs.getString("origin"));
        flight.setDestination(rs.getString("destination"));
        flight.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
        flight.setPrice(rs.getDouble("price"));
        booking.setFlight(flight);

        User user = new User();
        user.setName(rs.getString("user_name"));
        if (hasColumn(rs, "user_email")) {
            user.setEmail(rs.getString("user_email"));
        }
        booking.setUser(user);

        return booking;
    }

    private HotelBooking mapResultSetToHotelBooking(ResultSet rs) throws SQLException {
        HotelBooking booking = new HotelBooking();
        booking.setId(rs.getInt("id"));
        booking.setUserId(rs.getInt("user_id"));
        booking.setHotelId(rs.getInt("hotel_id"));
        booking.setBookingReference(rs.getString("booking_reference"));
        booking.setCheckInDate(rs.getDate("check_in_date").toLocalDate());
        booking.setCheckOutDate(rs.getDate("check_out_date").toLocalDate());
        booking.setRoomsCount(rs.getInt("rooms_count"));
        booking.setGuestsCount(rs.getInt("guests_count"));
        booking.setTotalAmount(rs.getDouble("total_amount"));
        booking.setBookingStatus(rs.getString("booking_status"));
        booking.setPaymentStatus(rs.getString("payment_status"));
        booking.setSpecialRequests(rs.getString("special_requests"));
        booking.setBookedAt(rs.getTimestamp("booked_at").toLocalDateTime());

        Hotel hotel = new Hotel();
        hotel.setId(rs.getInt("hotel_id"));
        hotel.setName(rs.getString("name"));
        hotel.setCity(rs.getString("city"));
        hotel.setCountry(rs.getString("country"));
        hotel.setPricePerNight(rs.getDouble("price_per_night"));
        booking.setHotel(hotel);

        User user = new User();
        user.setName(rs.getString("user_name"));
        booking.setUser(user);

        return booking;
    }

    // Utility method to check if ResultSet has a column
    private boolean hasColumn(ResultSet rs, String columnName) {
        try {
            rs.findColumn(columnName);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}