package services;

import models.FlightBooking;
import models.HotelBooking;
import models.CarRentalBooking;
import java.util.List;
import java.util.Optional;

public interface BookingService {
    boolean bookFlight(FlightBooking booking);
    boolean bookHotel(HotelBooking booking);
    boolean bookCarRental(CarRentalBooking booking);

    Optional<FlightBooking> getFlightBookingById(int bookingId);
    Optional<FlightBooking> getFlightBookingByReference(String reference);
    List<FlightBooking> getUserFlightBookings(int userId);
    List<FlightBooking> getAllFlightBookings(); // ADD THIS METHOD

    List<HotelBooking> getUserHotelBookings(int userId);
    List<CarRentalBooking> getUserCarRentalBookings(int userId);

    boolean updateFlightBookingStatus(int bookingId, String status);
    boolean updateHotelBookingStatus(int bookingId, String status);
    boolean updateCarRentalBookingStatus(int bookingId, String status);

    int getTotalBookingsCount();
    double getTotalRevenue();
    int getPendingBookingsCount();
}