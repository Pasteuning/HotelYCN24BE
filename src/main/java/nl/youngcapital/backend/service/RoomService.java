package nl.youngcapital.backend.service;

import nl.youngcapital.backend.model.Hotel;
import nl.youngcapital.backend.model.Room;
import nl.youngcapital.backend.repository.HotelRepository;
import nl.youngcapital.backend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HotelRepository hotelRepository;



    public Iterable<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoom(long id) {
        return roomRepository.findById(id);
    }

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

    public void deleteRoom (long id){
        roomRepository.deleteById(id);
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


}