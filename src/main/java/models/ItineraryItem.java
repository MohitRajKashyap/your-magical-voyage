package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class ItineraryItem {
    private int id;
    private int itineraryId;
    private String itemType;
    private String bookingReference;
    private LocalDate itemDate;
    private LocalTime itemTime;
    private String description;
    private String location;
    private double cost;

    public ItineraryItem() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getItineraryId() { return itineraryId; }
    public void setItineraryId(int itineraryId) { this.itineraryId = itineraryId; }

    public String getItemType() { return itemType; }
    public void setItemType(String itemType) { this.itemType = itemType; }

    public String getBookingReference() { return bookingReference; }
    public void setBookingReference(String bookingReference) { this.bookingReference = bookingReference; }

    public LocalDate getItemDate() { return itemDate; }
    public void setItemDate(LocalDate itemDate) { this.itemDate = itemDate; }

    public LocalTime getItemTime() { return itemTime; }
    public void setItemTime(LocalTime itemTime) { this.itemTime = itemTime; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }
}