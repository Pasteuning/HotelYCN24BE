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

    
    @GetMapping("/allrooms")
    public Iterable<Room> getAllRooms() {
        Room room = new Room();
        return roomService.getAllRooms();
    }

    @GetMapping("/room/{id}")
    public Optional<Room> getRoom(@PathVariable long id){
        return roomService.getRoom(id);
    }

    @GetMapping("/searchrooms/{hotelId}")
    public Iterable<RoomDTO> searchRooms(@PathVariable long hotelId, @RequestParam LocalDate cid, @RequestParam LocalDate cod, @RequestParam int adults, @RequestParam int children) {
        System.out.println("Received: ");
        System.out.println("HotelId: " + hotelId +
                ", Check-in: " + cid +
                ", Check-out: " + cod +
                ", Adults: " + adults +
                ", Children: " + children);
        return roomService.searchRooms(hotelId, cid, cod, adults, children);
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
