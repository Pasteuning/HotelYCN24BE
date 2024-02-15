package nl.youngcapital.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.youngcapital.backend.model.Booking;

@Component
public interface BookingRepository extends CrudRepository<Booking, Long> {
}
