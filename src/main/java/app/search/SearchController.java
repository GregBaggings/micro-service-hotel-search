package app.search;

import app.handlers.ErrorHandler;
import app.handlers.ResponseBuilder;
import app.models.Hotel;
import app.models.MergedDetails;
import app.models.Price;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
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
import java.util.List;

/**
 * Created by Gergely_Agnecz on 7/27/2017.
 */
@RestController
public class SearchController {
    private ErrorHandler incorrectInputHandler = new ErrorHandler("Incorrect input. Please enter a valid hotel name!");
    private ErrorHandler missingParameterHandler = new ErrorHandler("Missing param: hotelName");
    private Logger logger = LoggerFactory.getLogger(SearchController.class);
    // private Gson gson = new Gson();
    private ResponseBuilder builder = new ResponseBuilder();
    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(value = "/v1/hotels")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ResponseEntity<?> hotels() {
        ResponseEntity<List<Hotel>> hotels = restTemplate.exchange("http://localhost:2221/v1/hotels", HttpMethod.GET, null, new ParameterizedTypeReference<List<Hotel>>() {
        });

        return new ResponseEntity<>(hotels.getBody(), HttpStatus.OK);
    }

    @RequestMapping(value = "/v1/search")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ResponseEntity<?> searchByName(@Validated @RequestParam(value = "hotelName", required = true) String hotelName) {
        ResponseEntity<List<Price>> pricesResponse;
        ResponseEntity<Hotel> hotelResponse = restTemplate.exchange("http://localhost:2221/v1/hotels/hotel?name={hotelName}", HttpMethod.GET, null, new ParameterizedTypeReference<Hotel>() {
        }, hotelName);

        Hotel hotel = hotelResponse.getBody();
        if (hotel == null) {
            return new ResponseEntity<>(new ErrorHandler(incorrectInputHandler.getError()), HttpStatus.NOT_FOUND);
        }

        int hotelId = hotelResponse.getBody().getId();
        logger.info("HotelID is : " + hotelId);

        try {
            pricesResponse = restTemplate.exchange("http://localhost:2223/v1/hotels/price?id={hotelId}", HttpMethod.GET, null, new ParameterizedTypeReference<List<Price>>() {
            }, hotelId);
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(new ErrorHandler("No pricing data is available for: " + hotelName), HttpStatus.NOT_FOUND);
        }

        List<Price> prices = pricesResponse.getBody();
        return new ResponseEntity<>(builder.buildResponse(hotel, prices), HttpStatus.OK);
    }

    @ExceptionHandler(TypeMismatchException.class)
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
