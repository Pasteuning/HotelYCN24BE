package nl.youngcapital.backend.controller;

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

    @GetMapping("/allusers")
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public Optional<User> getUser(@PathVariable long id) {
        return userService.getUser(id);
    }

    @PostMapping("/createuser")
    public User createUser (@RequestBody User User) {
        return userService.createUser(User);
    }

    @GetMapping ("/deleteuser/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }

    @PostMapping ("/edituser/{id}")
    public User editUser (@PathVariable long id, @RequestBody User updatedUser) {
        return userService.editUser(id, updatedUser);

    }
}
