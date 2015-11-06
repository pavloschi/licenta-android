package licenta.victor.com.licentadroid.main;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
/*import android.support.v4.app.FragmentActivity;*/
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import eventbus.DestinationEvent;
import licenta.victor.com.licentadroid.R;
import licenta.victor.com.licentadroid.location.LocationListenerImpl;
import licenta.victor.com.licentadroid.prefs.AppConstants;
import licenta.victor.com.licentadroid.socket.ConnectionController;
import licenta.victor.com.licentadroid.socket.SocketClientThread;
import model.LocationSignal;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private FloatingActionButton startButton;
    private FloatingActionButton stopButton;
    ArrayList<Marker> markers = new ArrayList<>();
    Marker markerInit ;
    Marker markerDestination;
    boolean started = false;
//    44.435085, 26.102830

    AlertDialog alertDialog;
    String[] carValues = {"1: Giani","2: asgas","3: sdfhshsh","4: aggg"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        startButton = (FloatingActionButton) findViewById(R.id.startTripFab);
        stopButton = (FloatingActionButton) findViewById(R.id.stopTripFab);
        LayoutInflater inflater = getLayoutInflater();


        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener mlocListener = new LocationListenerImpl(this);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
        if(!started){
            startButton.setVisibility(View.VISIBLE);
            stopButton.setVisibility(View.GONE);
        }else{
            startButton.setVisibility(View.GONE);
            stopButton.setVisibility(View.VISIBLE);
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionController.getInstance().start();
                startButton.setVisibility(View.GONE);
                stopButton.setVisibility(View.VISIBLE);
                started = true;
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionController.getInstance().stop();
                startButton.setVisibility(View.VISIBLE);
                stopButton.setVisibility(View.GONE);
                started = false;
            }
        });
        if(!started){
            buildDialog(inflater);
        }


        //ConnectionController.getInstance().start();
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));

        EventBus.getDefault().register(this);

        alertDialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {
        markerInit = mMap.addMarker(new MarkerOptions().position(new LatLng( 44.435085, 26.102830)).title("Marker"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng( 44.435085, 26.102830), 12.0f));
        markers.add(markerInit);
    }

    public void onEventMainThread(DestinationEvent event){
        LocationSignal destination = event.getDestination();

        markerDestination = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(destination.getLat(), destination.getLng()))
                        .title("Destination"));
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(markerInit.getPosition());
        builder.include(markerDestination.getPosition());
        LatLngBounds bounds = builder.build();
        int padding = 150;
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        //mMap.animateCamera(cu);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(destination.getLat() ,destination.getLng()), 12.0f));
        markerDestination.showInfoWindow();


        Log.e("dest: ",destination.getLat()+" "+destination.getLng());
        Log.e("marker","marker");
    }

    public void onEventMainThread(LocationSignal event){
        LocationSignal location = event;
        markerInit.setPosition(new LatLng(location.getLat(),location.getLng()));
    }


    public void buildDialog(LayoutInflater inflater){


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View dialogView = inflater.inflate(R.layout.dialog_layout, null);
        dialogBuilder.setView(dialogView);
        final EditText editText = (EditText) dialogView.findViewById(R.id.editText);
        final Spinner spinner = (Spinner) dialogView.findViewById(R.id.spinner);
        editText.setText(AppConstants.getServerIp());


        //OK button
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ip = editText.getText().toString();
                AppConstants.setServerIp(ip);
                int carId = spinner.getSelectedItemPosition()+ 1;
                AppConstants.setCarId(carId);
            }

        });

        //cancel button
        dialogBuilder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }

        });
        dialogBuilder.setTitle("Set car and server IP :");
        alertDialog = dialogBuilder.create();

    }


}

