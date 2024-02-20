package nl.youngcapital.backend.dto;

import nl.youngcapital.backend.model.Room;

public class RoomDTO {
    private long id;
    private String hotelName;
    private Room.RoomType roomType;
    private int noBeds;
    private double price;


    public RoomDTO(Room room) {
        this.id = room.getId();
        this.hotelName = room.getHotel().getName();
        this.roomType = room.getRoomType();
        this.noBeds = room.getNoBeds();
        this.price = room.getPrice();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Room.RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(Room.RoomType roomType) {
        this.roomType = roomType;
    }

    public int getNoBeds() {
        return noBeds;
    }

    public void setNoBeds(int noBeds) {
        this.noBeds = noBeds;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
