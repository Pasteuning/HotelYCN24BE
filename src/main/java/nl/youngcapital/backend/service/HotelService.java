package nl.youngcapital.backend.service;

import nl.youngcapital.backend.model.Hotel;
import nl.youngcapital.backend.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;
    public Iterable<Hotel> getAllHotels(){
        return hotelRepository.findAll();
    }

    public Optional<Hotel> getHotel(long id) {
        return hotelRepository.findById(id);
    }

    public Hotel createHotel (Hotel hotel){
        hotelRepository.save(hotel);
        System.out.println("Hotel successfully created: \n" + hotel);
        return hotel;
    }

    public void deleteHotel (long id) {
        hotelRepository.deleteById(id);
    }

    public Hotel editHotel(long id, Hotel updatedHotel)  {
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

        hotelRepository.save(hotel);
        return hotel;
    }
}


