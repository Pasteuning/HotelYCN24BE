package nl.youngcapital.backend.controller;

import nl.youngcapital.backend.service.HotelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HotelController {
    private HotelService hotelService = new HotelService();

    @GetMapping("/hotel")
    public void createHotel() {
        hotelService.printHotel();
    }
}
