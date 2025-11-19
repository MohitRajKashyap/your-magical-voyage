package dao;

import config.DatabaseConfig;
import models.Hotel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HotelDAOImpl implements HotelDAO {

    @Override
    public boolean createHotel(Hotel hotel) {
        String sql = "INSERT INTO hotels (name, chain_name, description, address, city, country, " +
                "star_rating, contact_email, contact_phone, total_rooms, available_rooms, " +
                "price_per_night, amenities, images_url, created_by) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hotel.getName());
            stmt.setString(2, hotel.getChainName());
            stmt.setString(3, hotel.getDescription());
            stmt.setString(4, hotel.getAddress());
            stmt.setString(5, hotel.getCity());
            stmt.setString(6, hotel.getCountry());
            stmt.setInt(7, hotel.getStarRating());
            stmt.setString(8, hotel.getContactEmail());
            stmt.setString(9, hotel.getContactPhone());
            stmt.setInt(10, hotel.getTotalRooms());
            stmt.setInt(11, hotel.getAvailableRooms());
            stmt.setDouble(12, hotel.getPricePerNight());
            stmt.setString(13, hotel.getAmenities());
            stmt.setString(14, hotel.getImagesUrl());
            stmt.setInt(15, hotel.getCreatedBy());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error creating hotel: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Optional<Hotel> getHotelById(int id) {
        String sql = "SELECT * FROM hotels WHERE id = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToHotel(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting hotel by ID: " + e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<Hotel> getAllHotels() {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT * FROM hotels ORDER BY created_at DESC";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                hotels.add(mapResultSetToHotel(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting all hotels: " + e.getMessage());
        }

        return hotels;
    }

    @Override
    public List<Hotel> getHotelsByLocation(String city, String country) {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT * FROM hotels WHERE city LIKE ? AND country LIKE ? AND approved = TRUE " +
                "ORDER BY star_rating DESC, price_per_night ASC";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + city + "%");
            stmt.setString(2, "%" + country + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                hotels.add(mapResultSetToHotel(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting hotels by location: " + e.getMessage());
        }

        return hotels;
    }

    @Override
    public List<Hotel> getPendingApprovalHotels() {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT * FROM hotels WHERE approved = FALSE ORDER BY created_at DESC";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                hotels.add(mapResultSetToHotel(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error getting pending approval hotels: " + e.getMessage());
        }

        return hotels;
    }

    @Override
    public boolean updateHotel(Hotel hotel) {
        String sql = "UPDATE hotels SET name = ?, chain_name = ?, description = ?, address = ?, " +
                "city = ?, country = ?, star_rating = ?, contact_email = ?, contact_phone = ?, " +
                "total_rooms = ?, available_rooms = ?, price_per_night = ?, amenities = ?, " +
                "images_url = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hotel.getName());
            stmt.setString(2, hotel.getChainName());
            stmt.setString(3, hotel.getDescription());
            stmt.setString(4, hotel.getAddress());
            stmt.setString(5, hotel.getCity());
            stmt.setString(6, hotel.getCountry());
            stmt.setInt(7, hotel.getStarRating());
            stmt.setString(8, hotel.getContactEmail());
            stmt.setString(9, hotel.getContactPhone());
            stmt.setInt(10, hotel.getTotalRooms());
            stmt.setInt(11, hotel.getAvailableRooms());
            stmt.setDouble(12, hotel.getPricePerNight());
            stmt.setString(13, hotel.getAmenities());
            stmt.setString(14, hotel.getImagesUrl());
            stmt.setInt(15, hotel.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating hotel: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteHotel(int id) {
        String sql = "DELETE FROM hotels WHERE id = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting hotel: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean approveHotel(int hotelId) {
        String sql = "UPDATE hotels SET approved = TRUE WHERE id = ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, hotelId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error approving hotel: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateHotelRooms(int hotelId, int roomsBooked) {
        String sql = "UPDATE hotels SET available_rooms = available_rooms - ? WHERE id = ? AND available_rooms >= ?";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, roomsBooked);
            stmt.setInt(2, hotelId);
            stmt.setInt(3, roomsBooked);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating hotel rooms: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Hotel> searchHotels(String location, String checkInDate, String checkOutDate) {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT * FROM hotels WHERE (city LIKE ? OR country LIKE ?) " +
                "AND approved = TRUE AND available_rooms > 0 " +
                "ORDER BY star_rating DESC, price_per_night ASC";

        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + location + "%");
            stmt.setString(2, "%" + location + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                hotels.add(mapResultSetToHotel(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error searching hotels: " + e.getMessage());
        }

        return hotels;
    }

    private Hotel mapResultSetToHotel(ResultSet rs) throws SQLException {
        Hotel hotel = new Hotel();
        hotel.setId(rs.getInt("id"));
        hotel.setName(rs.getString("name"));
        hotel.setChainName(rs.getString("chain_name"));
        hotel.setDescription(rs.getString("description"));
        hotel.setAddress(rs.getString("address"));
        hotel.setCity(rs.getString("city"));
        hotel.setCountry(rs.getString("country"));
        hotel.setStarRating(rs.getInt("star_rating"));
        hotel.setContactEmail(rs.getString("contact_email"));
        hotel.setContactPhone(rs.getString("contact_phone"));
        hotel.setTotalRooms(rs.getInt("total_rooms"));
        hotel.setAvailableRooms(rs.getInt("available_rooms"));
        hotel.setPricePerNight(rs.getDouble("price_per_night"));
        hotel.setAmenities(rs.getString("amenities"));
        hotel.setImagesUrl(rs.getString("images_url"));
        hotel.setCreatedBy(rs.getInt("created_by"));
        hotel.setApproved(rs.getBoolean("approved"));
        hotel.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return hotel;
    }
}