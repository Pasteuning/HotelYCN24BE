package nl.youngcapital.backend.repository;

import nl.youngcapital.backend.model.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface ReservationRepository extends CrudRepository<Reservation, Long> {
}
