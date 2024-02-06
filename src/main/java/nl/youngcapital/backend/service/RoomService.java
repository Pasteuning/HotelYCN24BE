package nl.youngcapital.backend.service;

import nl.youngcapital.backend.model.Hotel;
import nl.youngcapital.backend.model.Room;
import nl.youngcapital.backend.model.RoomDTO;
import nl.youngcapital.backend.repository.HotelRepository;
import nl.youngcapital.backend.repository.ReservationRepository;
import nl.youngcapital.backend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ReservationRepository reservationRepository;


    // CRUD Methoden
    public Room createRoom (Room room, long hotelId) {
        //Koppelt een hotel aan een kamer indien het hotel bestaat. Anders blijft hotel null
        if (hotelRepository.findById(hotelId).isPresent()) {
            Hotel hotel = hotelRepository.findById(hotelId).get();
            room.setHotel(hotel);
            roomRepository.save(room);
            System.out.println("Room successfully created \n" + room);
            System.out.println("Assigned to hotel: \n" + hotel);
            return room;
        } else {
            System.out.println("Hotel not found for hotelId: " + hotelId);
            System.out.println("Room not saved. Returning null.");
            return null;
        }
    }

    public Iterable<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoom(long id) {
        return roomRepository.findById(id);
    }

    public Room editRoom(long id, Room updatedRoom, long hotelId) {

        Room room = roomRepository.findById(id).orElseThrow();
        if (updatedRoom.getRoomType() != null) {
            room.setRoomType(updatedRoom.getRoomType());
        }
        if (updatedRoom.getNoBeds() != 0) {
            room.setNoBeds(updatedRoom.getNoBeds());
        }
        if (updatedRoom.getPrice() != null) {
            room.setPrice(updatedRoom.getPrice());
        }
        if (hotelRepository.findById(hotelId).isPresent()){
            Hotel hotel = hotelRepository.findById(hotelId).get();
            room.setHotel(hotel);
        }

        roomRepository.save(room);
        return room;
    }

    public void deleteRoom (long id){
        roomRepository.deleteById(id);
    }



    // Andere methoden
    public Iterable<RoomDTO> searchRooms (long hotelId, LocalDate cid, LocalDate cod, int adults, int children) {
        if (hotelRepository.existsById(hotelId)) {
            Hotel hotel = hotelRepository.findById(hotelId).orElseThrow();

            // Zoekt naar geschikte kamers op basis van aantal bedden
            List<Room> suitableRooms = new ArrayList<>();
            for (int i = 0; i < hotel.getRooms().size(); i++) {
                if (hotel.getRooms().get(i).getNoBeds() >= adults) {
                    suitableRooms.add(hotel.getRooms().get(i));
                }
            }

            // Hier komen de beschikbare kamers in
            List<RoomDTO> availableRooms = new ArrayList<>();
            
            // Gaat elke kamer af van de geschikte kamers af
            for (Room suitableRoom : suitableRooms) {
                boolean available = checkAvailability(suitableRoom, cid, cod);

                // Indien de kamer beschikbaar is, wordt de totaalprijs berekend waarna de kamer in de availableRooms gezet
                if (available) {
                    RoomDTO room = new RoomDTO(suitableRoom);
                    room.setPrice(calculatePrice(room.getPrice(), cid, cod, children));
                    availableRooms.add(room);
                }
            }
            return availableRooms;
        } else return null;
    }

    private boolean checkAvailability(Room suitableRoom, LocalDate cid, LocalDate cod) {
        // Gaat alle bestaande reserveringen per geschikte kamer af 
        // Kijkt of er overlap is wat betreft ciDate en coDate
        // Indien de boolean bij elke reservering true blijft, komt het in de availableRooms List terecht
        boolean available = true;
        for (int j = 0; j < suitableRoom.getReservation().size(); j++) {
            if (suitableRoom.getReservation().get(j).getCiDate().isBefore(cid) &&
                    suitableRoom.getReservation().get(j).getCoDate().isAfter(cid)) {
                available = false;
            } else if (suitableRoom.getReservation().get(j).getCiDate().isBefore(cod) &&
                    suitableRoom.getReservation().get(j).getCoDate().isAfter(cod)) {
                available = false;
            } else if (suitableRoom.getReservation().get(j).getCiDate().isAfter(cid) &&
                    suitableRoom.getReservation().get(j).getCoDate().isBefore(cod)) {
                available = false;
            } 
        }
        return available;
    }

    private double calculatePrice(double price, LocalDate cid, LocalDate cod, int children) {
        long noDays = ChronoUnit.DAYS.between(cid, cod);
        double totalPrice = (noDays * price);

        // Als er kinderen zijn, komt er een toeslag van 25 euro bovenop
        if (children > 0) {
            totalPrice += 25;
        }
        return totalPrice;
    }





}