package nl.youngcapital.backend.controller;

import nl.youngcapital.backend.model.Room;
import nl.youngcapital.backend.model.RoomDTO;
import nl.youngcapital.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@CrossOrigin(maxAge = 3600)
public class RoomController {
    @Autowired
    private RoomService roomService;



    // Create
    @PostMapping("/createroom")
    public Room createRoom (@RequestBody Room room, @RequestParam long hotelId){
        return roomService.createRoom(room, hotelId);
    }


    // Read
    @GetMapping("/allrooms")
    public Iterable<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/room/{id}")
    public Optional<Room> getRoom(@PathVariable ("id") long id){
        return roomService.getRoom(id);
    }

    @GetMapping("/searchrooms/{hotelId}")
    public Iterable<RoomDTO> searchRooms(@PathVariable ("hotelId") long hotelId, @RequestParam LocalDate cid, @RequestParam LocalDate cod, @RequestParam int adults, @RequestParam int children) {
        return roomService.searchRooms(hotelId, cid, cod, adults, children);
    }


    // Edit
    @PutMapping("/editroom/{id}")
    public Room editRoom (@PathVariable ("id") long id, @RequestBody Room updatedRoom, @RequestParam long hotelId){
        return roomService.editRoom(id, updatedRoom, hotelId);
    }


    // Delete
    @DeleteMapping ("/deleteroom/{id}")
    public void deleteRoom(@PathVariable ("id") long id){
        roomService.deleteRoom(id);
    }
}
