package models;

import java.time.LocalDateTime;

public class RentalCar {
    private int id;
    private int companyId;
    private String companyName;
    private String model;
    private String brand;
    private String vehicleType;
    private int year;
    private String transmission;
    private String fuelType;
    private int seats;
    private int luggageCapacity;
    private double pricePerDay;
    private int availableUnits;
    private String location;
    private String features;
    private String imageUrl;
    private int createdBy;
    private boolean approved;
    private LocalDateTime createdAt;

    public RentalCar() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCompanyId() { return companyId; }
    public void setCompanyId(int companyId) { this.companyId = companyId; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getTransmission() { return transmission; }
    public void setTransmission(String transmission) { this.transmission = transmission; }

    public String getFuelType() { return fuelType; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }

    public int getSeats() { return seats; }
    public void setSeats(int seats) { this.seats = seats; }

    public int getLuggageCapacity() { return luggageCapacity; }
    public void setLuggageCapacity(int luggageCapacity) { this.luggageCapacity = luggageCapacity; }

    public double getPricePerDay() { return pricePerDay; }
    public void setPricePerDay(double pricePerDay) { this.pricePerDay = pricePerDay; }

    public int getAvailableUnits() { return availableUnits; }
    public void setAvailableUnits(int availableUnits) { this.availableUnits = availableUnits; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getFeatures() { return features; }
    public void setFeatures(String features) { this.features = features; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }

    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String[] getFeaturesList() {
        if (features == null || features.isEmpty()) {
            return new String[0];
        }
        return features.split(",");
    }
}