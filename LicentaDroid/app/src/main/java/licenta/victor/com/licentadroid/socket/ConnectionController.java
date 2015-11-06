package licenta.victor.com.licentadroid.socket;

import android.location.Location;

import de.greenrobot.event.EventBus;
import eventbus.DestinationEvent;
import model.LocationSignal;

/**
 * Created by Victor on 24-Jun-15.
 */
public class ConnectionController {

    private static ConnectionController singleton;

    private SocketClientThread thread;

    private ConnectionController(){

    }

    public static ConnectionController getInstance(){
        if(singleton == null){
            singleton = new ConnectionController();
        }
        return singleton;
    }

    public void start(){
        if(thread == null){
            thread = new SocketClientThread();
            thread.start();
        }
    }

    public  void stop(){
        if(thread == null){
            thread = new SocketClientThread();
            thread.onConnectionStop();
        }
    }

    public void onLocationChanged(Location location){
        thread.onNewLocation(location);
    }

    public void onDestinationRecieved(LocationSignal location){
        EventBus.getDefault().post(new DestinationEvent(location));
    }


}
