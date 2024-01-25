package nl.youngcapital.backend.repository;

import nl.youngcapital.backend.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends CrudRepository<User, Long> {
}
