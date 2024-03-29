package nl.youngcapital.backend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.youngcapital.backend.model.Reservation;

import java.util.Optional;

@Component
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    @Query(value = "SELECT res.* " +
            "FROM reservation res " +
            "JOIN room r on r.id = res.room_id " +
            "WHERE r.hotel_id = ?1", nativeQuery = true)
    Iterable<Reservation> findReservationsOfHotel(long id);

    @Query(value = "SELECT * FROM reservation WHERE user_id = ?1 AND ci_date >= CURRENT_DATE", nativeQuery = true)
    Iterable<Reservation> findReservationsOfUser(long id);

    @Query(value = "SELECT * FROM reservation WHERE user_id = ?1 AND ci_date < CURRENT_DATE", nativeQuery = true)
    Iterable<Reservation> findPastReservationsOfUser(long id);

    Optional<Reservation> findByUuid(String uuid);

    Optional<Reservation> findByUuidAndBookingIdIsNull(String uuid);
}
