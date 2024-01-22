package nl.youngcapital.backend.controller;

import nl.youngcapital.backend.model.Hotel;
import nl.youngcapital.backend.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(maxAge = 3600)
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @GetMapping("/hotel")
    public Iterable<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @PostMapping("/createhotel")
    public Hotel createHotel (@RequestBody Hotel hotel) {
        return hotelService.createHotel(hotel);
    }

//    @GetMapping ("/deletehotel/{id}")
//    public void deleteHotel(@PathVariable Long id) {
//        hotelService.deleteHotel(id);
//    }


}
