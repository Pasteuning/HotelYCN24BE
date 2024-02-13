package nl.youngcapital.backend.repository;

import nl.youngcapital.backend.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "SELECT * FROM user WHERE email = ?1 AND account_id IS NOT NULL", nativeQuery = true)
    User getAccountIdFromEmail(String email);

    User findByEmail(String email);
}
