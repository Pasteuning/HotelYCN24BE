package nl.youngcapital.backend.repository;

import nl.youngcapital.backend.model.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface RoomRepository extends CrudRepository<Room, Long> {
}
