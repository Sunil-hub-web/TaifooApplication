package com.example.taifooapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.taifooapplication.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class SerachAreaDetails extends AppCompatActivity implements OnMapReadyCallback {

    Button btn_select;
    TextView text_CurrentLocation;

    TextView textView1, textView2, textView3, textView4, textView5, textView6, text_MapView;
    FusedLocationProviderClient fusedLocationProviderClient;

    String str_Latitude, str_Longitude, str_Countryname, str_Locality, str_PostalCode, str_Address,id,userid;

    private GoogleMap mMap;
    Bitmap smallMarker;

    private Geocoder geocoder;

    SearchView searchView;

    private int FINE_LOCATION_ACCESS_REQUEST_CODE = 10001;
    LocationManager locationManager;

    String message;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach_area_details);

        getSupportActionBar().hide();


        searchView = findViewById(R.id.sv_location);

        btn_select = findViewById(R.id.verifay);
        text_CurrentLocation = findViewById(R.id.currentLocation);

        textView1 = findViewById(R.id.text_view1);
        textView2 = findViewById(R.id.text_view2);
        textView3 = findViewById(R.id.text_view3);
        textView4 = findViewById(R.id.text_view4);
        textView5 = findViewById(R.id.text_view5);
        textView6 = findViewById(R.id.text_view6);
        text_MapView = findViewById(R.id.mapView);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.geofence_map);
        mapFragment.getMapAsync(this);

        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.pin);
        Bitmap b = bitmapdraw.getBitmap();
        smallMarker = Bitmap.createScaledBitmap(b, 50, 50, false);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    //Write Function To enable gps
                    locationPermission();

                } else {
                    //GPS is already On then
                    String location = searchView.getQuery().toString();

                    List<Address> addressList = null;

                    if (location != null || !location.equals("")) {

                        Geocoder geocoder = new Geocoder(SerachAreaDetails.this);

                        try {
                            addressList = geocoder.getFromLocationName(location, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        //mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));

                        //view_address.setText(address.getAddressLine(0));

                        //set Latitude On Text View
                        textView1.setText(Html.fromHtml("<font color='#FFFFFF'><b>Latitude :</b><br></font>" + address.getLatitude()));

                        //set Longitude On Text View
                        textView2.setText(Html.fromHtml("<font color='#FFFFFF'><b>Longitude :</b><br></font>" + address.getLongitude()));

                        textView3.setText(Html.fromHtml("<font color='#FFFFFF'><b>CountryName :</b><br></font>" + address.getCountryName()));

                        //set Locality On Text View
                        textView4.setText(Html.fromHtml("<font color='#FFFFFF'><b>Locality :</b><br></font>" + address.getLocality()));

                        //set Locality On Text View
                        textView5.setText(Html.fromHtml("<font color='#FFFFFF'><b>PostalCode :</b><br></font>" + address.getPostalCode()));

                        //set address On Text View
                        textView6.setText(Html.fromHtml("<font color='#FFFFFF'><b>Address :</b><br></font>" + address.getAddressLine(0)));
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        enableUserLocation();

        //getTopLocation(googleMap);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(20.3490, 85.8077);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Dlf").icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        //mMap.moveCamera (CameraUpdateFactory.newLatLngZoom (sydney, 16));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17f));

        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);


        //googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        //Showing Current Location
        googleMap.setMyLocationEnabled(true); // false to disable


    }

    private void enableUserLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            //Ask for permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //We need to show user a dialog for displaying why the permission is needed and then ask for the permission...
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_LOCATION_ACCESS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //We have the permission
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                        (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mMap.setMyLocationEnabled(true);
            } else {
                //We do not have the permission..
            }
        }
    }
    public void locationPermission() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Enable Your GPS Location").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}