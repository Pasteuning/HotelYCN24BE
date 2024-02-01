package nl.youngcapital.backend.service;

import nl.youngcapital.backend.model.User;
import nl.youngcapital.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;



    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUser(long id) {
        return userRepository.findById(id);
    }

    public User createUser (User User){
        userRepository.save(User);
        System.out.println("User successfully created: \n" + User);
        return User;
    }

    public void deleteUser (long id) {
        userRepository.deleteById(id);
    }

    public User editUser(long id, User updatedUser)  {
        User User = userRepository.findById(id).orElseThrow();
        if (updatedUser.getFirstName() != null) {
            User.setFirstName(updatedUser.getFirstName());
        }
        if (updatedUser.getLastName() != null) {
            User.setLastName(updatedUser.getLastName());
        }
        if (updatedUser.getDateOfBirth() != null) {
            User.setDateOfBirth(updatedUser.getDateOfBirth());
        }
        if (updatedUser.getStreet() != null) {
            User.setStreet(updatedUser.getStreet());
        }
        if (updatedUser.getHouseNumber() != null) {
            User.setHouseNumber(updatedUser.getHouseNumber());
        }
        if (updatedUser.getZipCode() != null) {
            User.setZipCode(updatedUser.getZipCode());
        }
        if (updatedUser.getCity() != null) {
            User.setCity(updatedUser.getCity());
        }
        if (updatedUser.getCountry() != null) {
            User.setCountry(updatedUser.getCountry());
        }
        if (updatedUser.getEmail() != null) {
            User.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPhoneNumber() != null) {
            User.setPhoneNumber(updatedUser.getPhoneNumber());
        }

        userRepository.save(User);
        return User;
    }
}
