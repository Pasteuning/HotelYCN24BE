package nl.youngcapital.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.awt.print.Book;
import java.time.LocalDate;

@Entity
public class Reservation {
    public enum Status { RESERVED, BOOKED, CANCELLED }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private LocalDate ciDate;
    @Column(nullable = false)
    private LocalDate coDate;
    @Column(nullable = false, length = 2)
    private int adults;
    @Column(nullable = false, length = 2)
    private int children;
    @Column(nullable = false)
    private boolean surcharge;
    @Column(length = 500)
    private String specialRequest;
    private Status status = Status.RESERVED;
    @ManyToOne
    private Room room;
    @ManyToOne
    private User user;
    @OneToOne
    private Booking booking;




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getCiDate() {
        return ciDate;
    }

    public void setCiDate(LocalDate ciDate) {
        this.ciDate = ciDate;
    }

    public LocalDate getCoDate() {
        return coDate;
    }

    public void setCoDate(LocalDate coDate) {
        this.coDate = coDate;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public boolean isSurcharge() {
        return surcharge;
    }

    public void setSurcharge(boolean surcharge) {
        this.surcharge = surcharge;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @JsonIgnore
    public Room getRoom() {
        return room;
    }

    @JsonIgnore
    public void setRoom(Room room) {
        this.room = room;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    @JsonIgnore
    public void setUser(User user) {
        this.user = user;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
