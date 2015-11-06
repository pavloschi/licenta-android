package model;

import java.io.Serializable;

/**
 * Created by Victor on 04-Jul-15.
 */
public class SocketMessage implements Serializable{
    public static final int CAR_CONNECTED = 1;
    public static final int CAR_DISCONNETED = 2;

    private int message;
    private int carId;

    public SocketMessage() {
    }

    public SocketMessage(int message, int carId) {
        this.message = message;
        this.carId = carId;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }
}
