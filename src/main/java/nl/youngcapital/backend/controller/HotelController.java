package nl.youngcapital.backend.controller;

import nl.youngcapital.backend.model.Hotel;
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

    @GetMapping("/allhotels")
    public Iterable<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/hotel/{id}")
    public Optional<Hotel> getHotel(@PathVariable long id) {
        return hotelService.getHotel(id);
    }

    @GetMapping("/hotel/{hotelId}/rooms")
    public Iterable<Room> getRooms(@PathVariable long hotelId) {
        return hotelService.getRooms(hotelId);
    }

    @PostMapping("/createhotel")
    public Hotel createHotel (@RequestBody Hotel hotel) {
        return hotelService.createHotel(hotel);
    }

    @GetMapping ("/deletehotel/{id}")
    public void deleteHotel(@PathVariable long id) {
        hotelService.deleteHotel(id);
    }

    @PostMapping ("/edithotel/{id}")
    public Hotel editHotel (@PathVariable long id, @RequestBody Hotel updatedHotel) {
        return hotelService.editHotel(id, updatedHotel);

    }
}
