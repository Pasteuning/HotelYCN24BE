package nl.youngcapital.backend.controller;

import java.util.Optional;

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
import nl.youngcapital.backend.model.AccountDTO;
import nl.youngcapital.backend.model.Review;
import nl.youngcapital.backend.model.User;
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
    @PutMapping("/account/{id}/change-password")
    public AccountService.Status changePassword(@PathVariable ("id") long id, @RequestBody String newPassword) {
        return accountService.changePassword(id, newPassword);
    }


    // Delete
    @DeleteMapping("/delete-account/{id}")
    public AccountService.Status deleteAccount(@PathVariable ("id") long id) {
        return accountService.deleteAccount(id);
    }



}

