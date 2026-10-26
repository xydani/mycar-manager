package com.example.mycarmanager;

import androidx.core.view.GravityCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import java.util.Locale;

public class CarGarageActivity extends NavigationActivity {
    // Dichiarazione di variabili di istanza
    private TextView carBrandText, carFuelText, carTypeText;
    private LinearLayout carOverlay;
    private ImageView carImage, carBrandImage, carFuelImage, carTypeImage;
    private MaterialButton newCarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Imposta il layout della pagina
        setContentView(R.layout.activity_car_garage);

        // Inizializza gli elementi della pagina
        initViews();

        // Inizializza navbar e dati dell'utente
        initNavigation();

        // Aggiorna informazioni auto corrente
        showCurrentCar();

        // Inizializzazione listeners
        initListeners();
    }

    private void initViews() {
        carBrandImage = findViewById(R.id.carBrandImage);
        carBrandText = findViewById(R.id.carBrandText);
        carFuelImage = findViewById(R.id.carFuelImage);
        carFuelText = findViewById(R.id.carFuelText);
        carImage = findViewById(R.id.carImage);
        carOverlay = findViewById(R.id.carOverlay);
        carTypeImage = findViewById(R.id.carTypeImage);
        carTypeText = findViewById(R.id.carTypeText);
        newCarButton = findViewById(R.id.newCarButton);
    }

    private void initListeners(){
        // Listener scorrimento dito selezione auto
        carOverlay.setOnTouchListener(new View.OnTouchListener() {
            float x1, x2;
            static final int MIN_DISTANCE = 150;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        break;

                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        float deltaX = x2 - x1;

                        if (Math.abs(deltaX) > MIN_DISTANCE) {
                            if (x2 > x1) { prevCar(); }
                            else { nextCar(); }
                        }
                        break;
                }
                return true;
            }
        });

        // Listener pulsante aggiungi auto [view]
        newCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToCarConnectionActivity;
                goToCarConnectionActivity = new Intent(CarGarageActivity.this, ConnectionTutorialActivity.class);
                startActivity(goToCarConnectionActivity);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    protected void onCarChanged() {
        // Immagine dell'auto, con il filtro del tema scelto
        setFilteredImage(carImage, currentCar.getCarImageResource());

        // Marca, carburante e tipo di auto
        carBrandImage.setImageResource(currentCar.getBrandLogoResource());
        carBrandText.setText(currentCar.getBrand());
        carFuelImage.setImageResource(currentCar.getFuelImageResource());
        carFuelText.setText(capitalize(currentCar.getFuelType()));
        carTypeImage.setImageResource(currentCar.getCarTypeImageResource());
        carTypeText.setText(capitalize(currentCar.getCarType()));
    }

    // "diesel" -> "Diesel"
    private static String capitalize(String text) {
        return text.substring(0, 1).toUpperCase(Locale.ROOT) + text.substring(1);
    }
}