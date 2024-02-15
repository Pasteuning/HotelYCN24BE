package nl.youngcapital.backend.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import nl.youngcapital.backend.model.Account;

@Component
public interface AccountRepository extends CrudRepository<Account, Long> {
	
	Optional<Account> findByToken(String token);
	
}
