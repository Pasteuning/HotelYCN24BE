package nl.youngcapital.backend.repository;

import nl.youngcapital.backend.model.Hotel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

public class HotelRepository {
    @Component
    public interface hotelRepository extends CrudRepository<Hotel, Long> {
    }
}
