package app.search;

import app.models.Hotel;
import app.models.MergedDetails;
import app.models.Price;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
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

    @RequestMapping(value = "/v1/search")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ResponseEntity<?> search() {

        //TODO ADD SEARCH PARAMS AND CHANGE THE POJO ACCORDING TO THE EXPECTED RESPONSE FORMAT

        ResponseEntity<List<Hotel>> hotels = restTemplate.exchange("http://localhost:2221/v1/hotels", HttpMethod.GET, null, new ParameterizedTypeReference<List<Hotel>>() {
        });
        ResponseEntity<List<Price>> prices = restTemplate.exchange("http://localhost:2223/v1/hotels/prices", HttpMethod.GET, null, new ParameterizedTypeReference<List<Price>>() {
        });

        MergedDetails mergedDetails = new MergedDetails(hotels.getBody(), prices.getBody());
        Gson gson = new Gson();
        String result= gson.toJson(mergedDetails);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
