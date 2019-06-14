package net.example.data.model;

import java.util.ArrayList;
import java.util.List;

public class Apartment {
    private int id;
    private String name;
    private ApartmentType type;
    private int numberOfPlaces;
    private String description;
    private String address;
    private User owner;
    private List<FileData> images = new ArrayList<>();
    private List<Facility> facilities = new ArrayList<>();
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ApartmentType getType() {
        return type;
    }

    public void setType(ApartmentType type) {
        this.type = type;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public void setNumberOfPlaces(int numberOfPlaces) {
        this.numberOfPlaces = numberOfPlaces;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FileData> getImages() {
        return images;
    }

    public void setImages(List<FileData> images) {
        this.images = images;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Facility> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<Facility> facilities) {
        this.facilities = facilities;
    }
}
