package nl.youngcapital.backend.service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import nl.youngcapital.backend.model.Account;
import nl.youngcapital.backend.model.Review;
import nl.youngcapital.backend.model.User;
import nl.youngcapital.backend.repository.AccountRepository;
import nl.youngcapital.backend.repository.ReviewRepository;
import nl.youngcapital.backend.repository.UserRepository;

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
            if (userRepository.getAccountIdFromEmail(user.getEmail()) != null) {
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


    // Edit
    public Status changePassword(long id, String newPassword) {
        try {
            Account account = accountRepository.findById(id).orElseThrow();

            if (newPassword.length() > 100) {
                System.err.println("Password cannot have more than 100 characters");
                return Status.FAILED;
            }

            account.setPassword(newPassword);
            accountRepository.save(account);
            System.out.println("Password successfully changed");
            return Status.SUCCESS;
        } catch (NoSuchElementException e) {
            System.err.println("Failed to change password. Cannot find account on Id: " + id);
        } catch (Exception e) {
            System.err.println("Error while changing password");
            System.err.println(e.getMessage());
        }
        return Status.FAILED;
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
    	
    	
//        try {
//            User user = userRepository.findByEmail(accountDTO.getEmail());
//
//            if (user != null && accountRepository.findById(user.getAccount().getId()).isPresent()) {
//                Account account = accountRepository.findById(user.getAccount().getId()).orElseThrow();
//
//                if (accountDTO.getPassword().equals(account.getPassword())) {
//                    System.out.println("Password is correct. ");
//                    SessionDTO sessionDTO =  new SessionDTO(user.getId(), account.getId());
//
//                    if (account.getHotelId() == -100) {
//                        return "/user_account";
//                    } else {
//                        return "/manager";
//                    }
//                } else {
//                    System.err.println("Password is incorrect");
//                }
//
//            } else {
//                System.err.println("Account doesn't exist on email: " + accountDTO.getEmail());
//                return "Account doesn't exist";
//            }
//        } catch (NoSuchElementException e) {
//            System.err.println("Failed to login. Cannot find email in database: " + accountDTO.getEmail());
//        } catch (Exception e) {
//            System.err.println("Error while logging in");
//            System.err.println(e.getMessage());
//        }
//        return "Failed to login";
    }

}
