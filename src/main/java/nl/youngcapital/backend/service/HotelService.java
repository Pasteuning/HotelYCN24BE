package nl.youngcapital.backend.service;

import nl.youngcapital.backend.model.Hotel;
import nl.youngcapital.backend.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;
    public Iterable<Hotel> getAllHotels(){
        return hotelRepository.findAll();
    }

  //  public void createHotel{
//        hotelRepository.save(new Hotel());


  //  }

}


