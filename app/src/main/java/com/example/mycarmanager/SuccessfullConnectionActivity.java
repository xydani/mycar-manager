package com.example.mycarmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import static com.example.mycarmanager.LoginActivity.currentUserIndex;
import androidx.appcompat.app.AppCompatActivity;
import static com.example.mycarmanager.User.users;
import java.util.ArrayList;
import java.util.Random;

public class SuccessfullConnectionActivity extends AppCompatActivity {
    public int selectedTheme;
    public SharedPreferences sharedPreferences;
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

        // Imposta il tema in base alle preferenze
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        selectedTheme = sharedPreferences.getInt("SelectedTheme", 1);
        changeTheme(selectedTheme);

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

    public void changeTheme (int newThemeId) {
        switch (newThemeId) {
            case 1:
                SuccessfullConnectionActivity.this.setTheme(R.style.BASE_THEME);
                break;
            case 2:
                SuccessfullConnectionActivity.this.setTheme(R.style.DEUTERAN_THEME);
                break;
            case 3:
                SuccessfullConnectionActivity.this.setTheme(R.style.PROTAN_THEME);
                break;
            case 4:
                SuccessfullConnectionActivity.this.setTheme(R.style.TRITAN_THEME);
                break;
        }
    }
}