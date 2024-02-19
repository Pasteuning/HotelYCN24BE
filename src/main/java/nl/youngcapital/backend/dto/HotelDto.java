package nl.youngcapital.backend.dto;

import nl.youngcapital.backend.model.Hotel;

public class HotelDto {
    private String name;
    private int noRooms;


    public HotelDto(Hotel hotel) {
        this.name = hotel.getName();
        this.noRooms = hotel.getRooms().size();
    }

    public Hotel krijgHotel() {
        Hotel hotel = new Hotel();
        hotel.setName(name);
        return hotel;
    }
}
