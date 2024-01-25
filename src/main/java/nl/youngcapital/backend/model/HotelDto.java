package nl.youngcapital.backend.model;

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
