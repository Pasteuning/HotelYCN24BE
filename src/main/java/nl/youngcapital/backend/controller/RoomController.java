package nl.youngcapital.backend.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import nl.youngcapital.backend.model.Account;
import nl.youngcapital.backend.model.Account.Role;
import nl.youngcapital.backend.model.Room;
import nl.youngcapital.backend.model.RoomDTO;
import nl.youngcapital.backend.service.RoomService;

@RestController
@CrossOrigin(maxAge = 3600)
public class RoomController {
    @Autowired
    private RoomService roomService;



    // Create
    @PostMapping("/createroom")
    public Room createRoom (@RequestBody Room room, @RequestParam long hotelId, HttpServletRequest request) {
    	Account account = (Account)request.getAttribute("YC_ACCOUNT");
    	if (account == null) {
    		return null;
    	}

    	if (account.getRole() != Role.OWNER) {
    		return null;
    	}

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
    public boolean editRoom (@PathVariable ("id") long id, @RequestBody Room updatedRoom, @RequestParam long hotelId){
        return roomService.editRoom(id, updatedRoom, hotelId);
    }

    @PutMapping("/set-roomdescription/{hotelId}")
    public RoomService.Status setRoomDescription(@PathVariable ("hotelId") long hotelId, @RequestParam String roomType, @RequestBody(required = false) String description) {
        return roomService.setRoomDescription(hotelId, roomType, description);
    }



    // Delete
    @DeleteMapping ("/deleteroom/{id}")
    public void deleteRoom(@PathVariable ("id") long id){
        roomService.deleteRoom(id);
    }
}
