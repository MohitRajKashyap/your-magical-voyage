package models;

import java.time.LocalDateTime;

public class Flight {
    private int id;
    private int airlineId;
    private String airlineName;
    private String flightNumber;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int durationMinutes;
    private int totalSeats;
    private int availableSeats;
    private double price;
    private String classType;
    private String status;
    private int createdBy;
    private boolean approved;
    private LocalDateTime createdAt;

    public Flight() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAirlineId() { return airlineId; }
    public void setAirlineId(int airlineId) { this.airlineId = airlineId; }

    public String getAirlineName() { return airlineName; }
    public void setAirlineName(String airlineName) { this.airlineName = airlineName; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }

    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public int getTotalSeats() { return totalSeats; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getClassType() { return classType; }
    public void setClassType(String classType) { this.classType = classType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }

    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getFormattedDuration() {
        int hours = durationMinutes / 60;
        int minutes = durationMinutes % 60;
        return hours + "h " + minutes + "m";
    }
}