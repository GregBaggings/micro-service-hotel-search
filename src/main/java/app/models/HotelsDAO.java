package app.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Gergely_Agnecz on 7/27/2017.
 */
@RestController
public class HotelsDAO {

    private HotelsRepository repository;

    @Autowired
    public HotelsDAO(HotelsRepository repository) {
        super();
        this.repository = repository;
    }

    public List<Hotel> findAll() {
        return repository.findAll();
    }

    public Hotel findByhotelName(String name) {
        return repository.findByName(name);
    }

    public Hotel findById(int id) {
        return repository.findById(id);
    }

    public List<Hotel> findAllByCity(String city) {
        return repository.findAllByCity(city);
    }

    public HotelsRepository.ShortHotelDetails findByHotelName(String name) {
        return repository.findByHotelName(name);
    }
}
