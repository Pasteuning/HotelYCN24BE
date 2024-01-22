package nl.youngcapital.backend.repository;

import nl.youngcapital.backend.model.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Long> {
}
