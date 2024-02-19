package nl.youngcapital.backend.controller;

import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import nl.youngcapital.backend.model.Account;
import nl.youngcapital.backend.dto.AccountDTO;
import nl.youngcapital.backend.model.Review;
import nl.youngcapital.backend.service.AccountService;

@RestController
@CrossOrigin(maxAge = 3600)
public class AccountController {
    @Autowired
    private AccountService accountService;



    // Create
    @PostMapping("/create-account/{userId}")
    public AccountService.Status createAccount(@PathVariable ("userId") long userId, @RequestBody Account account) {
        return accountService.createAccount(userId, account);
    }

    @PostMapping("/create-account")
    public Long createAccount2(@RequestBody Account account) {
        return accountService.createAccount2(account);
    }


    @PostMapping("/login")
    public Account login(@RequestBody AccountDTO accountDTO) {
        return accountService.login(accountDTO.getEmail(), accountDTO.getPassword());
    }


    // Read
    @GetMapping("/all-accounts")
    public Iterable<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/account/{id}")
    public Optional<Account> getAccount(@PathVariable("id") long accountId) {
        return accountService.getAccount(accountId);
    }

    @GetMapping("/get-account")
    public Optional<Account> getAccountFromToken(HttpServletRequest request) {
        return accountService.getAccountFromToken(request);
    }


    @GetMapping("/account/{id}/reviews")
    public Iterable<Review> getReviewsFromAccount(@PathVariable ("id") long id) {
        Optional<Account> accountOptional = accountService.getAccount(id);
        if (accountOptional.isPresent()) {
        	Account account = accountOptional.get();

        	return account.getReviews();
        }

        return null;
    }




    // Edit
    @PutMapping("/account/change-password")
    public boolean changePassword(@RequestBody String newPassword, HttpServletRequest request) {
        Account account = (Account) request.getAttribute("YC_ACCOUNT");

        if (account == null) {
            System.err.println("Invalid Token");
            return false;
        }

        if (newPassword.length() > 100) {
            System.err.println("Password cannot have more than 100 characters");
            return false;
        }

        if (newPassword.isBlank()) {
            System.err.println("Password cannot be blank");
            return false;
        }

        return accountService.changePassword(account, newPassword);
    }


    // Delete
    @DeleteMapping("/delete-account/{id}")
    public AccountService.Status deleteAccount(@PathVariable ("id") long id) {
        return accountService.deleteAccount(id);
    }



    // Andere methodes
    @PostMapping("is-email-available")
    public boolean isEmailAvailable(@RequestBody String email) {
        return accountService.isEmailAvailable(email);
    }
}

