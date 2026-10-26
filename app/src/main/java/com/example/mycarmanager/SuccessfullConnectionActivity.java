package com.example.mycarmanager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import static com.example.mycarmanager.LoginActivity.currentUserIndex;
import static com.example.mycarmanager.User.users;
import java.util.ArrayList;
import java.util.Random;

public class SuccessfullConnectionActivity extends ThemedActivity {
    private final Handler handler = new Handler(Looper.getMainLooper());

    // Porta al garage, chiudendo questa pagina e le eventuali pagine aperte sopra il garage
    private final Runnable goToGarage = new Runnable() {
        @Override
        public void run() {
            Intent goToHomePage;
            goToHomePage = new Intent(SuccessfullConnectionActivity.this, CarGarageActivity.class);
            goToHomePage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(goToHomePage);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Imposta il layout della pagina
        setContentView(R.layout.activity_successfull_connection);

        // Aggiunta di un auto casuale al garage dell'utente
        // (solo alla prima creazione, non quando l'activity viene ricreata, es. rotazione)
        if (savedInstanceState == null)
            addRandomCar();

        // Secondi di caricamento dopo la prima connessione
        // prima di essere indirizzati al garage
        handler.postDelayed(goToGarage, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Evita che una pagina già chiusa apra il garage
        handler.removeCallbacks(goToGarage);
    }

    // Aggiunge al garage dell'utente un'auto casuale del catalogo, tra quelle non ancora presenti
    public void addRandomCar(){
        ArrayList<Car> garage = users.get(currentUserIndex).getGarage();
        ArrayList<Car> availableCars = new ArrayList<>();

        for (Car car : Car.getCatalog()) {
            boolean alreadyIn = false;

            for (Car userCar : garage) {
                if (userCar.getPlate().equals(car.getPlate())) {
                    alreadyIn = true;
                    break;
                }
            }

            if (!alreadyIn)
                availableCars.add(car);
        }

        // Il garage pieno viene già gestito nella selezione del dispositivo
        if (!availableCars.isEmpty())
            garage.add(availableCars.get(new Random().nextInt(availableCars.size())));
    }
}