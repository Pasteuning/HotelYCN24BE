package nl.youngcapital.backend.repository;

import nl.youngcapital.backend.model.Hotel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface HotelRepository extends CrudRepository<Hotel, Long> {
    }

