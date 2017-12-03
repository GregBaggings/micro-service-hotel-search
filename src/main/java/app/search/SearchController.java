package app.search;

import app.handlers.ErrorHandler;
import app.handlers.ResponseBuilder;
import app.models.Hotel;
import app.models.Price;
import app.models.Room;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gergely_Agnecz on 7/27/2017.
 */
@RestController
public class SearchController {
    private ErrorHandler incorrectInputHandler = new ErrorHandler("Incorrect input. Please enter a valid destination!");
    private ErrorHandler missingParameterHandler = new ErrorHandler("Missing param: destination");
    private String regex = "^[a-zA-Z]+$";
    private Logger logger = LoggerFactory.getLogger(SearchController.class);
    private ResponseBuilder builder = new ResponseBuilder();
    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(value = "/v1/search")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ResponseEntity<?> searchByDestination(@Validated @RequestParam(value = "destination", required = true) String destination) {
        ResponseEntity<List<Price>> pricesServiceResponse = null;
        ResponseEntity<List<Hotel>> hotelsServiceResponse = null;
        ResponseEntity<List<Room>> roomsServiceResponse = null;
        List<Integer> hotelIds = new ArrayList<Integer>();
        List<List<Hotel>> hotelList = new ArrayList<>();
        List<List<Price>> priceList = new ArrayList<>();
        List<List<Room>> roomList = new ArrayList<>();

        if (destination == null || !destination.matches(regex)) {
            throw new IllegalArgumentException();
        }

        try {
            hotelsServiceResponse = restTemplate.exchange("http://localhost:2221/v1/hotels/{destination}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Hotel>>() {
            }, destination);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(new ErrorHandler("No hotel found for the given destination: " + destination), HttpStatus.NOT_FOUND);
        }

        for (Hotel h : hotelsServiceResponse.getBody()) {
            hotelIds.add(h.getId());
            hotelList.add(hotelsServiceResponse.getBody());
        }

        logger.info("HotelIDs are: " + hotelIds);

        for (int i = 0; i < hotelIds.size(); i++) {
            try {
                logger.info("Getting price details for hotelID: " + hotelIds.get(i));
                pricesServiceResponse = restTemplate.exchange("http://localhost:2223/v1/hotels/prices/price?id={hotelId}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Price>>() {
                }, hotelIds.get(i));
                priceList.add(pricesServiceResponse.getBody());
                logger.info("Price list for hotelID " + hotelIds.get(i) + " : " + priceList.toString());
            } catch (HttpClientErrorException e) {
                logger.info("No pricing details for hotel id: " + hotelIds.get(i));
            }
        }

        logger.info("Full price list: " + priceList.toString());

        for (int i = 0; i < hotelIds.size(); i++) {
            try {
                logger.info("Getting room details for hotelID: " + hotelIds.get(i));
                roomsServiceResponse = restTemplate.exchange("http://localhost:2224/v1/hotels/hotel/{hotelId}/rooms", HttpMethod.GET, null, new ParameterizedTypeReference<List<Room>>() {
                }, hotelIds.get(i));
                roomList.add(roomsServiceResponse.getBody());
                logger.info("Rooms list for hotelID " + hotelIds.get(i) + " : " + roomList.toString());
            } catch (HttpClientErrorException e) {
                logger.info("No room details for hotel id: " + hotelIds.get(i));
            }
        }

        logger.info("Full room list: " + roomList.toString());

        if (roomList.isEmpty() || priceList.isEmpty()) {
            return new ResponseEntity<>(new ErrorHandler("No hotel found"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(builder.buildResponseFromLists(hotelList, priceList, roomList), HttpStatus.OK);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<?> wrongType(Exception exception, HttpServletRequest request) {
        return new ResponseEntity<>(incorrectInputHandler, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseEntity<?> missingParam(Exception exception, HttpServletRequest request) {
        return new ResponseEntity<>(missingParameterHandler, HttpStatus.BAD_REQUEST);
    }
}
