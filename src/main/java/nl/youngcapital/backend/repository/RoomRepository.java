package nl.youngcapital.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.youngcapital.backend.model.Room;

@Component
public interface RoomRepository extends CrudRepository<Room, Long> {

    Iterable<Room> findByHotelIdAndRoomType(long hotelId, Room.RoomType roomType);
}
