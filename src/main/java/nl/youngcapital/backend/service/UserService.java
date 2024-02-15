package nl.youngcapital.backend.service;

import nl.youngcapital.backend.model.Reservation;
import nl.youngcapital.backend.model.ReservationDTO;
import nl.youngcapital.backend.model.User;
import nl.youngcapital.backend.repository.ReservationRepository;
import nl.youngcapital.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationRepository reservationRepository;


    // Create
    public User createUser (User user){
        user.setEmail(user.getEmail().toLowerCase());
        userRepository.save(user);
        System.out.println("Successfully created user on Id: " + user.getId());
        return user;
    }


    // Read
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUser(long id) {
        return userRepository.findById(id);
    }

    public Iterable<ReservationDTO> findReservationsOfUser(long id, String pastOrFuture) {
        if (pastOrFuture == null) { pastOrFuture = "future"; }

        List<ReservationDTO> dtoList = new ArrayList<>();

        if (userRepository.existsById(id)) {
            Iterable<Reservation> reservations;
            if (pastOrFuture.equals("past")) {
                reservations = reservationRepository.findPastReservationsOfUser(id);
            } else {
                reservations = reservationRepository.findReservationsOfUser(id);
            }

            for (Reservation reservation : reservations){
                dtoList.add(new ReservationDTO(reservation));
            }
            dtoList.sort(Comparator.comparing(dto -> dto.getReservation().getCiDate()));
            System.out.println("Returning list of reservations from user with Id: " + id);
        }
        else {
            System.err.println("Failed to get reservations. Cannot find user on Id: " + id);
        }
        return dtoList;
    }


    // Edit
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


    // Delete
    public void deleteUser (long id) {
        userRepository.deleteById(id);
    }
}
