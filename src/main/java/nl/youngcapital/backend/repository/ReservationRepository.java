package nl.youngcapital.backend.repository;

import nl.youngcapital.backend.model.Reservation;
import nl.youngcapital.backend.model.ReservationDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    @Query(value = "SELECT res.* " +
            "FROM reservation res " +
            "JOIN room r on r.id = res.room_id " +
            "WHERE r.hotel_id = ?1", nativeQuery = true)
    Iterable<Reservation> findReservationsOfHotel(long id);
}
