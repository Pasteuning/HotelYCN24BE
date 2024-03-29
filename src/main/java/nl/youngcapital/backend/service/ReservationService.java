package nl.youngcapital.backend.service;

import nl.youngcapital.backend.dto.ReservationDTO;
import nl.youngcapital.backend.model.*;
import nl.youngcapital.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private AccountRepository accountRepository;



    // Create
    public String createReservation (ReservationDTO reservationDTO) {

        Optional<Room> room = roomRepository.findById(reservationDTO.getRoomId());
        Optional<User> user = userRepository.findById(reservationDTO.getUserId());
        Reservation reservation = reservationDTO.getReservation();

        if (room.isEmpty() || user.isEmpty()) {
            return null;
        }

        String uuid = UUID.randomUUID().toString();

        reservation.setRoom(room.get());
        reservation.setUser(user.get());
        //zet surcharge op true indien er kinderen komen
        reservation.setSurcharge(reservationDTO.getReservation().getChildren() != 0);
        reservation.setUuid(uuid);

        reservationRepository.save(reservation);
        System.out.println("Successfully created reservation on Id: " + reservation.getId());
        return uuid;
    }






    // Read
    public Iterable<ReservationDTO> getAllReservations(String sort) {
        // Stuurt een ReservationDTO lijst terug
        Iterable<Reservation> reservations = reservationRepository.findAll();
        List<ReservationDTO> DTOList = new ArrayList<>();

        for (Reservation reservation : reservations) {
            DTOList.add(new ReservationDTO(reservation));
        }
        return sortList(DTOList, sort);
    }

    public List<ReservationDTO> sortList(List<ReservationDTO> list, String sort) {
        // Als er geen sort-waarde is meegegeven, wordt er gesorteerd op roomId (default)
        if (sort == null) { sort = ""; }
        switch (sort) {
            case "hotelId":
                list.sort(Comparator.comparingLong(dto -> {
                    Long hotelId = dto.getHotelId();
                    return hotelId != null ? hotelId : Long.MIN_VALUE;
                }));
                break;
            case "hotelName":
                list.sort(Comparator.comparing(dto -> {
                    String hotelName = dto.getHotelName();
                    return hotelName != null ? hotelName : "";
                }));
                break;
            case "reservationId":
                list.sort(Comparator.comparingLong(dto -> dto.getReservation().getId()));
                break;
            case "ciDate":
                list.sort(Comparator.comparing(dto -> dto.getReservation().getCiDate()));
                break;
            case "coDate":
                list.sort(Comparator.comparing(dto -> dto.getReservation().getCoDate()));
                break;
            case "adults":
                list.sort(Comparator.comparingInt(dto -> dto.getReservation().getAdults()));
                break;
            case "children":
                list.sort(Comparator.comparingInt(dto -> dto.getReservation().getChildren()));
                break;
            case "surcharge":
                list.sort(Comparator.comparing(dto -> dto.getReservation().isSurcharge()));
                break;
            case "status":
                list.sort(Comparator.comparing(dto -> dto.getReservation().getStatus()));
                break;
            case "userId":
                list.sort(Comparator.comparingLong(dto -> {
                    Long userId = dto.getUserId();
                    return userId != null ? userId : Long.MIN_VALUE;
                }));
                break;
            case "firstName":
                list.sort(Comparator.comparing(dto -> {
                    String firstName = dto.getFirstName();
                    return firstName != null ? firstName : ""; // You can use an empty string or any other default value
                }));
                break;
            case "lastName":
                list.sort(Comparator.comparing(dto -> {
                    String lastName = dto.getLastName();
                    return lastName != null ? lastName : "";
                }));
                break;
            default:
                list.sort(Comparator.comparingLong(dto -> {
                    Long roomId = dto.getRoomId();
                    return roomId != null ? roomId : Long.MIN_VALUE;
                }));
        } return list;
    }

    public ReservationDTO getReservation(long id) {
        if (reservationRepository.existsById(id)) {
            Reservation reservation = reservationRepository.findById(id).orElseThrow();
            return new ReservationDTO(reservation);
        } else return null;
    }

    public String isReservationPaid(String uuid) {

        Optional<Reservation> reservation = reservationRepository.findByUuid(uuid);

        // Stuurt een datum uit indien de reservering al betaald is
        if (reservation.isEmpty()) {
            return "NOT_FOUND";
        } else if (reservation.get().getBooking() == null) {
            return "NOT_PAID";
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return reservation.get().getBooking().getDate().format(formatter);
        }
    }



     // Edit
    public boolean editReservation(long id, ReservationDTO updatedReservation) {
        try {
            Reservation reservation = reservationRepository.findById(id)
                    .orElseThrow(() -> new NoSuchElementException("Cannot find reservation on Id: " + id));
            Room room = roomRepository.findById(updatedReservation.getRoomId())
                    .orElseThrow(() -> new NoSuchElementException("Cannot find room on Id: " + updatedReservation.getRoomId()));
            reservation.setRoom(room);

            if (updatedReservation.getReservation().getCiDate() != null) {
                reservation.setCiDate(updatedReservation.getReservation().getCiDate());
            }
            if (updatedReservation.getReservation().getCoDate() != null) {
                reservation.setCoDate(updatedReservation.getReservation().getCoDate());
            }
            if (updatedReservation.getReservation().getAdults() != 0) {
                reservation.setAdults(updatedReservation.getReservation().getAdults());
            }
            reservation.setChildren(updatedReservation.getReservation().getChildren());
            reservation.setSurcharge(updatedReservation.getReservation().getChildren() != 0);
            if (updatedReservation.getReservation().getSpecialRequest() != null) {
                reservation.setSpecialRequest(updatedReservation.getReservation().getSpecialRequest());
            }
            reservation.setStatus(Reservation.Status.RESERVED);

            reservationRepository.save(reservation);
            System.out.println("Reservation successfully edited");
            return true;
        } catch (NoSuchElementException e) {
            System.err.println("Failed to edit reservation. " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error while editing reservation");
            System.err.println(e.getMessage());
        }
        return false;
    }


//    public boolean editReservation(long id, ReservationDTO updatedReservation) {
//        try {
//            Reservation reservation = reservationRepository.findById(id).orElseThrow();
//            Room room = roomRepository.findById(updatedReservation.getRoomId()).orElseThrow();
//            reservation.setRoom(room);
//
//            if (updatedReservation.getReservation().getCiDate() != null) {
//                reservation.setCiDate(updatedReservation.getReservation().getCiDate());
//            }
//            if (updatedReservation.getReservation().getCoDate() != null) {
//                reservation.setCoDate(updatedReservation.getReservation().getCoDate());
//            }
//            if (updatedReservation.getReservation().getAdults() != 0) {
//                reservation.setAdults(updatedReservation.getReservation().getAdults());
//            }
//            reservation.setChildren(updatedReservation.getReservation().getChildren());
//            reservation.setSurcharge(updatedReservation.getReservation().getChildren() != 0);
//            if (updatedReservation.getReservation().getSpecialRequest() != null) {
//                reservation.setSpecialRequest(updatedReservation.getReservation().getSpecialRequest());
//            }
//            reservation.setStatus(Reservation.Status.RESERVED);
//
//            reservationRepository.save(reservation);
//            System.out.println("Reservation successfully edited");
//            return true;
//        } catch (NoSuchElementException e) {
//            System.out.println(e.getMessage());
//            if (e.getMessage().contains("reservation")) {
//                System.err.println("Failed to edit reservation. Cannot find reservation on Id: " + id);
//            } else {
//                System.err.println("Failed to edit reservation. Cannot find room on Id: " + updatedReservation.getRoomId());
//            }
//        } catch (Exception e) {
//            System.err.println("Error while editing reservation");
//            System.err.println(e.getMessage());
//        }
//        return false;
//    }


    // Delete
    public boolean cancelReservation(long id) {
        // Met deze methode wordt de booking verwijderd en de reservering op status CANCELLED gezet

        try {
            Reservation reservation = reservationRepository.findById(id).orElseThrow();

            // Als er een booking en een account is, worden er loyalty punten van het account gehaald
            Optional<Account> account = accountRepository.findById(reservation.getUser().getAccount().getId());
            if (account.isPresent() && reservation.getBooking() != null) {
                account.get().setLoyaltyPoints((account.get().getLoyaltyPoints() - 100));
                System.out.println("Loyalty points removed on account: " + account.get().getId());
                accountRepository.save(account.get());
            }


            // Als er een Booking is gekoppeld aan de reservering, wordt de booking verwijderd
            if (reservation.getBooking() != null) {
                long bookingId = reservation.getBooking().getId();
                reservation.setBooking(null);
                bookingRepository.deleteById(bookingId);
                System.out.println("Successfully deleted booking with Id: " + bookingId +
                        ", associated with reservation with Id: " + id);
            }


            // Reserveringen worden niet verwijderd, maar op status CANCELLED gezet
            reservation.setStatus(Reservation.Status.CANCELLED);
            reservationRepository.save(reservation);
            System.out.println("Reservation set to status CANCELLED for Id: " + id);
            return true;
        } catch (NoSuchElementException e) {
            System.err.println("Failed to delete reservation. Reservation with Id: " + id + " doesn't exist");
        } catch (Exception e) {
            System.err.println("Failed to cancel reservation with Id: " + id);
            System.err.println(e.getMessage());
        }
        return false;
    }


}
