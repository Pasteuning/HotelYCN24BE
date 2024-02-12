package nl.youngcapital.backend.repository;

import nl.youngcapital.backend.model.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface ReviewRepository extends CrudRepository<Review, Long> {
}
