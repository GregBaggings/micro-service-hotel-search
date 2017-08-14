package app.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Gergely_Agnecz on 7/27/2017.
 */
public interface HotelsRepository extends JpaRepository<Hotel, Integer> {

    List<Hotel> findAll();

    List<Hotel> findAllByCity(String city);

    @Query(value = "SELECT h FROM Hotel h where h.hotelName = ?1")
    Hotel findByName(String name);

    Hotel findById(int id);

    ShortHotelDetails findByHotelName(String name);


    interface ShortHotelDetails {
        String getHotelName();

        String getCity();

        String getAddress();
    }
}


