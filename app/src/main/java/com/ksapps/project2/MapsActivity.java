package com.ksapps.project2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double[] lat = new double[25];
    double[] lng = new double[25];
    int k;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle extras = getIntent().getExtras();
        String latitude = extras.getString("val1");
        String longitude = extras.getString("val2");
        String id = extras.getString("val3");
        assert latitude != null;
        String[] sep = latitude.split(":");
        assert longitude != null;
        String[] sep1 = longitude.split(":");
        k = Integer.parseInt(id);
        for (int i = 0; i < k; i++) {
            lat[i] = Double.parseDouble(sep[i]);
            lng[i] = Double.parseDouble(sep1[i]);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        for(int i=0;i<k;i++){
            markers(i);
        }
    }
    public void markers(int i){
        LatLng mumbai = new LatLng(lat[i], lng[i]);
        mMap.addMarker(new MarkerOptions().position(mumbai).title("Marker in Id "+i));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mumbai));
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 5.0f ) );
    }
}
