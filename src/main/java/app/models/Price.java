package app.models;

/**
 * Created by Gergely_Agnecz on 8/1/2017.
 */

public class Price {

    private int hotelId;
    private int roomId;
    private String roomName;
    private int price;

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "HotelID: " + this.hotelId + ", roomID: " + this.roomId + ", room name: " + this.roomName + ", price: " + this.price;
    }
}
