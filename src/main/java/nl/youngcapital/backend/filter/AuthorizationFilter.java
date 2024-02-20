package nl.youngcapital.backend.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nl.youngcapital.backend.model.Account;
import nl.youngcapital.backend.repository.AccountRepository;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {
	
	@Autowired
	private AccountRepository accountRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Lees de token uit de request
		String authorizationToken = request.getHeader("Authorization");
		if (authorizationToken != null && !authorizationToken.isBlank()) {
			// Vind de user
			String token = authorizationToken.substring(7); // "Bearer 1234"

			Optional<Account> accountOptional = accountRepository.findByToken(token);
			if (accountOptional.isPresent()) {
				// Plaats die in de het request
				request.setAttribute("YC_ACCOUNT", accountOptional.get());
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
