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
        if (savedInstanceState == null) {
            boolean carAdded;
            do {
                carAdded = addRandomCar();
            } while (!carAdded);
        }

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

    // Metodo che aggiunge un auto random al garage dell'utente
    public boolean addRandomCar(){

        boolean alreadyIn = false;
        boolean added = false;
        int numberOfCars = users.get(currentUserIndex).getGarage().size();
        Random rand = new Random();
        int n = rand.nextInt(6);

        if(numberOfCars == 0){

            switch (n) {
                case 0:
                    Car volkswagenPolo = new Car("Volkswagen", "Polo", "volkswagenPolo",
                            "GH236FF", "petrol", "sedan", "5", "1478",
                            "5", "abs", "5", "22", "103", "76",
                            "10.9", "98", 39.222334, 9.114042,
                            true, 100, new AirConditioning(true,
                            18.0f, 3), true, new Radio(true,
                            103.2f, 25), false);
                    users.get(currentUserIndex).getGarage().add(volkswagenPolo);
                    added = true;
                    break;

                case 1:
                    Car bmwI3 = new Car("BMW", "I3", "bmwI3",
                            "GK211TR", "electric", "citycar", "5", "1345",
                            "5", "abs", "5", "12.9", "9.3", "170",
                            "7.1", "67", 39.222334, 9.114042,
                            false, 95, new AirConditioning(false,
                            0f, 0), false, new Radio(false,
                            0f, 0), false);
                    users.get(currentUserIndex).getGarage().add(bmwI3);
                    added = true;
                    break;

                case 2:
                    Car jeepCherokee = new Car("Jeep", "Cherokee", "jeepCherokee",
                            "GS011FA", "diesel", "suv", "5", "2097",
                            "5", "abs", "6", "15.7", "385", "380",
                            "6.3", "87", 39.222334, 9.114042,
                            true, 0, new AirConditioning(false,
                            0f, 0), false, new Radio(true,
                            69.2f, 10), true);
                    users.get(currentUserIndex).getGarage().add(jeepCherokee);
                    added = true;
                    break;

                case 3:
                    Car skodaEnyaq = new Car("Skoda", "Enyaq", "skodaEniaq",
                            "PD007DS", "electric", "suv", "5", "2120",
                            "5", "abs", "6", "19.6", "112", "177",
                            "8.5", "50", 39.88222501781158, 8.60992834858646,
                            false, 25, new AirConditioning(false,
                            0f, 0), true, new Radio(true,
                            88.5f, 21), true);
                    users.get(currentUserIndex).getGarage().add(skodaEnyaq);
                    added = true;
                    break;

                case 4:
                    Car dodgeChallenger = new Car("Dodge", "Challenger", "dodgeChallenger",
                            "EL334BK", "petrol", "sportive", "3", "2032",
                            "5", "abs", "8", "14.6", "232", "375",
                            "6.8", "25", 40.96683774356043, 8.208346721299286,
                            true, 0, new AirConditioning(false,
                            0f, 0), false, new Radio(false,
                            0f, 0), false);
                    users.get(currentUserIndex).getGarage().add(dodgeChallenger);
                    added = true;
                    break;

                case 5:
                    Car toyotaYaris = new Car("Toyota", "Yaris", "toyotaYaris",
                            "BR772WF", "diesel", "citycar", "3", "1170",
                            "5", "abs", "5", "8.2", "92", "116",
                            "11.2", "98", 40.91576913643203, 9.518095495148964,
                            true, 100, new AirConditioning(true,
                            16f, 3), false, new Radio(false,
                            0f, 0), true);
                    users.get(currentUserIndex).getGarage().add(toyotaYaris);
                    added = true;
                    break;

                default:break;
            }

        } else {

            switch (n) {
                case 0:
                    Car volkswagenPolo = new Car("Volkswagen", "Polo", "volkswagenPolo",
                            "GH236FF", "petrol", "sedan", "5", "1478",
                            "5", "abs", "5", "22", "103", "76",
                            "10.9", "98", 39.222334, 9.114042,
                            true, 100, new AirConditioning(true,
                            18.0f, 3), true, new Radio(true,
                            103.2f, 25), false);
                    for (int i = 0; i < numberOfCars; i++) {
                        alreadyIn = users.get(currentUserIndex).getGarage().get(i).getPlate().equals(volkswagenPolo.getPlate());
                        if(alreadyIn)
                            break;
                    }
                    if (!alreadyIn) {
                        users.get(currentUserIndex).getGarage().add(volkswagenPolo);
                        added = true;
                    }
                    break;
                case 1:
                    Car bmwI3 = new Car("BMW", "I3", "bmwI3",
                            "GK211TR", "electric", "citycar", "5", "1345",
                            "5", "abs", "5", "12.9", "9.3", "170",
                            "7.1", "67", 39.222334, 9.114042,
                            false, 95, new AirConditioning(false,
                            0f, 0), false, new Radio(false,
                            0f, 0), false);
                    for (int i = 0; i < numberOfCars; i++) {
                        alreadyIn = users.get(currentUserIndex).getGarage().get(i).getPlate().equals(bmwI3.getPlate());
                        if(alreadyIn)
                            break;
                    }
                    if (!alreadyIn){
                        users.get(currentUserIndex).getGarage().add(bmwI3);
                        added = true;
                    }
                    break;
                case 2:
                    Car jeepCherokee = new Car("Jeep", "Cherokee", "jeepCherokee",
                            "GS011FA", "diesel", "suv", "5", "2097",
                            "5", "abs", "6", "15.7", "385", "380",
                            "6.3", "87", 39.222334, 9.114042,
                            true, 0, new AirConditioning(false,
                            0f, 0), false, new Radio(true,
                            69.2f, 10), true);
                    for (int i = 0; i < numberOfCars; i++){
                        alreadyIn = users.get(currentUserIndex).getGarage().get(i).getPlate().equals(jeepCherokee.getPlate());
                        if (alreadyIn)
                            break;
                    }
                    if(!alreadyIn) {
                        users.get(currentUserIndex).getGarage().add(jeepCherokee);
                        added = true;
                    }
                    break;

                case 3:
                    Car skodaEnyaq = new Car("Skoda", "Enyaq", "skodaEniaq",
                            "PD007DS", "electric", "suv", "5", "2120",
                            "5", "abs", "6", "19.6", "112", "177",
                            "8.5", "50", 39.88222501781158, 8.60992834858646,
                            false, 25, new AirConditioning(false,
                            0f, 0), true, new Radio(true,
                            88.5f, 21), true);
                    for (int i = 0; i < numberOfCars; i++){
                        alreadyIn = users.get(currentUserIndex).getGarage().get(i).getPlate().equals(skodaEnyaq.getPlate());
                        if (alreadyIn)
                            break;
                    }
                    if(!alreadyIn) {
                        users.get(currentUserIndex).getGarage().add(skodaEnyaq);
                        added = true;
                    }
                    break;

                case 4:
                    Car dodgeChallenger = new Car("Dodge", "Challenger", "dodgeChallenger",
                            "EL334BK", "petrol", "sportive", "3", "2032",
                            "5", "abs", "8", "14.6", "232", "375",
                            "6.8", "25", 40.96683774356043, 8.208346721299286,
                            true, 0, new AirConditioning(false,
                            0f, 0), false, new Radio(false,
                            0f, 0), false);
                    for (int i = 0; i < numberOfCars; i++){
                        alreadyIn = users.get(currentUserIndex).getGarage().get(i).getPlate().equals(dodgeChallenger.getPlate());
                        if (alreadyIn)
                            break;
                    }
                    if(!alreadyIn) {
                        users.get(currentUserIndex).getGarage().add(dodgeChallenger);
                        added = true;
                    }
                    break;

                case 5:
                    Car toyotaYaris = new Car("Toyota", "Yaris", "toyotaYaris",
                            "BR772WF", "diesel", "citycar", "3", "1170",
                            "5", "abs", "5", "8.2", "92", "116",
                            "11.2", "98", 40.91576913643203, 9.518095495148964,
                            true, 100, new AirConditioning(true,
                            16f, 3), false, new Radio(false,
                            0f, 0), true);
                    for (int i = 0; i < numberOfCars; i++){
                        alreadyIn = users.get(currentUserIndex).getGarage().get(i).getPlate().equals(toyotaYaris.getPlate());
                        if (alreadyIn)
                            break;
                    }
                    if(!alreadyIn) {
                        users.get(currentUserIndex).getGarage().add(toyotaYaris);
                        added = true;
                    }
                    break;

                default:break;
            }
        }

        return added;
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