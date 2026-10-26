package com.example.mycarmanager;

import static com.example.mycarmanager.LoginActivity.currentCarIndex;
import static com.example.mycarmanager.LoginActivity.currentUserIndex;
import static com.example.mycarmanager.User.users;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import de.hdodenhof.circleimageview.CircleImageView;

// Activity base delle pagine principali: gestisce la navbar laterale, la navbar in basso,
// i dati dell'utente nella navbar laterale e la selezione dell'auto corrente
public abstract class NavigationActivity extends ThemedActivity {

    protected User currentUser;
    protected Car currentCar;
    protected DrawerLayout drawerLayout;
    private NavigationView navMenu;
    private CircleImageView navbarProfilePic;
    private TextView navBarUsername, navbarEmail, carName, carPlate;

    // Inizializza navbar e dati dell'utente, da chiamare dopo setContentView
    protected void initNavigation() {
        // Recupera i dati dell'utente corrente
        currentUser = users.get(currentUserIndex);

        drawerLayout = findViewById(R.id.drawerLayout);
        navMenu = findViewById(R.id.nav_view);
        navbarProfilePic = findViewById(R.id.navbarProfilePic);
        navBarUsername = findViewById(R.id.navBarUsername);
        navbarEmail = findViewById(R.id.navbarEmail);
        carName = findViewById(R.id.carName);
        carPlate = findViewById(R.id.carPlate);

        // Listener tasto di apertura della navbar laterale [alto sx]
        findViewById(R.id.navMenuButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
                navMenu.bringToFront();
            }
        });

        // Pulsanti della navbar laterale
        initNavigationButton(R.id.navbarGarageButton, CarGarageActivity.class);
        initNavigationButton(R.id.navbarManageButton, CarManageActivity.class);
        initNavigationButton(R.id.navbarMapButton, CarMapActivity.class);
        initNavigationButton(R.id.navbarFeaturesButton, CarFeaturesActivity.class);
        initNavigationButton(R.id.navbarAlertsButton, CarAlertsActivity.class);
        initNavigationButton(R.id.navbarAccountButton, AccountActivity.class);
        initNavigationButton(R.id.navbarNewCarButton, ConnectionTutorialActivity.class);
        initNavigationButton(R.id.navbarColorCorrectionButton, ColorBlindActivity.class);

        // Pulsanti della navbar in basso
        initNavigationButton(R.id.garageButtonContainer, CarGarageActivity.class);
        initNavigationButton(R.id.manageButtonContainer, CarManageActivity.class);
        initNavigationButton(R.id.mapButtonContainer, CarMapActivity.class);
        initNavigationButton(R.id.featuresButtonContainer, CarFeaturesActivity.class);
        initNavigationButton(R.id.alertsButtonContainer, CarAlertsActivity.class);

        // Listener pulsante "Logout" [navbar laterale]
        findViewById(R.id.navbarLogoutButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        // Frecce di selezione dell'auto, presenti solo nelle pagine dedicate all'auto
        View garageLeftArrow = findViewById(R.id.garageLeftArrow);
        View garageRightArrow = findViewById(R.id.garageRightArrow);

        if (garageLeftArrow != null) {
            garageLeftArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    prevCar();
                }
            });
        }

        if (garageRightArrow != null) {
            garageRightArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nextCar();
                }
            });
        }

        updateUserData();

        if (!currentUser.getGarage().isEmpty())
            currentCar = currentUser.getGarage().get(currentCarIndex);
    }

    // Collega un pulsante all'apertura di una pagina, tranne che per la pagina corrente
    // Il garage è la pagina di base: le altre sezioni vengono aperte sopra il garage
    // una alla volta, quindi Back da una sezione riporta sempre al garage
    private void initNavigationButton(int buttonId, final Class<?> activityClass) {
        View button = findViewById(buttonId);

        if (button == null || activityClass == getClass())
            return;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavigationActivity.this, activityClass);

                // Si torna al garage già aperto invece di aprirne uno nuovo
                if (activityClass == CarGarageActivity.class)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);

                // La sezione corrente viene chiusa, il garage resta aperto sotto
                if (!(NavigationActivity.this instanceof CarGarageActivity))
                    finish();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // Tornando alla pagina, utente e auto corrente potrebbero essere cambiati in un'altra pagina
        currentUser = users.get(currentUserIndex);
        updateUserData();

        if (currentCar != null) {
            currentCar = currentUser.getGarage().get(currentCarIndex);
            showCurrentCar();
        }
    }

    // Aggiorna informazioni utente [navbar laterale]
    protected void updateUserData() {
        navBarUsername.setText(currentUser.getUsername());
        navbarEmail.setText(currentUser.getEmail());
        setProfileImage(navbarProfilePic);
    }

    // Mostra l'immagine del profilo: quella scelta dalla galleria oppure quella predefinita filtrata
    protected void setProfileImage(ImageView imageView) {
        if (currentUser.getImageUri() != null)
            imageView.setImageURI(Uri.parse(currentUser.getImageUri()));
        else
            setFilteredImage(imageView, currentUser.getImgResource());
    }

    protected void nextCar() {
        // Dopo l'ultima auto dell'elenco si torna alla prima
        currentCarIndex = (currentCarIndex + 1) % currentUser.getGarage().size();
        currentCar = currentUser.getGarage().get(currentCarIndex);
        showCurrentCar();
    }

    protected void prevCar() {
        // Prima della prima auto dell'elenco si passa all'ultima
        int numberOfCars = currentUser.getGarage().size();
        currentCarIndex = (currentCarIndex - 1 + numberOfCars) % numberOfCars;
        currentCar = currentUser.getGarage().get(currentCarIndex);
        showCurrentCar();
    }

    // Mostra nome e targa dell'auto corrente e aggiorna il resto della pagina
    protected void showCurrentCar() {
        if (carName != null)
            carName.setText(currentCar.getBrand() + " " + currentCar.getName());

        if (carPlate != null)
            carPlate.setText(currentCar.getPlate());

        onCarChanged();
    }

    // Aggiorna gli elementi della pagina che dipendono dall'auto corrente
    protected void onCarChanged() {}

    protected void logout() {
        startActivity(new Intent(this, LoginActivity.class));
        finishAffinity();
    }
}