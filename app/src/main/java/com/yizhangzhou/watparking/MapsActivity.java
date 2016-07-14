package com.yizhangzhou.watparking;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.fitness.result.SyncInfoResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            if (getIntent().getStringExtra("Myclass") != null) {
                JSONObject jsonObj = new JSONObject(getIntent().getStringExtra("Myclass"));
                System.out.println(jsonObj.getString("building_code"));
                System.out.println("Here is another activity;;;;;;;;;;;;;;;;;");


                final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?" + "saddr=" + 43.472761 + "," + -80.542164 + "&daddr=" + jsonObj.getString("latitude") + "," + jsonObj.getString("longitude")));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
//            } else if (getIntent().getStringExtra("Mycar") != null) {
//                System.out.println(getIntent().getStringExtra("Mycar"));

//
//               GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
//                    @Override
//                    public void onMyLocationChange(Location location) {
//                        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
//
//                        mMap.addMarker(new MarkerOptions().position(loc).title("Current Location"));
//                        if(mMap != null){
//                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
//                        }
//                    }
//                };

                //  mMap.setOnMyLocationChangeListener(myLocationChangeListener);

//                GPSTracker gps=new GPSTracker(this);
//                LatLng Current = new LatLng(gps.getLatitude(), gps.getLongitude());
//
//                mMap.addMarker(new MarkerOptions().position(Current).title("Current Location"));
//                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Current, 16));
//            }
            }
        }catch( Exception e){
            e.printStackTrace();
        }


    }
}
