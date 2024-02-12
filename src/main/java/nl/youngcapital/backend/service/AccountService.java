package nl.youngcapital.backend.service;

import nl.youngcapital.backend.model.*;
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

    public enum Status { ACCOUNT_ALREADY_EXISTS, ACCOUNT_DOES_NOT_EXIST, SUCCESS, FAILED }


    // Create
    public Status createAccount(long userId, Account account) {
        try {
            User user = userRepository.findById(userId).orElseThrow();
            if (userRepository.checkIfAccountExists(user.getEmail()) != null) {
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
        try {
            Account account = accountRepository.findById(id).orElseThrow();
            User user = userRepository.findById(account.getUser().getId()).orElseThrow();
            user.setAccount(null);

            Iterable<Review> reviews = getReviewsFromAccount(id);
                for (Review review : reviews) { review.setAccount(null); }

            accountRepository.deleteById(account.getId());
            System.out.println("Successfully deleted account with Id: " + account.getId());
            return Status.SUCCESS;
        } catch (NoSuchElementException e) {
            System.err.println("Failed to delete account. Cannot find account on Id: " + id);
        } catch (Exception e) {
            System.err.println("Failed to delete account");
            System.err.println(e.getMessage());
        }
        return Status.FAILED;
    }


    // Andere methodes
    public Status login(AccountDTO accountDTO){
        try {
            User user = userRepository.findByEmail(accountDTO.getEmail());
            if (user != null && accountRepository.findById(user.getAccount().getId()).isPresent()) {
                Account account = accountRepository.findById(user.getAccount().getId()).orElseThrow();
                if (accountDTO.getPassword().equals(account.getPassword())) {
                    System.out.println("Password is correct");
                    return Status.SUCCESS;
                } else {
                    System.err.println("Password is incorrect");
                }
            } else {
                System.err.println("Account doesn't exist on email: " + accountDTO.getEmail());
                return Status.ACCOUNT_DOES_NOT_EXIST;
            }
        } catch (NoSuchElementException e) {
            System.err.println("Failed to login. Cannot find email in database: " + accountDTO.getEmail());
        } catch (Exception e) {
            System.err.println("Error while logging in");
            System.err.println(e.getMessage());
        }
        return Status.FAILED;
    }
}
