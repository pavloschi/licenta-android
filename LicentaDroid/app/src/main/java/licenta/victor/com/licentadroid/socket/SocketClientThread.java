package licenta.victor.com.licentadroid.socket;

import android.location.Location;
import android.os.Looper;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import model.LocationSignal;
import licenta.victor.com.licentadroid.prefs.AppConstants;
import model.SocketMessage;


/**
 * Created by Victor on 06-Jun-15.
 */
public class SocketClientThread extends Thread {

    private static final int SERVERPORT = 4321;
  /*  private static final String SERVER_IP = "192.168.1.143";*/

    private Socket socket;
    // private MapsActivity context;

    //Message msg = new Message();

    LocationSignal signal; /*= new LocationSignal(0,0,carId);*/
    int carId;

    static ObjectOutputStream out;
    static ObjectInputStream in;
   // 44.412917,  44.412917
    public SocketClientThread() {
        carId = AppConstants.CAR_ID;
        signal = new LocationSignal( 0, 0, carId);
    }

    @Override
    public void run() {
        Looper.prepare();
        try {
            InetAddress serverAddr = InetAddress.getByName(AppConstants.getServerIp());
            Socket socket = new Socket(serverAddr, SERVERPORT);
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());
                Reader reader = new Reader(in);
                reader.start();
                onConnectionStart();

                while (true) {


                    try {

                        Log.e("sent", "asgas");
                        long now = System.currentTimeMillis();
                        signal.setTimestamp(now);
                        // JSONObject json = signal.toJson();
                        out.writeObject(signal);
                        out.flush();


                        Thread.sleep(3000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void onNewLocation(Location location) {
        // long now = System.currentTimeMillis();
        signal = new LocationSignal(location.getLatitude(), location.getLongitude(), carId);
    }

    public void onConnectionStart() {
        if (out != null) {
            try {
                SocketMessage message = new SocketMessage();
                message.setMessage(SocketMessage.CAR_CONNECTED);
                message.setCarId(AppConstants.CAR_ID);
                out.writeObject(message);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onConnectionStop() {

    }

    public void sendMessage() {

    }

    public LocationSignal getSignal() {
        return signal;
    }

    public void setSignal(LocationSignal signal) {
        this.signal = signal;
    }

    private class Reader extends  Thread{
        private ObjectInputStream in;

        public Reader(ObjectInputStream in) {
            this.in = in;
        }

        public void run() {

            try {
                while (true) {

                    Object obj = in.readObject();


                    if(obj instanceof LocationSignal){
                        LocationSignal locationSignal = (LocationSignal) obj;
                        ConnectionController.getInstance().onDestinationRecieved(locationSignal);
                    }

                    System.out.println(obj);

                    System.out.println("=====");


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


}
