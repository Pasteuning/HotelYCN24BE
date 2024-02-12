package nl.youngcapital.backend.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false,length = 80)
    private String name;
    @Column(nullable = false, length = 50)
    private String street;
    @Column(nullable = false,length = 30)
    private String houseNumber;
    @Column(nullable = false,length = 30)
    private String zipCode;
    @Column(nullable = false,length = 200)
    private String city;
    @Column(nullable = false,length = 80)
    private String country;
    @Column(length = 800)
    private String description;
    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms = new ArrayList<>();
    @OneToMany(mappedBy = "hotel")
    private List<Review> reviews = new ArrayList<>();



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> room) {
        this.rooms = room;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}


