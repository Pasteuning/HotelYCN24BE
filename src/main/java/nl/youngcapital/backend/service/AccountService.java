package nl.youngcapital.backend.service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import nl.youngcapital.backend.model.Account;
import nl.youngcapital.backend.model.Review;
import nl.youngcapital.backend.model.User;
import nl.youngcapital.backend.repository.AccountRepository;
import nl.youngcapital.backend.repository.ReviewRepository;
import nl.youngcapital.backend.repository.UserRepository;

import javax.swing.text.html.Option;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    public enum Status { ACCOUNT_ALREADY_EXISTS, ACCOUNT_DOES_NOT_EXIST, SUCCESS, FAILED }


    // Create
    public Status createAccount(long userId, Account account) {
        try {
            User user = userRepository.findById(userId).orElseThrow();
            if (userRepository.findByEmailAndAccountIsNotNull(user.getEmail()).isPresent()) {
                return Status.ACCOUNT_ALREADY_EXISTS;
            } else {
                user.setAccount(account);

                account.setLoyaltyPoints(0);
                account.setRole(Account.Role.GUEST);
                account.setHotelId(-100);
                account.setUser(user);

                accountRepository.save(account);
                userRepository.save(user);
                System.out.println("Successfully created account on Id: " + account.getId());
                return Status.SUCCESS;
            }
        } catch (NoSuchElementException e) {
            System.err.println("Failed to create account. Cannot find user on Id: " + userId);
            return Status.FAILED;
        } catch (DataAccessException e) {
            System.err.println("Failed to save account to the database: " + e.getMessage());
            return Status.FAILED;
        }
    }

    public Long createAccount2(Account account) {
        if (isAnyFieldBlank(account.getUser())) {
            System.err.println("User creation failed. All fields must be filled in.");
            return null;
        }

        if (userRepository.findByEmailAndAccountIsNotNull(account.getUser().getEmail()).isPresent()) {
            System.err.println("Account already exists on email: " + account.getUser().getEmail());
            return null;
        }

        account.getUser().setEmail(account.getUser().getEmail().toLowerCase());
        account.setLoyaltyPoints(0);
        account.setRole(Account.Role.GUEST);
        account.setHotelId(-100);
        account.setUser(account.getUser());

        userRepository.save(account.getUser());
        accountRepository.save(account);

        System.out.println("Successfully created account on Id: " + account.getId());
        return account.getUser().getId();
    }

    private boolean isAnyFieldBlank(User user) {
        return user.getFirstName() == null ||
                user.getLastName() == null ||
                user.getDateOfBirth() == null ||
                user.getStreet() == null ||
                user.getHouseNumber() == null ||
                user.getZipCode() == null ||
                user.getCity() == null ||
                user.getCountry() == null ||
                user.getEmail() == null;
    }


    // Read
    public Iterable<Account> getAllAccounts() {
        System.out.println("Returning list of all accounts");
        return accountRepository.findAll();
    }

    public Optional<Account> getAccount(long id) {
        if (accountRepository.findById(id).isPresent()) {
            System.out.println("Returning account with Id: " + id);
        } else {
            System.err.println("Failed to get account. Cannot find account on Id: " + id);
        }
        return accountRepository.findById(id);

    }

    public Optional<Account> getAccountFromToken(HttpServletRequest request) {
        Account account = (Account)request.getAttribute("YC_ACCOUNT");
        return Optional.ofNullable(account);
    }



    // Edit
    public boolean changePassword(Account account, String newPassword) {
        account.setPassword(newPassword);
        accountRepository.save(account);
        System.out.println("New password successfully saved");
        return true;
    }


    // Delete
    public Status deleteAccount(long id) {
    	Optional<Account> accountOptional = accountRepository.findById(id);
    	if (accountOptional.isPresent()) {
            Account account = accountOptional.get();

            User user = account.getUser();
            user.setAccount(null);
            userRepository.save(user);

            Iterable<Review> reviews = account.getReviews();
            for (Review review : reviews) { 
            	review.setAccount(null);
            	reviewRepository.save(review);
            }

            accountRepository.deleteById(account.getId());
            System.out.println("Successfully deleted account with Id: " + account.getId());
            return Status.SUCCESS;
    	}
        return Status.FAILED;
    }


    public Account login(String email, String password) {
    	// Check de user
    	Optional<User> userOptional = userRepository.findByEmailAndAccountIsNotNull(email);
    	if (userOptional.isEmpty()) {
    		return null;
    	}

    	User dbUser = userOptional.get();
    	if (!dbUser.getAccount().getPassword().equalsIgnoreCase(password)) {
    		return null;
    	}
    	
    	// Generate token
    	int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 100;
        Random random = new Random();

    	String generatedToken = random.ints(leftLimit, rightLimit + 1)
    		      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
    		      .limit(targetStringLength)
    		      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
    		      .toString();

    	dbUser.getAccount().setToken(generatedToken);
    	accountRepository.save(dbUser.getAccount());
    	
    	return dbUser.getAccount();
    }


    public boolean isEmailAvailable(String email) {
        Optional<User> userOptional = userRepository.findByEmailAndAccountIsNotNull(email);
        return userOptional.isEmpty();
    }
}
