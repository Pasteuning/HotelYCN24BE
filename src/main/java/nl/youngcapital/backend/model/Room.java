package nl.youngcapital.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Room {
    public enum RoomType { SINGLE, DOUBLE, FAMILY }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private RoomType roomType;
    @Column(nullable = false, length = 4)
    private int noBeds;
    @Column(nullable = false, length = 10)
    private Double price;
    @ManyToOne
    private Hotel hotel;
    @OneToMany(mappedBy = "room")
    private List<Reservation> reservation = new ArrayList<>();



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNoBeds() {
        return noBeds;
    }

    public void setNoBeds(int noBeds) {
        this.noBeds = noBeds;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    @JsonIgnore
    public Hotel getHotel() {
        return hotel;
    }

    @JsonIgnore
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public List<Reservation> getReservation() {
        return reservation;
    }

    public void setReservation(List<Reservation> reservation) {
        this.reservation = reservation;
    }


    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomType=" + roomType +
                ", noBeds=" + noBeds +
                ", price=" + price +
                ", hotel=" + hotel +
                ", reservation=" + reservation +
                '}';
    }
}


