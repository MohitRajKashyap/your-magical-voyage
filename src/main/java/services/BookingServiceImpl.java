package services;

import dao.BookingDAO;
import dao.BookingDAOImpl;
import models.FlightBooking;
import models.HotelBooking;
import models.CarRentalBooking;
import java.util.List;
import java.util.Optional;

public class BookingServiceImpl implements BookingService {
    private BookingDAO bookingDAO;

    public BookingServiceImpl() {
        this.bookingDAO = new BookingDAOImpl();
    }

    @Override
    public boolean bookFlight(FlightBooking booking) {
        return bookingDAO.createFlightBooking(booking);
    }

    @Override
    public boolean bookHotel(HotelBooking booking) {
        return bookingDAO.createHotelBooking(booking);
    }

    @Override
    public boolean bookCarRental(CarRentalBooking booking) {
        return bookingDAO.createCarRentalBooking(booking);
    }

    @Override
    public Optional<FlightBooking> getFlightBookingById(int bookingId) {
        return bookingDAO.getFlightBookingById(bookingId);
    }

    @Override
    public Optional<FlightBooking> getFlightBookingByReference(String reference) {
        return bookingDAO.getFlightBookingByReference(reference);
    }

    @Override
    public List<FlightBooking> getUserFlightBookings(int userId) {
        return bookingDAO.getFlightBookingsByUser(userId);
    }

    @Override
    public List<FlightBooking> getAllFlightBookings() {
        return bookingDAO.getAllFlightBookings();
    }

    @Override
    public List<HotelBooking> getUserHotelBookings(int userId) {
        return bookingDAO.getHotelBookingsByUser(userId);
    }

    @Override
    public List<CarRentalBooking> getUserCarRentalBookings(int userId) {
        return bookingDAO.getCarRentalBookingsByUser(userId);
    }

    @Override
    public boolean updateFlightBookingStatus(int bookingId, String status) {
        return bookingDAO.updateFlightBookingStatus(bookingId, status);
    }

    @Override
    public boolean updateHotelBookingStatus(int bookingId, String status) {
        return bookingDAO.updateHotelBookingStatus(bookingId, status);
    }

    @Override
    public boolean updateCarRentalBookingStatus(int bookingId, String status) {
        return bookingDAO.updateCarRentalBookingStatus(bookingId, status);
    }

    @Override
    public int getTotalBookingsCount() {
        return bookingDAO.getTotalBookingsCount();
    }

    @Override
    public double getTotalRevenue() {
        return bookingDAO.getTotalRevenue();
    }

    @Override
    public int getPendingBookingsCount() {
        return bookingDAO.getPendingBookingsCount();
    }
}