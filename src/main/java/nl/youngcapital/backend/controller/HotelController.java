package nl.youngcapital.backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import nl.youngcapital.backend.model.Hotel;
import nl.youngcapital.backend.dto.ReservationDTO;
import nl.youngcapital.backend.model.Review;
import nl.youngcapital.backend.model.Room;
import nl.youngcapital.backend.service.HotelService;

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

    @GetMapping("/hotel/{id}/reviews")
    public Iterable<Review> getReviewsFromHotel(@PathVariable ("id") long id) {
        return hotelService.getReviewsOfHotel(id);
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
