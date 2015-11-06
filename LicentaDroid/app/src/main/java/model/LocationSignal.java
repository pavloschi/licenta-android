package model;

import java.io.Serializable;

public class LocationSignal implements Serializable{
    private int id;
    private long timestamp;
    private double lat;
    private double lng;
    private int carId;

    private int speed;
    private int code;

    public LocationSignal() {
        super();
    }

    public LocationSignal(int id, long timestamp, double lat, double lng, int carId) {
        super();
        this.id = id;
        this.timestamp = timestamp;
        this.lat = lat;
        this.lng = lng;
        this.carId = carId;
    }

    public LocationSignal(long timestamp, double lat, double lng, int carId) {
        super();
        this.timestamp = timestamp;
        this.lat = lat;
        this.lng = lng;
        this.carId = carId;
    }


    public LocationSignal(double lat, double lng, int carId) {
        super();

        this.lat = lat;
        this.lng = lng;
        this.carId = carId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isValid(){
        if(lat != 0 && lng != 0){
            return true;
        }
        return false;
    }


}
