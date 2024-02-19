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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nl.youngcapital.backend.dto.ReservationDTO;
import nl.youngcapital.backend.model.User;
import nl.youngcapital.backend.service.UserService;

@RestController
@CrossOrigin(maxAge = 3600)
public class UserController {
    @Autowired
    private UserService userService;



    // Create
    @PostMapping("/createuser")
    public Long createUser (@RequestBody User user) {
        return userService.createUser(user);
    }


    // Read
    @GetMapping("/allusers")
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public Optional<User> getUser(@PathVariable ("id") long id) {
        return userService.getUser(id);
    }

    @GetMapping("/{id}/reservations")
    public Iterable<ReservationDTO> findReservationsOfUser(@PathVariable ("id") long id, @RequestParam(required = false) String pastOrFuture) {
        // Voer als parameter "past" in of "future". default = future
        return userService.findReservationsOfUser(id, pastOrFuture);
    }


    // Edit
    @PutMapping ("/edituser/{id}")
    public boolean editUser (@PathVariable ("id") long id, @RequestBody User updatedUser) {
        return userService.editUser(id, updatedUser);
    }


    // Delete
    @DeleteMapping ("/deleteuser/{id}")
    public void deleteUser(@PathVariable ("id") long id) {
        userService.deleteUser(id);
    }
}
