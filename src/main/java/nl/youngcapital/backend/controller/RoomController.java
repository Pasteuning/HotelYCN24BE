package nl.youngcapital.backend.controller;

import nl.youngcapital.backend.model.Room;
import nl.youngcapital.backend.service.HotelService;
import nl.youngcapital.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(maxAge = 3600)
public class RoomController {
    @Autowired
    private RoomService roomService;


    @GetMapping("/allrooms")
    public Iterable<Room> getAllRooms() {
        Room room = new Room();
        return roomService.getAllRooms();
    }

    @GetMapping("/room/{id}")
    public Optional<Room> getRoom(@PathVariable long id){
        return roomService.getRoom(id);
    }

    @PostMapping("/createroom")
    public Room createRoom (@RequestBody Room room, @RequestParam long hotelId){
        System.out.println("Received: " + room);
        System.out.println("Received hotel id: " + hotelId);
        return roomService.createRoom(room, hotelId);
    }

    @GetMapping ("/deleteroom/{id}")
    public void deleteRoom(@PathVariable long id){
        roomService.deleteRoom(id);
    }

    @PostMapping("/editroom/{id}")
    public Room editRoom (@PathVariable long id, @RequestBody Room updatedRoom, @RequestParam long hotelId){
        return roomService.editRoom(id, updatedRoom, hotelId);
    }

}
