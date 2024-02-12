package nl.youngcapital.backend.repository;

import nl.youngcapital.backend.model.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface BookingRepository extends CrudRepository<Booking, Long> {
}
