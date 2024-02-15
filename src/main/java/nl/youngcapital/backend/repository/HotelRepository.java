package nl.youngcapital.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.youngcapital.backend.model.Hotel;

@Component
public interface HotelRepository extends CrudRepository<Hotel, Long> {
    }

