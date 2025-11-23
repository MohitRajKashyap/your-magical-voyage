package models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HotelBooking {
    private int id;
    private int userId;
    private int hotelId;
    private String bookingReference;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int roomsCount;
    private int guestsCount;
    private double totalAmount;
    private String bookingStatus;
    private String paymentStatus;
    private String specialRequests;
    private LocalDateTime bookedAt;
    private Hotel hotel;
    private User user;

    public HotelBooking() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getHotelId() { return hotelId; }
    public void setHotelId(int hotelId) { this.hotelId = hotelId; }
    public String getBookingReference() { return bookingReference; }
    public void setBookingReference(String bookingReference) { this.bookingReference = bookingReference; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }
    public int getRoomsCount() { return roomsCount; }
    public void setRoomsCount(int roomsCount) { this.roomsCount = roomsCount; }
    public int getGuestsCount() { return guestsCount; }
    public void setGuestsCount(int guestsCount) { this.guestsCount = guestsCount; }
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
    public Hotel getHotel() { return hotel; }
    public void setHotel(Hotel hotel) { this.hotel = hotel; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public long getNights() {
        if (checkInDate != null && checkOutDate != null) {
            return java.time.temporal.ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        }
        return 0;
    }
}