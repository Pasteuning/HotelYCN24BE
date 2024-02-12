package nl.youngcapital.backend.controller;

import nl.youngcapital.backend.model.ReservationDTO;
import nl.youngcapital.backend.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(maxAge = 3600)
public class ReservationController {
    @Autowired
    private ReservationService reservationService;



    // Create
    @PostMapping("/createreservation")
    public boolean createReservation(@RequestBody ReservationDTO reservationDTO) {
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
