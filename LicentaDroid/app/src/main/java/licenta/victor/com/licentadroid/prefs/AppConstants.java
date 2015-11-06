package licenta.victor.com.licentadroid.prefs;

/**
 * Created by Victor on 24-Jun-15.
 */
public class AppConstants {

    public static int CAR_ID = 2;
    public static String SERVER_IP = "192.168.1.143";

    public static  void setCAR_ID(int CAR_ID) {
        CAR_ID = CAR_ID;
    }

    public static String getServerIp() {
        return SERVER_IP;
    }

    public static void setServerIp(String serverIp) {
        SERVER_IP = serverIp;
    }

    public static int getCarId() {
        return CAR_ID;
    }

    public static void setCarId(int carId) {
        CAR_ID = carId;
    }
}
