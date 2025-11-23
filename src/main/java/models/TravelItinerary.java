package models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TravelItinerary {
    private int id;
    private int userId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalCost;
    private String status;
    private LocalDateTime createdAt;
    private List<ItineraryItem> items;
    private User user;

    public TravelItinerary() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<ItineraryItem> getItems() { return items; }
    public void setItems(List<ItineraryItem> items) { this.items = items; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public long getDurationDays() {
        if (startDate != null && endDate != null) {
            return java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
        }
        return 0;
    }
}