package nl.youngcapital.backend.service;

import nl.youngcapital.backend.model.Reservation;
import nl.youngcapital.backend.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public Iterable<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservation(long id) {
        if (reservationRepository.existsById(id)) {
            return reservationRepository.findById(id);
        } else return Optional.empty();
    }

    public Reservation createReservation (Reservation reservation) {
        //zet surcharge op true indien er kinderen komen
        reservation.setSurcharge(reservation.getChildren() != 0);
        reservationRepository.save(reservation);
        System.out.println("Reservation successfully created: \n" + reservation);
        return reservation;
    }

    public void deleteReservation (long id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            System.out.println("Reservation successfully deleted for Id:" + id);
        } else {
            System.out.println("Reservation not found for Id: " + id);
        }
    }

    public Reservation editReservation(long id, Reservation updatedReservation) {

        if (reservationRepository.existsById(id)) {
            Reservation reservation = reservationRepository.findById(id).orElseThrow();
            if (updatedReservation.getCiDate() != null) {
                reservation.setCiDate(updatedReservation.getCiDate());
            }
            if (updatedReservation.getCoDate() != null) {
                reservation.setCoDate(updatedReservation.getCoDate());
            }
            if (updatedReservation.getAdults() != 0) {
                reservation.setAdults(updatedReservation.getAdults());
            }
            reservation.setChildren(updatedReservation.getChildren());
            //zet surcharge op true indien er kinderen komen
            reservation.setSurcharge(updatedReservation.getChildren() != 0);

            reservationRepository.save(reservation);
            System.out.println("Reservation successfully updated: \n" + reservation);
            return reservation;
        } else {
            //creëert een nieuwe reservation als de reservation niet bestaat
            System.out.println("Reservation not found for id: " + id);
            System.out.println("Creating new reservation");
            return createReservation(updatedReservation);

        }

    }
}
