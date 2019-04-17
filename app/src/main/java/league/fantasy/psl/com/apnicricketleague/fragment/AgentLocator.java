package league.fantasy.psl.com.apnicricketleague.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.io.IOException;

import league.fantasy.psl.com.apnicricketleague.R;
import league.fantasy.psl.com.apnicricketleague.Utils.GPSTracker;
import league.fantasy.psl.com.apnicricketleague.Utils.Helper;
import league.fantasy.psl.com.apnicricketleague.client.ApiClient;
import league.fantasy.psl.com.apnicricketleague.model.response.Agent.AgentBeanResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgentLocator extends Fragment implements OnMapReadyCallback,View.OnClickListener, Callback<AgentBeanResponse> {
    private Button btnAgent;
    SupportMapFragment mapFragment;
    GoogleMap googleMap;
    GPSTracker gpsTracker;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    View mView;
    private Tracker tracker;
    public AgentLocator() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        tracker=Helper.getGoogleAnalytics(getActivity().getApplication());
        Helper.updateGoogleAnalytics(tracker,this.getClass().getSimpleName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        try {

            mView=inflater.inflate(R.layout.fragment_agent_locator, container, false);
            init();
            //checkLocationPermission();
            if(Helper.permissionAlreadyGranted(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION) && Helper.permissionAlreadyGranted(getApplicationContext(),Manifest.permission.ACCESS_COARSE_LOCATION)){
                mapFragment.getMapAsync(this);
                btnAgent.setOnClickListener(this);
            }else{
                requestPermission(getActivity());
            }


        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(mView.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return mView;
    }

    private void init(){
        mapFragment =  (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        btnAgent=mView.findViewById(R.id.btn_agent);

    }

    private void checkLocationPermission() {
        try {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
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
                                        ActivityCompat.requestPermissions(getActivity(),
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
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void moveCameraToLocation(){
        gpsTracker=new GPSTracker(getActivity());
        LatLng latLng=new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()))      // Sets the center of the map to location user
                .zoom(10)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        googleMap.addMarker(new MarkerOptions().position(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


    }
    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.btn_agent:
                gpsTracker=new GPSTracker(getActivity());
                try{
                    JSONObject object=new JSONObject();
                    object.put("lat",String.valueOf(gpsTracker.getLatitude()));
                    object.put("lng",String.valueOf(gpsTracker.getLongitude()));
                    object.put("dist","5");
                    object.put("userId",1001);
                    object.put("method_Name",this.getClass().getSimpleName()+".btn_agent.onClick");
                    Helper.trackEvent(tracker,"Button","onClick",this.getClass().getSimpleName()+".btn_agent");
                    ApiClient.getInstance().getAgents(Helper.encrypt(object.toString()))
                            .enqueue(this);
                }catch (Exception e){
                    e.printStackTrace();
                    Helper.showAlertNetural(mView.getContext(),"Error",e.getMessage());
                }
                break;
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap=googleMap;

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setMyLocationEnabled(true);
        /*moveCameraToLocation();
        LatLng sydney = new LatLng(33.6844, 73.0479);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Helper.permissionAlreadyGranted(getApplicationContext(),Manifest.permission.ACCESS_COARSE_LOCATION)) {
                //Location Permission already granted

                googleMap.setMyLocationEnabled(true);
                moveCameraToLocation();
                //getCurrentLocationAndDrawMarkers();
            } else {
                //Request Location Permission
                requestPermission(getActivity());
            }
        } else {

            googleMap.setMyLocationEnabled(true);
            moveCameraToLocation();
            //getCurrentLocationAndDrawMarkers();
        }
    }

    private void requestPermission(Activity activity) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION)) {

        }
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
        if(Helper.permissionAlreadyGranted(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION)){
            mapFragment.getMapAsync(this);
            btnAgent.setOnClickListener(this);
        }
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
                    gpsTracker=new GPSTracker(mView.getContext());
                    moveCameraToLocation();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(mView.getContext(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    public void onResponse(Call<AgentBeanResponse> call, Response<AgentBeanResponse> response) {
        if(response.isSuccessful()){
            if(response.body().getResponseCode().equals("1001")){
                getCurrentLocationAndDrawMarkers(response.body());
            }else{
                Helper.showAlertNetural(mView.getContext(),"Error",response.body().getMessage());
            }
        }else{
            try {
                Helper.showAlertNetural(mView.getContext(),"Error",response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<AgentBeanResponse> call, Throwable t) {
        t.fillInStackTrace();
        Helper.showAlertNetural(mView.getContext(),"Error",t.getMessage());
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

    void getCurrentLocationAndDrawMarkers(AgentBeanResponse list) {
        try {
            GPSTracker gps = new GPSTracker(mView.getContext());
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
}
