package com.example.mycarmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class CarAlertsActivity extends NavigationActivity {

    public LinearLayout alertBox1, alertBox2, alertBox3, alertBox4, noAlerts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Imposta il layout della pagina
        setContentView(R.layout.activity_car_alerts);

        // Inizializza gli elementi della pagina
        initViews();

        // Inizializza navbar e dati dell'utente
        initNavigation();

        // Imposta gli alert per la macchina corrente
        showCurrentCar();
    }

    private void initViews () {
        alertBox1 = findViewById(R.id.alertBox1);
        alertBox2 = findViewById(R.id.alertBox2);
        alertBox3 = findViewById(R.id.alertBox3);
        alertBox4 = findViewById(R.id.alertBox4);
        noAlerts = findViewById(R.id.noAlerts);
    }

    @Override
    protected void onCarChanged() {
        // Avvisi di esempio per ogni marca
        // [1: chiave auto, 2: liquido refrigerante, 3: olio, 4: liquido lavavetri]
        switch (currentCar.getBrand()) {
            case "BMW": showAlerts(false, false, false, false); break;
            case "Volkswagen": showAlerts(true, false, false, false); break;
            case "Jeep": showAlerts(false, true, true, true); break;
            case "Skoda": showAlerts(false, false, true, true); break;
            case "Dodge": showAlerts(true, true, true, true); break;
            case "Toyota": showAlerts(false, false, false, true); break;
            default: break;
        }
    }

    // Mostra gli avvisi indicati, oppure il messaggio di nessun avviso
    private void showAlerts(boolean alert1, boolean alert2, boolean alert3, boolean alert4) {
        alertBox1.setVisibility(alert1 ? View.VISIBLE : View.GONE);
        alertBox2.setVisibility(alert2 ? View.VISIBLE : View.GONE);
        alertBox3.setVisibility(alert3 ? View.VISIBLE : View.GONE);
        alertBox4.setVisibility(alert4 ? View.VISIBLE : View.GONE);

        boolean noAlert = !alert1 && !alert2 && !alert3 && !alert4;
        noAlerts.setVisibility(noAlert ? View.VISIBLE : View.GONE);
    }
}