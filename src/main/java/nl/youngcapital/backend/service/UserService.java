package nl.youngcapital.backend.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.youngcapital.backend.model.Reservation;
import nl.youngcapital.backend.dto.ReservationDTO;
import nl.youngcapital.backend.model.User;
import nl.youngcapital.backend.repository.ReservationRepository;
import nl.youngcapital.backend.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationRepository reservationRepository;


    // Create
    public Long createUser (User user){
        if (isAnyFieldBlank(user)) {
            System.err.println("User creation failed. All fields must be filled in.");
            return null;
        }

        user.setEmail(user.getEmail().toLowerCase());
        userRepository.save(user);
        System.out.println("Successfully created user on Id: " + user.getId());
        return user.getId();
    }

    private boolean isAnyFieldBlank(User user) {
        return user.getFirstName() == null ||
                user.getLastName() == null ||
                user.getDateOfBirth() == null ||
                user.getStreet() == null ||
                user.getHouseNumber() == null ||
                user.getZipCode() == null ||
                user.getCity() == null ||
                user.getCountry() == null ||
                user.getEmail() == null;
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
//            System.out.println("Returning list of reservations from user with Id: " + id);
        }
        else {
            System.err.println("Failed to get reservations. Cannot find user on Id: " + id);
        }
        return dtoList;
    }


    // Edit
    public boolean editUser(long id, User updatedUser)  {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            System.err.println("User doesn't exist on Id: " + id);
            return false;
        }

        if (updatedUser.getFirstName() != null) {
            user.get().setFirstName(updatedUser.getFirstName());
        }
        if (updatedUser.getLastName() != null) {
            user.get().setLastName(updatedUser.getLastName());
        }
        if (updatedUser.getDateOfBirth() != null) {
            user.get().setDateOfBirth(updatedUser.getDateOfBirth());
        }
        if (updatedUser.getStreet() != null) {
            user.get().setStreet(updatedUser.getStreet());
        }
        if (updatedUser.getHouseNumber() != null) {
            user.get().setHouseNumber(updatedUser.getHouseNumber());
        }
        if (updatedUser.getZipCode() != null) {
            user.get().setZipCode(updatedUser.getZipCode());
        }
        if (updatedUser.getCity() != null) {
            user.get().setCity(updatedUser.getCity());
        }
        if (updatedUser.getCountry() != null) {
            user.get().setCountry(updatedUser.getCountry());
        }
        if (updatedUser.getEmail() != null) {
            user.get().setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPhoneNumber() != null) {
            user.get().setPhoneNumber(updatedUser.getPhoneNumber());
        }

        userRepository.save(user.get());
        System.out.println("Successfully saved user with Id: " + id);
        return true;
    }


    // Delete
    public void deleteUser (long id) {
        userRepository.deleteById(id);
    }
}
