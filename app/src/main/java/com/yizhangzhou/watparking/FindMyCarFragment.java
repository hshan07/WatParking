package com.yizhangzhou.watparking;

/**
 * Created by Shiina on 2016/6/15.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.MapsInitializer;

public class FindMyCarFragment extends Fragment {
    Boolean first=true;
    View major;
    int flag=0;
//    MapView mMapView;
//    private GoogleMap googleMap;
    //GPSTracker gps=new GPSTracker();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if(first == true) {
            // return inflater.inflate(R.layout.findmycar_layout, container, false);
            View rootView = inflater.inflate(R.layout.findmycar_layout, container, false);

            // new LoginOperation().execute();

            View view = inflater.inflate(R.layout.findmycar_layout, container, false);
//            mMapView = (MapView) view.findViewById(R.id.mapView);
//            mMapView.onCreate(savedInstanceState);


            final Button ping = (Button) view.findViewById(R.id.ping);

            final TextView text=(TextView) view.findViewById(R.id.textView1);
            ping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(flag==1) {
                        ping.setText("ping my car");
                        flag=0;

//                        Intent intent = new Intent(getActivity(), MapsActivity.class);
//                        intent.putExtra("Mycar","1");
//                        startActivity(intent);

//                        mMapView.onResume();// needed to get the map to display immediately

                        try {
                            MapsInitializer.initialize(getActivity().getApplicationContext());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//
//                        googleMap = mMapView.getMap();
                        // latitude and longitude
                        double latitude = 43.47031155;
                        double longitude =-80.54084139;

                        Intent intent = new Intent(getActivity(), MapsActivity.class);
                        intent.putExtra("Mycar","123");
                        startActivity(intent);
//
//                        // create marker
//                        MarkerOptions marker = new MarkerOptions().position(
//                                new LatLng(latitude, longitude)).title("Current Location");
//
//                        // Changing marker icon
//                        marker.icon(BitmapDescriptorFactory
//                                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
//
//                        // adding marker
//                        googleMap.addMarker(marker);
//
//                        CameraPosition cameraPosition = new CameraPosition.Builder()
//                                .target(new LatLng(43.47031155, -80.54084139)).zoom(12).build();
//                        googleMap.animateCamera(CameraUpdateFactory
//                                .newCameraPosition(cameraPosition));

                    }else{
                        ping.setText("find my car");
                        flag=1;
                    }

                }
            });
            first=false;
            major=view;
            return view;
        } else {
            return major;
        }

    }






//
//
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mMapView.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mMapView.onPause();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mMapView.onDestroy();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mMapView.onLowMemory();
//    }

}