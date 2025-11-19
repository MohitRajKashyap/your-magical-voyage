package models;

import java.time.LocalDateTime;

public class FlightBooking {
    private int id;
    private int userId;
    private int flightId;
    private String bookingReference;
    private int passengersCount;
    private double totalAmount;
    private String bookingStatus;
    private String paymentStatus;
    private String specialRequests;
    private LocalDateTime bookedAt;
    private Flight flight;
    private User user;

    public FlightBooking() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getFlightId() { return flightId; }
    public void setFlightId(int flightId) { this.flightId = flightId; }

    public String getBookingReference() { return bookingReference; }
    public void setBookingReference(String bookingReference) { this.bookingReference = bookingReference; }

    public int getPassengersCount() { return passengersCount; }
    public void setPassengersCount(int passengersCount) { this.passengersCount = passengersCount; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getSpecialRequests() { return specialRequests; }
    public void setSpecialRequests(String specialRequests) { this.specialRequests = specialRequests; }

    public LocalDateTime getBookedAt() { return bookedAt; }
    public void setBookedAt(LocalDateTime bookedAt) { this.bookedAt = bookedAt; }

    public Flight getFlight() { return flight; }
    public void setFlight(Flight flight) { this.flight = flight; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}