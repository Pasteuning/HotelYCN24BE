package nl.youngcapital.backend.service;

import nl.youngcapital.backend.model.Account;
import nl.youngcapital.backend.model.Review;
import nl.youngcapital.backend.model.User;
import nl.youngcapital.backend.repository.AccountRepository;
import nl.youngcapital.backend.repository.ReviewRepository;
import nl.youngcapital.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewRepository reviewRepository;



    // Create
    public boolean createAccount(long userId, Account account) {
        try {
            User user = userRepository.findById(userId).orElseThrow();
            user.setAccount(account);

            account.setLoyaltyPoints(0);
            account.setRole(Account.Role.GUEST);
            account.setHotelId(-100);
            account.setUser(user);

            accountRepository.save(account);
            userRepository.save(user);
            System.out.println("Successfully created account on Id: " + account.getId());
            return true;
        } catch (NoSuchElementException e) {
            System.err.println("Failed to create account. Cannot find user on Id: " + userId);
            return false;
        } catch (DataAccessException e) {
            System.err.println("Failed to save account to the database: " + e.getMessage());
            return false;
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

    public Iterable<Review> getReviewsFromAccount(long id) {
        try {
            Account account = accountRepository.findById(id).orElseThrow();
            return account.getReviews();
        } catch (NoSuchElementException e) {
            System.err.println("Failed to get reviews. Cannot find account on Id: " + id);
        }
        return null;
    }


    // Edit
    public boolean changePassword(long id, String newPassword) {
        try {
            Account account = accountRepository.findById(id).orElseThrow();

            if (newPassword.length() > 100) {
                System.err.println("Password cannot have more than 100 characters");
                return false;
            }

            account.setPassword(newPassword);
            accountRepository.save(account);
            System.out.println("Password successfully changed");
        } catch (NoSuchElementException e) {
            System.err.println("Failed to change password. Cannot find account on Id: " + id);
        } catch (Exception e) {
            System.err.println("Error while changing password");
            System.err.println(e.getMessage());
        }
        return false;
    }


    // Delete
    public boolean deleteAccount(long id) {
        try {
            Account account = accountRepository.findById(id).orElseThrow();
            User user = userRepository.findById(account.getUser().getId()).orElseThrow();
            user.setAccount(null);

            Iterable<Review> reviews = getReviewsFromAccount(id);
                for (Review review : reviews) { review.setAccount(null); }

            accountRepository.deleteById(account.getId());
            System.out.println("Successfully deleted account with Id: " + account.getId());
            return true;
        } catch (NoSuchElementException e) {
            System.err.println("Failed to delete account. Cannot find account on Id: " + id);
        } catch (Exception e) {
            System.err.println("Failed to delete account");
            System.err.println(e.getMessage());
        }
        return false;
    }






}
