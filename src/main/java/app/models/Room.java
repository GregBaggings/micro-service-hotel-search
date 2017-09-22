package app.models;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by Gergely_Agnecz on 8/1/2017.
 */
public class Room {

    private int hotelid;
    private int roomid;
    private String roomname;
    private boolean isItFree;
    private boolean smoking;
    private Date from;
    private Date until;

    public int getHotelid() {
        return hotelid;
    }

    public void setHotelid(int hotelid) {
        this.hotelid = hotelid;
    }

    public int getRoomid() {
        return roomid;
    }

    public void setRoomid(int roomid) {
        this.roomid = roomid;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public boolean isItFree() {
        return isItFree;
    }

    public void setItFree(boolean itFree) {
        isItFree = itFree;
    }

    public boolean isSmoking() {
        return smoking;
    }

    public void setSmoking(boolean smoking) {
        this.smoking = smoking;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getUntil() {
        return until;
    }

    public void setUntil(Date until) {
        this.until = until;
    }

    @Override
    public String toString() {
        return "hotelid=" + hotelid +
                ", roomid=" + roomid +
                ", roomname='" + roomname + '\'' +
                ", isItFree=" + isItFree +
                ", smoking=" + smoking +
                ", from=" + from +
                ", until=" + until;
    }
}
