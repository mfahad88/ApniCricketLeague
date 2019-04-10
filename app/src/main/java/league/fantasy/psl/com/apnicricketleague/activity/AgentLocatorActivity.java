package league.fantasy.psl.com.apnicricketleague.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.Utils.GPSTracker;
import league.fantasy.psl.com.apnicricketleague.Utils.Helper;
import league.fantasy.psl.com.apnicricketleague.client.ApiClient;
import league.fantasy.psl.com.apnicricketleague.model.request.AgentBeanRequest;
import league.fantasy.psl.com.apnicricketleague.model.response.Agent.AgentBeanResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgentLocatorActivity extends AppCompatActivity implements OnMapReadyCallback,View.OnClickListener,Callback<AgentBeanResponse> {
    private Button btnAgent;
    SupportMapFragment mapFragment;
    GoogleMap googleMap;
    GPSTracker tracker;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_locator);
        try {
            init();
            checkLocationPermission();
            mapFragment.getMapAsync(this);
            btnAgent.setOnClickListener(this);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap=googleMap;
        checkLocationPermission();
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setMyLocationEnabled(true);
        moveCameraToLocation();
        LatLng sydney = new LatLng(33.6844, 73.0479);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));



    }

    private void init(){
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        btnAgent=findViewById(R.id.btn_agent);

    }
    public void moveCameraToLocation(){
        tracker=new GPSTracker(this);
        LatLng latLng=new LatLng(tracker.getLatitude(), tracker.getLongitude());
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(tracker.getLatitude(), tracker.getLongitude()))      // Sets the center of the map to location user
                .zoom(10)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        googleMap.addMarker(new MarkerOptions().position(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                            tracker=new GPSTracker(this);
                            moveCameraToLocation();
                        // getCurrentLocationAndDrawMarkers();
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.btn_agent:
                tracker=new GPSTracker(this);
                try{


                    ApiClient.getInstance().getAgents(new AgentBeanRequest(String.valueOf(tracker.getLatitude()),String.valueOf(tracker.getLongitude()),"5",1,this.getClass().getSimpleName()+".btn_agent.onClick"))
                            .enqueue(this);
                }catch (Exception e){
                    e.printStackTrace();
                    Helper.showAlertNetural(this,"Error",e.getMessage());
                }
            break;
        }
    }


   void getCurrentLocationAndDrawMarkers(AgentBeanResponse list) {
        try {
            GPSTracker gps = new GPSTracker(this);
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            if (latitude == 0.0) {
                latitude = 33.6844;
                longitude = 73.0479;

            }

            LatLng latLng = new LatLng(latitude, longitude);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

            // mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

            //move map camera
            // mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11));
            if (latitude > 0.0) {

                    googleMap.addMarker(new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)).title("Name"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latLng.latitude, latLng.longitude)));
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(11));


                if (list.getData() != null && list.getData().size() > 0) {
                    for (int i = 0; i < list.getData().size(); i++) {
                        //double distance = getDistance(latLng.latitude, latLng.longitude,
                        //Double.parseDouble(list.get(i).getLatitude()), Double.parseDouble(list.get(i).getLongitude()));
                        //if (distance < 5) {
                        createMarker(Double.parseDouble(list.getData().get(i).getLat()), Double.parseDouble(list.getData().get(i).getLng()), list.getData().get(i).getAddress(), list.getData().get(i).getAgentName()+"("+list.getData().get(i).getContact()+")");
                        //}
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Marker createMarker(double latitude, double longitude, String title, String snippet) {
        Marker marker = null;
        try {
            try {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(latitude, longitude));
                markerOptions.title(title);
               // Log.e("snippet--->",snippet);
                markerOptions.snippet("JS Representative: "+snippet);
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker));
                marker = googleMap.addMarker(markerOptions);
                //googleMap.setOnMarkerClickListener(this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return marker;
    }

    private void checkLocationPermission() {
        try {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    new AlertDialog.Builder(getApplicationContext())
                            .setTitle("Location Permission Needed")
                            .setMessage("This app needs the Location permission, please accept to use location functionality")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    try {
                                        //Prompt the user once explanation has been shown
                                        ActivityCompat.requestPermissions(AgentLocatorActivity.this,
                                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                                MY_PERMISSIONS_REQUEST_LOCATION);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            })
                            .create()
                            .show();


                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(AgentLocatorActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(Call<AgentBeanResponse> call, Response<AgentBeanResponse> response) {
        if(response.isSuccessful()){
            if(response.body().getResponseCode().equals("1001")){
                getCurrentLocationAndDrawMarkers(response.body());
            }else{
                Helper.showAlertNetural(AgentLocatorActivity.this,"Error",response.body().getMessage());
            }
        }else{
            try {
                Helper.showAlertNetural(AgentLocatorActivity.this,"Error",response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<AgentBeanResponse> call, Throwable t) {
        t.fillInStackTrace();
        Helper.showAlertNetural(AgentLocatorActivity.this,"Error",t.getMessage());
    }
}
