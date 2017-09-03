package app.search;

import app.models.Hotel;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Gergely_Agnecz on 7/27/2017.
 */
@RestController
public class SearchController {
    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(value = "/v1/hotels")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ResponseEntity<?> hotels() {
        ResponseEntity<List<Hotel>> hotels = restTemplate.exchange("http://localhost:2221/v1/hotels", HttpMethod.GET, null, new ParameterizedTypeReference<List<Hotel>>() {
        });

        return new ResponseEntity<>(hotels.getBody(), HttpStatus.OK);
    }
}
