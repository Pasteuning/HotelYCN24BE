package nl.youngcapital.backend.controller;

import nl.youngcapital.backend.model.ReservationDTO;
import nl.youngcapital.backend.model.User;
import nl.youngcapital.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(maxAge = 3600)
public class UserController {
    @Autowired
    private UserService userService;



    // Create
    @PostMapping("/createuser")
    public User createUser (@RequestBody User User) {
        return userService.createUser(User);
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
    public User editUser (@PathVariable ("id") long id, @RequestBody User updatedUser) {
        return userService.editUser(id, updatedUser);
    }


    // Delete
    @DeleteMapping ("/deleteuser/{id}")
    public void deleteUser(@PathVariable ("id") long id) {
        userService.deleteUser(id);
    }
}
