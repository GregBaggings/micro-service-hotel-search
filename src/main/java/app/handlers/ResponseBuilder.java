package app.handlers;

import app.models.Hotel;
import app.models.Price;
import app.models.Room;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Gergely_Agnecz on 8/21/2017.
 */
public class ResponseBuilder {
    private JSONObject jsonObject = new JSONObject();

    public JSONObject buildResponse(Hotel hotel, List<Price> prices) {

        HashMap<Object, Object> hotelDetails = new HashMap<>();
        hotelDetails.put("hotelName", hotel.getHotelName());
        hotelDetails.put("country", hotel.getCountry());
        hotelDetails.put("city", hotel.getCity());
        hotelDetails.put("address", hotel.getAddress());
        hotelDetails.put("lat", hotel.getLat());
        hotelDetails.put("lon", hotel.getLon());
        hotelDetails.put("minprice", hotel.getMinprice());

        jsonObject.put("result", "OK");
        jsonObject.put("hotelDetails", hotelDetails);
        jsonObject.put("pricing", prices);
        return jsonObject;
    }

    public JSONObject buildResponseFromLists(List<List<Hotel>> hotel, List<List<Price>> prices, List<List<Room>> rooms) {

        HashMap<Object, Object> hotelDetails = new HashMap<>();
        for (int i = 0; i < hotel.size(); i++) {
            jsonObject.put("hotelDetails", hotelDetails);
            hotelDetails.put("hotel" + i, hotel.get(i).get(i));
        }
        jsonObject.put("result", "OK");
        return jsonObject;
    }
}
