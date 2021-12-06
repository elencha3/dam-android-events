package com.curso.eventsregister;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button button;
    RecyclerView recyclerView;
    TextView titleTV;
    TextView eventsTV;
    MyAdapter myAdapter;
    String name;
    String place;
    String date;
    String time;
    public static ArrayList<String> eventos = new ArrayList<>();
    LatLng coordinates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        titleTV = findViewById(R.id.titleTV);
        eventsTV = findViewById(R.id.eventsTitleTV);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(this, eventos);
        recyclerView.setAdapter(myAdapter);

        setEvent();


        button = findViewById(R.id.eventBT);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DateHour.class);
                startActivityForResult(intent, 2);
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.getCameraPosition();
        activateLocation();
        if (place != null) {
            geocode();
        }

    }


    public void activateLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            String[] permisions = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(this, permisions, 1);
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == 1 && permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            activateLocation();
        }
    }

    public void geocode() {
        Geocoder geocoder = new Geocoder(this);

        try {
            List<Address> addressList = geocoder.getFromLocationName(place, 5);

            if (addressList.size() != 0) {
                Address direction = addressList.get(0);
                coordinates = new LatLng(direction.getLatitude(), direction.getLongitude());
                mMap.addMarker(new MarkerOptions().position(coordinates).title(place));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates));
                mMap.getCameraPosition();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setEvent() {

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        place = intent.getStringExtra("place");
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");

        if (name != null && date != null && time != null) {
            eventos.add(name + "\n" + date + " " + time);
            myAdapter.notifyDataSetChanged();
        }

    }

}