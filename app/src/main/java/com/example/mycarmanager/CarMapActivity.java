package com.example.mycarmanager;

import androidx.annotation.NonNull;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CarMapActivity extends NavigationActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Imposta il layout della pagina
        setContentView(R.layout.activity_car_map);

        // Inizializza navbar e dati dell'utente
        initNavigation();

        // Aggiorna informazioni auto corrente
        showCurrentCar();

        // Impostazione della mappa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapActual);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onCarChanged() {
        updateMap();
    }

    private void updateMap() {
        // La mappa potrebbe non essere ancora pronta: in quel caso ci pensa onMapReady
        if (googleMap == null)
            return;

        // Rimuove il marker dell'auto precedente
        googleMap.clear();

        LatLng position = new LatLng(currentCar.getLatitude(), currentCar.getLongitude());
        String title = currentCar.getBrand() + " " + currentCar.getName();

        googleMap.addMarker(new MarkerOptions()
                .position(position)
                .title(title));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        updateMap();
    }
}