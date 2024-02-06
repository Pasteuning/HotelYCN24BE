package nl.youngcapital.backend.service;

import nl.youngcapital.backend.model.Reservation;
import nl.youngcapital.backend.model.ReservationDTO;
import nl.youngcapital.backend.model.Room;
import nl.youngcapital.backend.model.User;
import nl.youngcapital.backend.repository.ReservationRepository;
import nl.youngcapital.backend.repository.RoomRepository;
import nl.youngcapital.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;



    public Iterable<ReservationDTO> getAllReservations(String sort) {
        Iterable<Reservation> reservations = reservationRepository.findAll();
        List<ReservationDTO> DTOList = new ArrayList<>();

        for (Reservation reservation : reservations) {
            DTOList.add(new ReservationDTO(reservation));
        }
        return sortList(DTOList, sort);
//        return DTOList;
    }

    public List<ReservationDTO> sortList(List<ReservationDTO> list, String sort) {
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
            case "roomId":
                list.sort(Comparator.comparingLong(dto -> {
                    Long roomId = dto.getRoomId();
                    return roomId != null ? roomId : Long.MIN_VALUE;
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
        } return list;
    }

    public ReservationDTO getReservation(long id) {
        if (reservationRepository.existsById(id)) {
            Reservation reservation = reservationRepository.findById(id).orElseThrow();
            return new ReservationDTO(reservation);
        } else return null;
    }

    public ReservationDTO createReservation (ReservationDTO reservationDTO) {
        Reservation reservation = reservationDTO.getReservation();
        ReservationDTO dto = new ReservationDTO(reservation);
        if (roomRepository.existsById(reservationDTO.getRoomId())) {
            Room room = roomRepository.findById(reservationDTO.getRoomId()).orElseThrow();
            reservation.setRoom(room);
            dto.setRoomId(room.getId());
            dto.setHotelId(room.getHotel().getId());
            dto.setHotelName(room.getHotel().getName());
        }
        if (userRepository.existsById(reservationDTO.getUserId())) {
            User user = userRepository.findById(reservationDTO.getUserId()).orElseThrow();
            reservation.setUser(user);
            dto.setUserId(user.getId());
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
        }

        //zet surcharge op true indien er kinderen komen
        reservation.setSurcharge(reservationDTO.getReservation().getChildren() != 0);
        reservationRepository.save(reservation);
        System.out.println("Reservation successfully created: \n" + reservation);
        return dto;
    }

    public void deleteReservation (long id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            System.out.println("Reservation successfully deleted for Id:" + id);
        } else {
            System.out.println("Reservation not found for Id: " + id);
        }
    }

//    public ReservationDTO editReservation(long id, ReservationDTO updatedReservation) {
//
//        if (reservationRepository.existsById(id)) {
//            Reservation reservation = reservationRepository.findById(id).orElseThrow();
//            if (updatedReservation.getCiDate() != null) {
//                reservation.setCiDate(updatedReservation.getCiDate());
//            }
//            if (updatedReservation.getCoDate() != null) {
//                reservation.setCoDate(updatedReservation.getCoDate());
//            }
//            if (updatedReservation.getAdults() != 0) {
//                reservation.setAdults(updatedReservation.getAdults());
//            }
//            reservation.setChildren(updatedReservation.getChildren());
//            //zet surcharge op true indien er kinderen komen
//            reservation.setSurcharge(updatedReservation.getChildren() != 0);
//
//            reservationRepository.save(reservation);
//            System.out.println("Reservation successfully updated: \n" + reservation);
//            return reservation;
//        } else {
//            //creëert een nieuwe reservation als de reservation niet bestaat
//            System.out.println("Reservation not found for id: " + id);
//            System.out.println("Creating new reservation");
//            return createReservation(updatedReservation);
//        }
//    }

    public void assignRoom(long reservationId, long roomId) {
        if (reservationRepository.existsById(reservationId) && roomRepository.existsById(roomId)) {
            Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();
            Room room = roomRepository.findById(roomId).orElseThrow();
            reservation.setRoom(room);
            reservationRepository.save(reservation);
            System.out.println("Reservation Id: " + reservationId + " successfully assigned to Room Id: " + roomId);
        } else {
            System.out.println("Room not found");
        }
    }

    public void assignUser(long reservationId, long userId) {
        if (reservationRepository.existsById(reservationId) && userRepository.existsById(userId)) {
            Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();
            User user = userRepository.findById(userId).orElseThrow();
            reservation.setUser(user);
            reservationRepository.save(reservation);
            System.out.println("Reservation Id: " + reservationId + " successfully assigned to User Id: " + userId);
        } else {
            System.out.println("User not found");
        }
    }
}
