package nl.youngcapital.backend.controller;

import nl.youngcapital.backend.model.Hotel;
import nl.youngcapital.backend.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(maxAge = 3600)
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @GetMapping("/hotel")
    public Iterable<Hotel> getAllHotels() {
        return hotelService.getAllHotels();
    }
}
