package com.example.mycarmanager;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CarFeaturesActivity extends NavigationActivity {

    private ImageView fuelLevelImage;
    private TextView carAcceleration, carConsumptionData, carDoors, carEmissions, carGears,
            carSeats, carWeight, engineHP, fuelLevelActual, fuelLevelTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Imposta il layout della pagina
        setContentView(R.layout.activity_car_features);

        // Inizializza gli elementi della pagina
        initViews();

        // Inizializza navbar e dati dell'utente
        initNavigation();

        // Aggiorna informazioni auto corrente
        showCurrentCar();
    }

    private void initViews() {
        carAcceleration = findViewById(R.id.carAccelleration);
        carConsumptionData = findViewById(R.id.carConsumptionData);
        carDoors = findViewById(R.id.carDoors);
        carEmissions = findViewById(R.id.carEmissions);
        carGears = findViewById(R.id.carGears);
        carSeats = findViewById(R.id.carSeats);
        carWeight = findViewById(R.id.carWeight);
        engineHP = findViewById(R.id.engineHP);
        fuelLevelActual = findViewById(R.id.fuelLevelActual);
        fuelLevelImage = findViewById(R.id.fuelLevelImage);
        fuelLevelTitle = findViewById(R.id.fuelLevelTitle);
    }

    @Override
    protected void onCarChanged() {
        // Aggiornamento dati Features
        carDoors.setText(currentCar.getDoors());
        carWeight.setText(currentCar.getWeight() + " Kg");
        carSeats.setText(currentCar.getSeats());
        carAcceleration.setText(currentCar.getAcceleration() + "s");
        carGears.setText(currentCar.getGears());
        engineHP.setText(currentCar.getHorsepower() + "HP");
        carEmissions.setText(currentCar.getEmissions() + "g/1Km");

        if(currentCar.getFuelType().equals("electric")) {
            carConsumptionData.setText(currentCar.getConsumption() + "kWh/100Km");
            fuelLevelTitle.setText("Energy level");
            fuelLevelImage.setImageResource(R.drawable.garage1_electriccar_img);
        }
        else {
            carConsumptionData.setText(currentCar.getConsumption() + "L/100Km");
            fuelLevelTitle.setText("Fuel level");
            fuelLevelImage.setImageResource(R.drawable.stats_engine_fuellevel);
        }

        fuelLevelActual.setText(currentCar.getFuelOrEnergyLevel() + "%");
    }
}