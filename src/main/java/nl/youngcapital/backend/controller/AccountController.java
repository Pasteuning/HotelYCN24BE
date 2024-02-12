package nl.youngcapital.backend.controller;

import nl.youngcapital.backend.model.Account;
import nl.youngcapital.backend.model.Review;
import nl.youngcapital.backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(maxAge = 3600)
public class AccountController {
    @Autowired
    private AccountService accountService;



    // Create
    @PostMapping("/create-account/{userId}")
    public boolean createAccount(@PathVariable ("userId") long userId, @RequestBody Account account) {
        return accountService.createAccount(userId, account);
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
        return accountService.getReviewsFromAccount(id);
    }


    // Edit
    @PutMapping("/account/{id}/change-password")
    public boolean changePassword(@PathVariable ("id") long id, @RequestBody String newPassword) {
        return accountService.changePassword(id, newPassword);
    }


    // Delete
    @DeleteMapping("/delete-account/{id}")
    public boolean deleteAccount(@PathVariable ("id") long id) {
        return accountService.deleteAccount(id);
    }



}

