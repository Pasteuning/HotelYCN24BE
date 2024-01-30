package nl.youngcapital.backend.controller;

import nl.youngcapital.backend.model.Reservation;
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
    public Iterable<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/reservations/{id}")
    public Optional<Reservation> getReservation(@PathVariable ("id") long id ) {
        return reservationService.getReservation(id);
    }

    @PostMapping("/createreservation")
    public Reservation createReservation(@RequestBody Reservation reservation) {
        System.out.println("Received: " + reservation);
        return reservationService.createReservation(reservation);
    }

    @GetMapping("/deletereservation/{id}")
    public void deleteReservation(@PathVariable ("id") long id) {
        reservationService.deleteReservation(id);
    }

    @PostMapping("/editreservation/{id}")
    public Reservation editReservation(@PathVariable long id, @RequestBody Reservation updatedReservation) {
        return reservationService.editReservation(id, updatedReservation);
    }
}
