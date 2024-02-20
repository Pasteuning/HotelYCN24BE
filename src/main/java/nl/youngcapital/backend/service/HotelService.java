package nl.youngcapital.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import nl.youngcapital.backend.model.Hotel;
import nl.youngcapital.backend.model.Reservation;
import nl.youngcapital.backend.dto.ReservationDTO;
import nl.youngcapital.backend.model.Review;
import nl.youngcapital.backend.model.Room;
import nl.youngcapital.backend.repository.HotelRepository;
import nl.youngcapital.backend.repository.ReservationRepository;
import nl.youngcapital.backend.repository.ReviewRepository;
import nl.youngcapital.backend.repository.RoomRepository;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReviewRepository reviewRepository;



    // Create
    public boolean createHotel (Hotel hotel){
        try {
            hotelRepository.save(hotel);
            System.out.println("Successfully created hotel on Id: " + hotel.getId());
            return true;
        } catch (DataAccessException e) {
            System.err.println("Failed to save hotel to the database: " + e.getMessage());
            return false;
        }
    }


    // Read
    public Iterable<Hotel> getAllHotels(){
        return hotelRepository.findAll();
    }

    public Optional<Hotel> getHotel(long id) {
        if (hotelRepository.findById(id).isPresent()) {
            System.out.println("Returning hotel with Id: " + id);
        } else {
            System.err.println("Failed to get hotel. Cannot find hotel on Id: " + id);
        }
        return hotelRepository.findById(id);
    }

    public Iterable<Room> getRooms(long hotelId) {
        try {
            Hotel hotel = hotelRepository.findById(hotelId).orElseThrow();
            //deze arraylist wordt gevuld met rooms die de gegeven hotelId hebben
            List<Room> rooms = new ArrayList<>(hotel.getRooms());
            System.out.println("Returning list of rooms of hotel with Id: " + hotelId);
            return rooms;
        } catch (NoSuchElementException e) {
            System.err.println("Failed to get rooms. Cannot find hotel on Id: " + hotelId);
        } catch (Exception e) {
            System.err.println("Error while getting rooms");
            System.err.println(e.getMessage());
        } return null;
    }

    public Iterable<ReservationDTO> getReservationsOfHotel(long id){
        try {
            Iterable<Reservation> reservations = reservationRepository.findReservationsOfHotel(id);

            List<ReservationDTO> dtoList = new ArrayList<>();

            for (Reservation r : reservations) {
                dtoList.add(new ReservationDTO(id, r.getRoom().getHotel().getName(), r.getId(),
                        r, r.getUser().getId(), r.getUser().getFirstName(), r.getUser().getLastName()));
            }
            System.out.println("Returning list of reservations of hotel with Id: " + id);
            return dtoList;
        } catch (NoSuchElementException e) {
            System.err.println("Failed to get hotel. Cannot find hotel on Id: " + id);
        }
        return null;
    }

    public Iterable<Review> getReviewsOfHotel(long id) {
        return reviewRepository.getReviewsFromHotel(id);
    }


    // Edit
    public boolean editHotel(long id, Hotel updatedHotel)  {
        try {
            Hotel hotel = hotelRepository.findById(id).orElseThrow();
            if (updatedHotel.getName() != null) {
                hotel.setName(updatedHotel.getName());
            }
            if (updatedHotel.getStreet() != null) {
                hotel.setStreet(updatedHotel.getStreet());
            }
            if (updatedHotel.getHouseNumber() != null) {
                hotel.setHouseNumber(updatedHotel.getHouseNumber());
            }
            if (updatedHotel.getZipCode() != null) {
                hotel.setZipCode(updatedHotel.getZipCode());
            }
            if (updatedHotel.getCity() != null) {
                hotel.setCity(updatedHotel.getCity());
            }
            if (updatedHotel.getCountry() != null) {
                hotel.setCountry(updatedHotel.getCountry());
            }
            if (updatedHotel.getDescription() != null) {
                hotel.setDescription(updatedHotel.getDescription());
            }
            hotelRepository.save(hotel);
            System.out.println("Successfully edited hotel");
            return true;
        } catch (NoSuchElementException e) {
            System.err.println("Failed to edit hotel. Cannot find hotel on Id: " + id);
        } catch (Exception e) {
            System.err.println("Error while editing hotel");
            System.err.println(e.getMessage());
        } return false;
    }


    //Delete
    public boolean deleteHotel (long id) {
        try {
            Hotel hotel = hotelRepository.findById(id).orElseThrow();
            hotelRepository.deleteById(id);
            System.out.println("Successfully deleted hotel with Id: " + id);
        } catch (NoSuchElementException e) {
            System.err.println("Failed to delete hotel. Cannot find hotel on Id: " + id);
        } catch (Exception e) {
            System.err.println("Failed to delete hotel");
            System.err.println(e.getMessage());
        }
        return false;
    }


}


