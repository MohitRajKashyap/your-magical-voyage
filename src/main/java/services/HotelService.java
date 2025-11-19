package services;

import dao.HotelDAO;
import dao.HotelDAOImpl;
import models.Hotel;
import java.util.List;
import java.util.Optional;

public class HotelService {
    private HotelDAO hotelDAO;

    public HotelService() {
        this.hotelDAO = new HotelDAOImpl();
    }

    public boolean addHotel(Hotel hotel) {
        return hotelDAO.createHotel(hotel);
    }

    public Optional<Hotel> getHotelById(int hotelId) {
        return hotelDAO.getHotelById(hotelId);
    }

    public List<Hotel> getAllHotels() {
        return hotelDAO.getAllHotels();
    }

    public List<Hotel> searchHotels(String location, String checkInDate, String checkOutDate) {
        return hotelDAO.searchHotels(location, checkInDate, checkOutDate);
    }

    public List<Hotel> getPendingApprovalHotels() {
        return hotelDAO.getPendingApprovalHotels();
    }

    public boolean approveHotel(int hotelId) {
        return hotelDAO.approveHotel(hotelId);
    }

    public boolean updateHotel(Hotel hotel) {
        return hotelDAO.updateHotel(hotel);
    }

    public boolean deleteHotel(int hotelId) {
        return hotelDAO.deleteHotel(hotelId);
    }

    public boolean updateHotelRooms(int hotelId, int roomsBooked) {
        return hotelDAO.updateHotelRooms(hotelId, roomsBooked);
    }

    public List<Hotel> getHotelsByLocation(String city, String country) {
        return hotelDAO.getHotelsByLocation(city, country);
    }
}