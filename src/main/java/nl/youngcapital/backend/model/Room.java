package nl.youngcapital.backend.model;

import jakarta.persistence.*;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    roomType mijnKamersType;
    public enum roomType{
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

    public roomType getMijnKamersType() {
        return mijnKamersType;
    }

    public void setMijnKamersType(roomType mijnKamersType) {
        this.mijnKamersType = mijnKamersType;
    }
}


