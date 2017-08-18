package app.search;

import app.handlers.ErrorHandler;
import app.models.Hotel;
import app.models.HotelsDAO;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Gergely_Agnecz on 7/27/2017.
 */
@RestController
public class SearchController {

    @Autowired
    HotelsDAO dao;

    @RequestMapping("/v1/hotels")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ResponseEntity<?> hotels() {
        List<Hotel> hotels = dao.findAll();

        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/hotel", params = "id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ResponseEntity<?> hotelByID(@RequestParam int id) {
        Hotel hotel = dao.findById(id);

        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/hotel", params = "name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ResponseEntity<?> hotelByName(@RequestParam String name) {
        Hotel hotel = dao.findByhotelName(name);

        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @RequestMapping("/v1/hotels/{city}")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ResponseEntity<?> hotelsForADestination(@PathVariable("city") String city) {
        List<Hotel> hotels = dao.findAllByCity(city);

        if (hotels.isEmpty()) {
            return new ResponseEntity(new ErrorHandler("No hotel is available for that target location!"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }
}
