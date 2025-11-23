package models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CarRentalBooking {
    private int id;
    private int userId;
    private int rentalCarId;
    private String bookingReference;
    private LocalDate pickupDate;
    private LocalDate dropoffDate;
    private String pickupLocation;
    private String dropoffLocation;
    private double totalAmount;
    private String bookingStatus;
    private String paymentStatus;
    private String specialRequests;
    private LocalDateTime bookedAt;
    private RentalCar rentalCar;
    private User user;

    public CarRentalBooking() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getRentalCarId() { return rentalCarId; }
    public void setRentalCarId(int rentalCarId) { this.rentalCarId = rentalCarId; }
    public String getBookingReference() { return bookingReference; }
    public void setBookingReference(String bookingReference) { this.bookingReference = bookingReference; }
    public LocalDate getPickupDate() { return pickupDate; }
    public void setPickupDate(LocalDate pickupDate) { this.pickupDate = pickupDate; }
    public LocalDate getDropoffDate() { return dropoffDate; }
    public void setDropoffDate(LocalDate dropoffDate) { this.dropoffDate = dropoffDate; }
    public String getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }
    public String getDropoffLocation() { return dropoffLocation; }
    public void setDropoffLocation(String dropoffLocation) { this.dropoffLocation = dropoffLocation; }
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
    public RentalCar getRentalCar() { return rentalCar; }
    public void setRentalCar(RentalCar rentalCar) { this.rentalCar = rentalCar; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public long getRentalDays() {
        if (pickupDate != null && dropoffDate != null) {
            return java.time.temporal.ChronoUnit.DAYS.between(pickupDate, dropoffDate);
        }
        return 0;
    }
}