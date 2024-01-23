package nl.youngcapital.backend.service;

import nl.youngcapital.backend.model.Room;
import nl.youngcapital.backend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public Iterable<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoom(long id) {
        return roomRepository.findById(id);
    }

    public Room createRoom (Room room){
        roomRepository.save(room);
        System.out.println("Room Successfully created \n" + room);
        return room;
    }
    public void deleteRoom (long id){
        roomRepository.deleteById(id);
    }

    public Room editRoom(long id, Room updatedRoom){
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

        roomRepository.save(room);
        return room;
    }


}