package nl.youngcapital.backend.model;

import jakarta.persistence.*;

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
    @JoinColumn
    private Hotel hotel;



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

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}


