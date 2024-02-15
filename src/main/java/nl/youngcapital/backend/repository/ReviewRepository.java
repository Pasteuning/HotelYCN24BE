package nl.youngcapital.backend.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.youngcapital.backend.model.Review;

@Component
public interface ReviewRepository extends CrudRepository<Review, Long> {

    @Query(value = "SELECT * FROM review WHERE hotel_id = ?1", nativeQuery = true)
    Iterable<Review> getReviewsFromHotel(long hotelId);
}
