package nl.youngcapital.backend.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.youngcapital.backend.model.Hotel;
import nl.youngcapital.backend.model.Reservation;
import nl.youngcapital.backend.model.Room;
import nl.youngcapital.backend.dto.RoomDTO;
import nl.youngcapital.backend.repository.HotelRepository;
import nl.youngcapital.backend.repository.ReservationRepository;
import nl.youngcapital.backend.repository.RoomRepository;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    public enum Status { SUCCESS, FAILED};


    // Create
    public Room createRoom (Room room, long hotelId) {
        try {
            Hotel hotel = hotelRepository.findById(hotelId).orElseThrow();
            room.setHotel(hotel);
            roomRepository.save(room);
            System.out.println("Successfully created room on Id: " + room.getId());
            return room;
        } catch (NoSuchElementException e) {
            System.err.println("Failed to create room. Cannot find hotel on Id: " + hotelId);
        } catch (Exception e) {
            System.err.println("Error while creating room");
            System.err.println(e.getMessage());
        }
        return null;
    }


    // Read
    public Iterable<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoom(long id) {
        return roomRepository.findById(id);
    }


    // Edit
    public boolean editRoom(long id, Room updatedRoom, long hotelId) {
        try {
            Room room = roomRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Cannot find room on Id: " + id));
            Hotel hotel = hotelRepository.findById(hotelId)
                    .orElseThrow(() -> new NoSuchElementException("Cannot find hotel on Id: " + hotelId));

            if (updatedRoom.getRoomType() != null) {
                room.setRoomType(updatedRoom.getRoomType());
            }
            if (updatedRoom.getNoBeds() != 0) {
                room.setNoBeds(updatedRoom.getNoBeds());
            }
            room.setDescription(updatedRoom.getDescription());
            room.setPrice(updatedRoom.getPrice());
            room.setHotel(hotel);

            roomRepository.save(room);
            return true;

        } catch (NoSuchElementException e) {
            System.err.println("Failed to edit room. " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error while changing password");
            System.err.println(e.getMessage());
        }
        return false;
    }

    public Status setRoomDescription(long hotelId, String roomType, String description) {
        try {
            Room.RoomType rtEnum = Room.RoomType.valueOf(roomType);

            if (description == null) { description = ""; }

            if (description.length() > 1000) {
                System.err.println("Room description cannot contain more than 1000 characters");
                return Status.FAILED;
            }

            if (hotelRepository.findById(hotelId).isPresent()) {
                Iterable<Room> rooms = roomRepository.findByHotelIdAndRoomType(hotelId, rtEnum);

                for (Room room : rooms) {
                    room.setDescription(description);
                }
                roomRepository.saveAll(rooms);
                System.out.println("Successfully set descriptions of room type " + rtEnum + " of hotel " + hotelId);
                return Status.SUCCESS;
            } else {
                System.err.println("Failed to set description of rooms. Cannot find hotel on Id: " + hotelId);
            }
        } catch (IllegalArgumentException e) {
                System.err.println("Failed to set description of rooms. Room type should be SINGLE, DOUBLE, or FAMILY");
            }
        return Status.FAILED;
    }


    // Delete
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
                if ((adults + children) <= hotel.getRooms().get(i).getNoBeds()) {
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
            if (availableRooms.isEmpty()) {
                System.out.println("No available rooms found");
            }
            return availableRooms;
        } else return null;
    }

    private boolean checkAvailability(Room suitableRoom, LocalDate cid, LocalDate cod) {
        // Gaat alle bestaande reserveringen per geschikte kamer af 
        // Kijkt of er overlap is wat betreft ciDate en coDate tussen zoekopdracht en bestaande reserveringen
        // Indien de boolean bij elke reservering true blijft en de reservering, komt het in de availableRooms List terecht
        boolean available = true;
        for (int i = 0; i < suitableRoom.getReservation().size(); i++) {
            boolean cancelled = suitableRoom.getReservation().get(i).getStatus() == Reservation.Status.CANCELLED;

            // Stuurt available false als reservering overlapt en de status van de reservering niet CANCELLED is
            if (suitableRoom.getReservation().get(i).getCiDate().isBefore(cid) &&
                    suitableRoom.getReservation().get(i).getCoDate().isAfter(cid) &&
                    !cancelled) {
                available = false;
                break;
            } else if (suitableRoom.getReservation().get(i).getCiDate().isBefore(cod) &&
                    suitableRoom.getReservation().get(i).getCoDate().isAfter(cod) &&
                    !cancelled) {
                available = false;
                break;
            } else if (suitableRoom.getReservation().get(i).getCiDate().isAfter(cid) &&
                    suitableRoom.getReservation().get(i).getCoDate().isBefore(cod) &&
                    !cancelled) {
                available = false;
                break;
            } else if (suitableRoom.getReservation().get(i).getCiDate().isEqual(cid) &&
                    suitableRoom.getReservation().get(i).getCoDate().isEqual(cod) &&
                    !cancelled) {
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