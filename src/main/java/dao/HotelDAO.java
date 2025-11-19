package dao;

import models.Hotel;
import java.util.List;
import java.util.Optional;

public interface HotelDAO {
    boolean createHotel(Hotel hotel);
    Optional<Hotel> getHotelById(int id);
    List<Hotel> getAllHotels();
    List<Hotel> getHotelsByLocation(String city, String country);
    List<Hotel> getPendingApprovalHotels();
    boolean updateHotel(Hotel hotel);
    boolean deleteHotel(int id);
    boolean approveHotel(int hotelId);
    boolean updateHotelRooms(int hotelId, int roomsBooked);
    List<Hotel> searchHotels(String location, String checkInDate, String checkOutDate);
}