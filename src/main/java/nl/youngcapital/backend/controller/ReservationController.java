package nl.youngcapital.backend.controller;

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

import nl.youngcapital.backend.dto.ReservationDTO;
import nl.youngcapital.backend.service.ReservationService;

@RestController
@CrossOrigin(maxAge = 3600)
public class ReservationController {
    @Autowired
    private ReservationService reservationService;



    // Create
    @PostMapping("/createreservation")
    public String createReservation(@RequestBody ReservationDTO reservationDTO) {
        return reservationService.createReservation(reservationDTO);
    }


    // Read
    @GetMapping("/allreservations")
    public Iterable<ReservationDTO> getAllReservations(@RequestParam(required = false) String sort){
        return reservationService.getAllReservations(sort);
    }

    @GetMapping("/reservations/{id}")
    public ReservationDTO getReservation(@PathVariable ("id") long id) {
        return reservationService.getReservation(id);
    }

    @GetMapping("/reservation-status")
    public String isReservationPaid(@RequestParam String uuid) {
        return reservationService.isReservationPaid(uuid);
    }



    // Edit
    @PutMapping("/editreservation/{id}")
    public boolean editReservation(@PathVariable ("id") long id, @RequestBody ReservationDTO updatedReservation) {
        return reservationService.editReservation(id, updatedReservation);
    }


    // Delete
    @DeleteMapping("/cancel-reservation/{id}")
    public boolean cancelReservation(@PathVariable ("id") long id) {
        return reservationService.cancelReservation(id);
    }




}
