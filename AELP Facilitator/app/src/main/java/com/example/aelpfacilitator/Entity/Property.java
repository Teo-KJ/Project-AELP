package com.example.aelpfacilitator.Entity;

import java.io.Serializable;

public class Property implements Serializable {
    private String id, propertyName, colour, ownerID;
    private double price, rental;
    private boolean isPurchased;

    public Property() {}

    public Property(String id, String propertyName, String colour, double price, double rental, boolean isPurchased, String ownerID){
        this.id = id;
        this.propertyName = propertyName;
        this.colour = colour;
        this.price = price;
        this.rental = rental;
        this.isPurchased = isPurchased;
        this.ownerID = ownerID;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public double getRental() {
        return rental;
    }

    public void setRental(double rental) {
        this.rental = rental;
    }
}