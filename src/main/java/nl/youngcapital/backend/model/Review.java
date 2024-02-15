package nl.youngcapital.backend.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, length = 50)
    private String Name;
    @Column(nullable = false)
    private double rating;
    @Column(length = 1000)
    private String comment;
    private LocalDateTime date;
    @ManyToOne
    private Account account;
    @ManyToOne
    private Hotel hotel;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @JsonIgnore
    public Account getAccount() {
        return account;
    }

    @JsonIgnore
    public void setAccount(Account account) {
        this.account = account;
    }

    @JsonIgnore
    public Hotel getHotel() {
        return hotel;
    }

    @JsonIgnore
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
