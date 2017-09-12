package app.models;

import java.util.List;

/**
 * Created by Gergely_Agnecz on 9/12/2017.
 */
public class MergedDetails {
    List<Hotel> hotels;
    List<Price> prices;

    public MergedDetails(List<Hotel> hotels, List<Price> prices) {
        this.hotels = hotels;
        this.prices = prices;
    }
}
