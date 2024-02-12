package nl.youngcapital.backend.repository;

import nl.youngcapital.backend.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public interface AccountRepository extends CrudRepository<Account, Long> {
}
