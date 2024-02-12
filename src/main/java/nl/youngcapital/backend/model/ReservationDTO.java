package nl.youngcapital.backend.model;

public class ReservationDTO {
    private Long hotelId;
    private String hotelName;
    private Long roomId;
    private Reservation reservation;
    private Long userId;
    private String firstName;
    private String lastName;


    public ReservationDTO(Long hotelId, String hotelName, Long roomId, Reservation reservation, Long userId, String firstName, String lastName) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.roomId = roomId;
        this.reservation = reservation;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public ReservationDTO(Reservation reservation) {
        this.hotelId = (reservation.getRoom() != null) ? reservation.getRoom().getHotel().getId() : null;
        this.hotelName = (reservation.getRoom() != null) ? reservation.getRoom().getHotel().getName() : null;
        this.roomId = (reservation.getRoom() != null) ? reservation.getRoom().getId() :null;
        this.reservation = reservation;
        this.userId = (reservation.getUser() != null) ? reservation.getUser().getId() : null;
        this.firstName = (reservation.getUser() != null) ? reservation.getUser().getFirstName() : null;
        this.lastName = (reservation.getUser() != null) ? reservation.getUser().getLastName() : null;
    }



    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
