package nl.youngcapital.backend.controller;

import nl.youngcapital.backend.model.Hotel;
import nl.youngcapital.backend.model.Reservation;
import nl.youngcapital.backend.model.ReservationDTO;
import nl.youngcapital.backend.model.Room;
import nl.youngcapital.backend.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(maxAge = 3600)
public class HotelController {
    @Autowired
    private HotelService hotelService;



    // Create
    @PostMapping("/createhotel")
    public boolean createHotel (@RequestBody Hotel hotel) {
        return hotelService.createHotel(hotel);
    }


    // Read
    @GetMapping("/allhotels")
    public Iterable<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/hotel/{id}")
    public Optional<Hotel> getHotel(@PathVariable ("id") long id) {
        return hotelService.getHotel(id);
    }

    @GetMapping("/hotel/{id}/rooms")
    public Iterable<Room> getRooms(@PathVariable ("id")long id) {
        return hotelService.getRooms(id);
    }

    @GetMapping("/hotel/{id}/reservations")
    public Iterable<ReservationDTO> getReservationsOfHotel(@PathVariable ("id") long id) {
        return hotelService.getReservationsOfHotel(id);
    }


    // Edit
    @PutMapping ("/edithotel/{id}")
    public boolean editHotel (@PathVariable ("id") long id, @RequestBody Hotel updatedHotel) {
        return hotelService.editHotel(id, updatedHotel);
    }


    // Delete
    @DeleteMapping ("/deletehotel/{id}")
    public boolean deleteHotel(@PathVariable ("id") long id) {
        return hotelService.deleteHotel(id);
    }


}
