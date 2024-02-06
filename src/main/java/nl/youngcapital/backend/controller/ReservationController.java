package nl.youngcapital.backend.controller;

import nl.youngcapital.backend.model.Hotel;
import nl.youngcapital.backend.model.Reservation;
import nl.youngcapital.backend.model.ReservationDTO;
import nl.youngcapital.backend.repository.HotelRepository;
import nl.youngcapital.backend.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(maxAge = 3600)
public class ReservationController {
    @Autowired
    private ReservationService reservationService;


    @GetMapping("/allreservations")
    public Iterable<ReservationDTO> getAllReservations(@RequestParam String sort){
        return reservationService.getAllReservations(sort);
    }

//    @GetMapping("/reservations/{id}")
//    public Optional<Reservation> getReservation(@PathVariable ("id") long id ) {
//        return reservationService.getReservation(id);
//    }


    @GetMapping("/reservations/{id}")
    public ReservationDTO getReservation(@PathVariable ("id") long id) {
        return reservationService.getReservation(id);
    }

//    @PostMapping("/createreservation")
//    public Reservation createReservation(@RequestBody Reservation reservation) {
//        System.out.println("Received: " + reservation);
//        return reservationService.createReservation(reservation);
//    }

    @PostMapping("/createreservation")
    public ReservationDTO createReservation(@RequestBody ReservationDTO reservationDTO) {
        System.out.println("Received: " + reservationDTO);
        return reservationService.createReservation(reservationDTO);
    }

    @GetMapping("/deletereservation/{id}")
    public void deleteReservation(@PathVariable ("id") long id) {
        reservationService.deleteReservation(id);
    }

//    @PostMapping("/editreservation/{id}")
//    public ReservationDTO editReservation(@PathVariable long id, @RequestBody ReservationDTO updatedReservation) {
//        return reservationService.editReservation(id, updatedReservation);
//    }

    @PostMapping("/reservations/assignroom")
    public void assignRoom(@RequestParam long reservationId, @RequestParam long roomId) {
        reservationService.assignRoom(reservationId, roomId);
    }

    @PostMapping("/reservations/assignuser")
    public void assignUser(@RequestParam long reservationId, @RequestParam long userId) {
        reservationService.assignUser(reservationId, userId);
    }
}
