package dao;

import models.FlightBooking;
import models.HotelBooking;
import models.CarRentalBooking;
import java.util.List;
import java.util.Optional;

public interface BookingDAO {
    // Flight Bookings
    boolean createFlightBooking(FlightBooking booking);
    Optional<FlightBooking> getFlightBookingById(int id);
    Optional<FlightBooking> getFlightBookingByReference(String reference);
    List<FlightBooking> getFlightBookingsByUser(int userId);
    List<FlightBooking> getAllFlightBookings(); // ADD THIS METHOD
    boolean updateFlightBookingStatus(int bookingId, String status);
    boolean updateFlightPaymentStatus(int bookingId, String paymentStatus);

    // Hotel Bookings
    boolean createHotelBooking(HotelBooking booking);
    Optional<HotelBooking> getHotelBookingById(int id);
    Optional<HotelBooking> getHotelBookingByReference(String reference);
    List<HotelBooking> getHotelBookingsByUser(int userId);
    List<HotelBooking> getAllHotelBookings();
    boolean updateHotelBookingStatus(int bookingId, String status);
    boolean updateHotelPaymentStatus(int bookingId, String paymentStatus);

    // Car Rental Bookings
    boolean createCarRentalBooking(CarRentalBooking booking);
    Optional<CarRentalBooking> getCarRentalBookingById(int id);
    Optional<CarRentalBooking> getCarRentalBookingByReference(String reference);
    List<CarRentalBooking> getCarRentalBookingsByUser(int userId);
    List<CarRentalBooking> getAllCarRentalBookings();
    boolean updateCarRentalBookingStatus(int bookingId, String status);
    boolean updateCarRentalPaymentStatus(int bookingId, String paymentStatus);

    // Statistics
    int getTotalBookingsCount();
    double getTotalRevenue();
    int getPendingBookingsCount();
}