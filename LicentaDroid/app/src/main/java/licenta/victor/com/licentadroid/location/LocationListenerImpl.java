package licenta.victor.com.licentadroid.location;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import licenta.victor.com.licentadroid.socket.ConnectionController;
import licenta.victor.com.licentadroid.socket.SocketClientThread;
import model.LocationSignal;

/**
 * Created by Victor on 24-Jun-15.
 */
public class LocationListenerImpl implements LocationListener {

    Activity context;

    public  LocationListenerImpl(Activity context){
        this.context = context;
    }

    @Override
    public void onLocationChanged(Location loc) {

        loc.getLatitude();
        loc.getLongitude();
        String text = "Lat: " + loc.getLatitude() +  "Lng: " + loc.getLongitude();
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        ConnectionController.getInstance().onLocationChanged(loc);
        LocationSignal signal= new LocationSignal();
        signal.setLat(loc.getLatitude());
        signal.setLng(loc.getLongitude());
        EventBus.getDefault().post(signal);
    }


    @Override
    public void onProviderDisabled(String provider) {
       // Toast.makeText(getApplicationContext(), "asgag", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
       //Toast.makeText(getApplicationContext(), "asgag", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }


}