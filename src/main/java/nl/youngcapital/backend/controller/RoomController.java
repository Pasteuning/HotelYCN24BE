package nl.youngcapital.backend.controller;

import nl.youngcapital.backend.model.Room;
import nl.youngcapital.backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(maxAge = 3600)
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/room")
    public Iterable<Room> getAllRooms() {
        Room room = new Room();

        return roomService.getAllRooms();
    }
}
