package nl.youngcapital.backend.model;

import jakarta.persistence.*;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    kamersType roomType;
    public enum kamersType{
        SINGLE, DOUBLE, FAMILY;
    }

    @Column(nullable = false, length = 4)
    private int noBeds;
    @Column(nullable = false, length = 10)
    private Double price;

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

    public kamersType getRoomType() {
        return roomType;
    }

    public void setRoomType(kamersType roomType) {
        this.roomType = roomType;
    }
}


