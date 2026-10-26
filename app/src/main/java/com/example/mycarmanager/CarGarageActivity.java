package com.example.mycarmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import de.hdodenhof.circleimageview.CircleImageView;
import static com.example.mycarmanager.LoginActivity.currentCarIndex;
import static com.example.mycarmanager.LoginActivity.currentUserIndex;
import static com.example.mycarmanager.User.users;

public class CarGarageActivity extends AppCompatActivity {
    // Dichiarazione di variabili di istanza
    private User currentUser;
    private Car currentCar;
    private TextView navBarUsername, navbarEmail, carName, carPlate, carBrandText;
    private TextView carFuelText, carTypeText;
    private LinearLayout navMenuButton, bottomNavbarGarageButton, carOverlay;
    private LinearLayout bottomNavbarManageButton, bottomNavbarMapButton, bottomNavbarFeaturesButton,
            bottomNavbarAlertsButton;
    private DrawerLayout drawerLayout;
    private NavigationView navMenu;
    private CircleImageView navbarProfilePic;
    private ImageView carImage, garageLeftArrow, garageRightArrow, carBrandImage;
    private ImageView carFuelImage, carTypeImage;
    private MaterialButton navbarGarageButton, navbarManageButton, navbarMapButton, navbarFeaturesButton,
            navbarAccountButton, navbarNewCarButton, navbarColorCorrectionButton,
            navbarLogoutButton, newCarButton, navbarAlertsButton;

    public SharedPreferences sharedPreferences;
    public int selectedTheme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Imposta il tema in base alle preferenze
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        selectedTheme = sharedPreferences.getInt("SelectedTheme", 1);
        changeTheme(selectedTheme);

        // Imposta il layout della pagina
        setContentView(R.layout.activity_car_garage);

        // Inizializza gli elementi della pagina
        initViews();

        // Recupera i dati dell'utente corrente
        currentUser = users.get(currentUserIndex);

        // Aggiorna i dati dell'utente
        updateData(selectedTheme);

        // Inizializzazione listeners
        initListeners();
    }

    private void initViews() {
        bottomNavbarAlertsButton = findViewById(R.id.alertsButtonContainer);
        bottomNavbarFeaturesButton = findViewById(R.id.featuresButtonContainer);
        bottomNavbarGarageButton = findViewById(R.id.garageButtonContainer);
        bottomNavbarManageButton = findViewById(R.id.manageButtonContainer);
        bottomNavbarMapButton = findViewById(R.id.mapButtonContainer);
        carBrandImage = findViewById(R.id.carBrandImage);
        carBrandText = findViewById(R.id.carBrandText);
        carFuelImage = findViewById(R.id.carFuelImage);
        carFuelText = findViewById(R.id.carFuelText);
        carImage = findViewById(R.id.carImage);
        carName = findViewById(R.id.carName);
        carOverlay = findViewById(R.id.carOverlay);
        carPlate = findViewById(R.id.carPlate);
        carTypeImage = findViewById(R.id.carTypeImage);
        carTypeText = findViewById(R.id.carTypeText);
        drawerLayout = findViewById(R.id.drawerLayout);
        garageLeftArrow = findViewById(R.id.garageLeftArrow);
        garageRightArrow = findViewById(R.id.garageRightArrow);
        navBarUsername = findViewById(R.id.navBarUsername);
        navMenu = findViewById(R.id.nav_view);
        navMenuButton = findViewById(R.id.navMenuButton);
        navbarAccountButton = findViewById(R.id.navbarAccountButton);
        navbarAlertsButton = findViewById(R.id.navbarAlertsButton);
        navbarColorCorrectionButton = findViewById(R.id.navbarColorCorrectionButton);
        navbarEmail = findViewById(R.id.navbarEmail);
        navbarFeaturesButton = findViewById(R.id.navbarFeaturesButton);
        navbarGarageButton = findViewById(R.id.navbarGarageButton);
        navbarLogoutButton = findViewById(R.id.navbarLogoutButton);
        navbarManageButton = findViewById(R.id.navbarManageButton);
        navbarMapButton = findViewById(R.id.navbarMapButton);
        navbarNewCarButton = findViewById(R.id.navbarNewCarButton);
        navbarProfilePic = findViewById(R.id.navbarProfilePic);
        newCarButton = findViewById(R.id.newCarButton);
    }

    private void initListeners(){
        // Listener freccia selezione auto [dx]
        garageRightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextCar(selectedTheme);
            }
        });

        // Listener freccia selezione auto [sx]
        garageLeftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prevCar(selectedTheme);
            }
        });

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
                            if (x2 > x1) { prevCar(selectedTheme); }
                            else { nextCar(selectedTheme); }
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

        // Listener tasto di apertura della navbar laterale [alto sx]
        navMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer();
            }
        });

        // Listener pulsante "Logout" [navbar laterale]
        navbarLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;
                intent = new Intent(CarGarageActivity.this, LoginActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        // Listener pulsante "Color Correction" [navbar laterale]
        navbarColorCorrectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;
                intent = new Intent(CarGarageActivity.this, ColorBlindActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "New Car" [navbar laterale]
        navbarNewCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;
                intent = new Intent(CarGarageActivity.this, ConnectionTutorialActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Account" [navbar laterale]
        navbarAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;
                intent = new Intent(CarGarageActivity.this, AccountActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Features" [navbar laterale]
        navbarFeaturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;
                intent = new Intent(CarGarageActivity.this, CarFeaturesActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Map" [navbar laterale]
        navbarMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;
                intent = new Intent(CarGarageActivity.this, CarMapActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Manage" [navbar laterale]
        navbarManageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;
                intent = new Intent(CarGarageActivity.this, CarManageActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Alerts" [navbar laterale]
        navbarAlertsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(CarGarageActivity.this, CarAlertsActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Manage" [navbar in basso]
        bottomNavbarManageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goToManageActivity;
                goToManageActivity = new Intent(CarGarageActivity.this, CarManageActivity.class);
                startActivity(goToManageActivity);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Map" [navbar in basso]
        bottomNavbarMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goToMapActivity;
                goToMapActivity = new Intent(CarGarageActivity.this, CarMapActivity.class);
                startActivity(goToMapActivity);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Features" [navbar in basso]
        bottomNavbarFeaturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goToFeaturesActivity;
                goToFeaturesActivity = new Intent(CarGarageActivity.this, CarFeaturesActivity.class);
                startActivity(goToFeaturesActivity);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Alerts" [navbar in basso]
        bottomNavbarAlertsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goToFeaturesActivity;
                goToFeaturesActivity = new Intent(CarGarageActivity.this, CarAlertsActivity.class);
                startActivity(goToFeaturesActivity);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }

    public void nextCar (int filter) {
        // Verifica se l'auto corrente è l'ultima dell'elenco
        if(currentCarIndex == (currentUser.getGarage().size()-1)) {
            currentCar = currentUser.getGarage().get(0);
            currentCarIndex = 0;
        }
        else {
            currentCar = currentUser.getGarage().get(currentCarIndex+1);
            currentCarIndex = currentCarIndex+1;
        }

        // Aggiorna informazioni auto corrente
        updateCarInformations(filter);
    }

    public void prevCar (int filter) {
        // Verifica se l'auto corrente è la prima dell'elenco
        if(currentCarIndex == (0)) {
            currentCar = currentUser.getGarage().get(currentUser.getGarage().size()-1);
            currentCarIndex = currentUser.getGarage().size()-1;
        }
        else {
            currentCar = currentUser.getGarage().get(currentCarIndex-1);
            currentCarIndex = currentCarIndex-1;
        }

        // Aggiorna informazioni auto corrente
        updateCarInformations(filter);
    }

    public void updateData(int filter) {
        // Aggiorna informazioni utente [navbar laterale]
        navBarUsername.setText(currentUser.getUsername());
        navbarEmail.setText(currentUser.getEmail());

        //Immagine del profilo, sia navbar che main page
        Uri uri;
        String stringUri;
        if((stringUri = currentUser.getImageUri()) != null) {
            uri = Uri.parse(stringUri);
            navbarProfilePic.setImageURI(uri);
        } else {
            // Imposta immagine del profilo
            switch (currentUser.getImgPath()) {
                case "default_profile_pic":
                    switch (filter) {
                        case 1:
                            updatePageColors("profile", 1);
                            break;
                        case 2:
                            updatePageColors("profile", 2);
                            break;
                        case 3:
                            updatePageColors("profile", 3);
                            break;
                        case 4:
                            updatePageColors("profile", 4);
                            break;
                    }
                    break;

                case "profile_pic_2":
                    switch (filter) {
                        case 1:
                            updatePageColors("profile", 5);
                            break;
                        case 2:
                            updatePageColors("profile", 6);
                            break;
                        case 3:
                            updatePageColors("profile", 7);
                            break;
                        case 4:
                            updatePageColors("profile", 8);
                            break;
                    }
                    break;

                case "profile_pic_3":
                    switch (filter) {
                        case 1:
                            updatePageColors("profile", 9);
                            break;
                        case 2:
                            updatePageColors("profile", 10);
                            break;
                        case 3:
                            updatePageColors("profile", 11);
                            break;
                        case 4:
                            updatePageColors("profile", 12);
                            break;
                    }
                    break;

                case "profile_pic4":
                    switch (filter) {
                        case 1:
                            updatePageColors("profile", 13);
                            break;
                        case 2:
                            updatePageColors("profile", 14);
                            break;
                        case 3:
                            updatePageColors("profile", 15);
                            break;
                        case 4:
                            updatePageColors("profile", 16);
                            break;
                    }
                    break;

                case "profile_pic_5":
                    switch (filter) {
                        case 1:
                            updatePageColors("profile", 17);
                            break;
                        case 2:
                            updatePageColors("profile", 18);
                            break;
                        case 3:
                            updatePageColors("profile", 19);
                            break;
                        case 4:
                            updatePageColors("profile", 20);
                            break;
                    }
                    break;

                case "profile_pic_6":
                    switch (filter) {
                        case 1:
                            updatePageColors("profile", 21);
                            break;
                        case 2:
                            updatePageColors("profile", 22);
                            break;
                        case 3:
                            updatePageColors("profile", 23);
                            break;
                        case 4:
                            updatePageColors("profile", 24);
                            break;
                    }
                    break;

                default:
                    break;
            }
        }

        // Aggiorna informazioni auto corrente
        currentCar = currentUser.getGarage().get(currentCarIndex);
        updateCarInformations(filter);
    }

    private void updateCarInformations(int filter) {
        // Informazioni generali dell'auto
        String carBrandName = currentCar.getBrand() + " " + currentCar.getName();
        carName.setText(carBrandName);
        carPlate.setText(currentCar.getPlate());

        // Impostazione logo automobile
        switch (currentCar.getBrand()) {
            case "BMW":
                switch(filter) {
                    case 1: updatePageColors("car", 1); break;
                    case 2: updatePageColors("car", 2); break;
                    case 3: updatePageColors("car", 3); break;
                    case 4: updatePageColors("car", 4); break;
                }
                carBrandImage.setImageResource(R.drawable.logo_bmw_img);
                carBrandText.setText("BMW");
                break;

            case "Volkswagen":
                switch(filter) {
                    case 1: updatePageColors("car", 5); break;
                    case 2: updatePageColors("car", 6); break;
                    case 3: updatePageColors("car", 7); break;
                    case 4: updatePageColors("car", 8); break;
                }
                carBrandImage.setImageResource(R.drawable.logo_volkswagen_img);
                carBrandText.setText("Volkswagen");
                break;

            case "Jeep":
                switch(filter) {
                    case 1: updatePageColors("car", 9); break;
                    case 2: updatePageColors("car", 10); break;
                    case 3: updatePageColors("car", 11); break;
                    case 4: updatePageColors("car", 12); break;
                }
                carBrandImage.setImageResource(R.drawable.logo_jeep_img);
                carBrandText.setText("Jeep");
                break;

            case "Skoda":
                switch(filter) {
                    case 1: updatePageColors("car", 13); break;
                    case 2: updatePageColors("car", 14); break;
                    case 3: updatePageColors("car", 15); break;
                    case 4: updatePageColors("car", 16); break;
                }
                carBrandImage.setImageResource(R.drawable.logo_skoda_img);
                carBrandText.setText("Skoda");
                break;

            case "Dodge":
                switch(filter) {
                    case 1: updatePageColors("car", 17); break;
                    case 2: updatePageColors("car", 18); break;
                    case 3: updatePageColors("car", 19); break;
                    case 4: updatePageColors("car", 20); break;
                }
                carBrandImage.setImageResource(R.drawable.logo_dodge_img);
                carBrandText.setText("Dodge");
                break;

            case "Toyota":
                switch(filter) {
                    case 1: updatePageColors("car", 21); break;
                    case 2: updatePageColors("car", 22); break;
                    case 3: updatePageColors("car", 23); break;
                    case 4: updatePageColors("car", 24); break;
                }
                carBrandImage.setImageResource(R.drawable.logo_toyota_img);
                carBrandText.setText("Toyota");
                break;

            default: break;
        }

        // Impostazione logo tipo carburante
        switch (currentCar.getFuelType()) {
            case "diesel":
                carFuelText.setText("Diesel");
                carFuelImage.setImageResource(R.drawable.garage1_dieselcar_img);
                break;

            case "petrol":
                carFuelText.setText("Petrol");
                carFuelImage.setImageResource(R.drawable.garage1_petrolcar_img);
                break;

            case "electric":
                carFuelText.setText("Electric");
                carFuelImage.setImageResource(R.drawable.garage1_electriccar_img);
                break;

            default: break;
        }

        // Impostazione logo tipo auto
        switch (currentCar.getCarType()) {
            case "sedan":
                carTypeText.setText("Sedan");
                carTypeImage.setImageResource(R.drawable.garage_sedancar_front_icon);
                break;

            case "suv":
                carTypeText.setText("Suv");
                carTypeImage.setImageResource(R.drawable.garage_suv_front_icon);
                break;

            case "citycar":
                carTypeText.setText("Citycar");
                carTypeImage.setImageResource(R.drawable.garage_citycar_front_icon);
                break;

            case "sportive":
                carTypeText.setText("Sportive");
                carTypeImage.setImageResource(R.drawable.garage_sportivecar_front_icon);
                break;

            default: break;
        }
    }

    private void openDrawer() {
        // Apre la navbar laterale
        drawerLayout.openDrawer(GravityCompat.START);
        navMenu.bringToFront();
    }

    public void changeTheme (int newThemeId) {
        switch (newThemeId) {
            case 1:
                CarGarageActivity.this.setTheme(R.style.BASE_THEME);
                break;
            case 2:
                CarGarageActivity.this.setTheme(R.style.DEUTERAN_THEME);
                break;
            case 3:
                CarGarageActivity.this.setTheme(R.style.PROTAN_THEME);
                break;
            case 4:
                CarGarageActivity.this.setTheme(R.style.TRITAN_THEME);
                break;
        }
    }

    public void updatePageColors(String type, int mode) {
        Bitmap originalBitmap, filteredBitmap;

        switch(type) {
            case "car":
                switch (mode) {
                    // BMW
                    case 1:
                        // Update immagine e testo
                        carImage.setImageResource(R.drawable.car_bmw_img);
                        break;
                    case 2:
                        // Update immagine e testo
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car_bmw_img);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.DEUTERANOPIA);
                        carImage.setImageBitmap(filteredBitmap);
                        break;
                    case 3:
                        // Update immagine e testo
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car_bmw_img);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.PROTANOPIA);
                        carImage.setImageBitmap(filteredBitmap);
                        break;
                    case 4:
                        // Update immagine e testo
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car_bmw_img);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.TRITANOPIA);
                        carImage.setImageBitmap(filteredBitmap);
                        break;

                    // VOLKSWAGEN
                    case 5:
                        // Update immagine e testo
                        carImage.setImageResource(R.drawable.car_volkswagen_img);
                        break;
                    case 6:
                        // Update immagine e testo
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car_volkswagen_img);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.DEUTERANOPIA);
                        carImage.setImageBitmap(filteredBitmap);
                        break;
                    case 7:
                        // Update immagine e testo
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car_volkswagen_img);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.PROTANOPIA);
                        carImage.setImageBitmap(filteredBitmap);
                        break;
                    case 8:
                        // Update immagine e testo
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car_volkswagen_img);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.TRITANOPIA);
                        carImage.setImageBitmap(filteredBitmap);
                        break;

                    // JEEP
                    case 9:
                        // Update immagine e testo
                        carImage.setImageResource(R.drawable.car_jeep_img);
                        break;
                    case 10:
                        // Update immagine e testo
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car_jeep_img);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.DEUTERANOPIA);
                        carImage.setImageBitmap(filteredBitmap);
                        break;
                    case 11:
                        // Update immagine e testo
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car_jeep_img);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.PROTANOPIA);
                        carImage.setImageBitmap(filteredBitmap);
                        break;
                    case 12:
                        // Update immagine e testo
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car_jeep_img);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.TRITANOPIA);
                        carImage.setImageBitmap(filteredBitmap);
                        break;

                    // SKODA
                    case 13:
                        // Update immagine e testo
                        carImage.setImageResource(R.drawable.car_skoda_img);
                        break;
                    case 14:
                        // Update immagine e testo
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car_skoda_img);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.DEUTERANOPIA);
                        carImage.setImageBitmap(filteredBitmap);
                        break;
                    case 15:
                        // Update immagine e testo
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car_skoda_img);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.PROTANOPIA);
                        carImage.setImageBitmap(filteredBitmap);
                        break;
                    case 16:
                        // Update immagine e testo
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car_skoda_img);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.TRITANOPIA);
                        carImage.setImageBitmap(filteredBitmap);
                        break;

                    // DODGE
                    case 17:
                        // Update immagine e testo
                        carImage.setImageResource(R.drawable.car_dodge_img);
                        break;
                    case 18:
                        // Update immagine e testo
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car_dodge_img);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.DEUTERANOPIA);
                        carImage.setImageBitmap(filteredBitmap);
                        break;
                    case 19:
                        // Update immagine e testo
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car_dodge_img);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.PROTANOPIA);
                        carImage.setImageBitmap(filteredBitmap);
                        break;
                    case 20:
                        // Update immagine e testo
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car_dodge_img);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.TRITANOPIA);
                        carImage.setImageBitmap(filteredBitmap);
                        break;

                    // TOYOTA
                    case 21:
                        // Update immagine e testo
                        carImage.setImageResource(R.drawable.car_toyota_img);
                        break;
                    case 22:
                        // Update immagine e testo
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car_toyota_img);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.DEUTERANOPIA);
                        carImage.setImageBitmap(filteredBitmap);
                        break;
                    case 23:
                        // Update immagine e testo
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car_toyota_img);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.PROTANOPIA);
                        carImage.setImageBitmap(filteredBitmap);
                        break;
                    case 24:
                        // Update immagine e testo
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car_toyota_img);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.TRITANOPIA);
                        carImage.setImageBitmap(filteredBitmap);
                        break;
                }
                break;

            case "profile":
                // 1 - 4    : DEFAULT PROFILE PIC   : [NOR-DEU-PRO-TRI]
                // 5 - 8    : PROFILE PIC 2         : [NOR-DEU-PRO-TRI]
                // 9 - 12   : PROFILE PIC 3         : [NOR-DEU-PRO-TRI]
                // 13 - 16  : PROFILE PIC 4         : [NOR-DEU-PRO-TRI]
                // 17 - 20  : PROFILE PIC 5         : [NOR-DEU-PRO-TRI]
                // 21 - 14  : PROFILE PIC 6         : [NOR-DEU-PRO-TRI]
                switch (mode) {
                    // DEFAULT PROFILE PIC
                    case 1:
                        // UPDATE IMMAGINE NAVBAR LATERALE
                        navbarProfilePic.setImageResource(R.drawable.default_profile_pic);
                        break;
                    case 2:
                        // UPDATE IMMAGINE NAVBAR LATERALE
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_profile_pic);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.DEUTERANOPIA);
                        navbarProfilePic.setImageBitmap(filteredBitmap);
                        break;
                    case 3:
                        // UPDATE IMMAGINE NAVBAR LATERALE
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_profile_pic);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.PROTANOPIA);
                        navbarProfilePic.setImageBitmap(filteredBitmap);
                        break;
                    case 4:
                        // UPDATE IMMAGINE NAVBAR LATERALE
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_profile_pic);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.TRITANOPIA);
                        navbarProfilePic.setImageBitmap(filteredBitmap);
                        break;

                    // PROFILE PIC 2
                    case 5:
                        // UPDATE IMMAGINE NAVBAR LATERALE
                        navbarProfilePic.setImageResource(R.drawable.profile_pic_2);
                        break;
                    case 6:
                        // UPDATE IMMAGINE NAVBAR LATERALE
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_2);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.DEUTERANOPIA);
                        navbarProfilePic.setImageBitmap(filteredBitmap);
                        break;
                    case 7:
                        // UPDATE IMMAGINE NAVBAR LATERALE
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_2);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.PROTANOPIA);
                        navbarProfilePic.setImageBitmap(filteredBitmap);
                        break;
                    case 8:
                        // UPDATE IMMAGINE NAVBAR LATERALE
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_2);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.TRITANOPIA);
                        navbarProfilePic.setImageBitmap(filteredBitmap);
                        break;

                    // PROFILE PIC 3
                    case 9:
                        // UPDATE IMMAGINE NAVBAR LATERALE
                        navbarProfilePic.setImageResource(R.drawable.profile_pic_3);
                        break;
                    case 10:
                        // UPDATE IMMAGINE NAVBAR LATERALE
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_3);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.DEUTERANOPIA);
                        navbarProfilePic.setImageBitmap(filteredBitmap);
                        break;
                    case 11:
                        // UPDATE IMMAGINE NAVBAR LATERALE
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_3);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.PROTANOPIA);
                        navbarProfilePic.setImageBitmap(filteredBitmap);
                        break;
                    case 12:
                        // UPDATE IMMAGINE NAVBAR LATERALE
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_3);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.TRITANOPIA);
                        navbarProfilePic.setImageBitmap(filteredBitmap);
                        break;

                    // PROFILE PIC 4
                    case 13:
                        // UPDATE IMMAGINE NAVBAR LATERALE
                        navbarProfilePic.setImageResource(R.drawable.profile_pic_4);
                        break;
                    case 14:
                        // UPDATE IMMAGINE NAVBAR LATERALE
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_4);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.DEUTERANOPIA);
                        navbarProfilePic.setImageBitmap(filteredBitmap);
                        break;
                    case 15:
                        // UPDATE IMMAGINE NAVBAR LATERALE
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_4);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.PROTANOPIA);
                        navbarProfilePic.setImageBitmap(filteredBitmap);
                        break;
                    case 16:
                        // UPDATE IMMAGINE NAVBAR LATERALE
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_4);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.TRITANOPIA);
                        navbarProfilePic.setImageBitmap(filteredBitmap);
                        break;

                    // PROFILE PIC 5
                    case 17:
                        // UPDATE IMMAGINE NAVBAR LATERALE
                        navbarProfilePic.setImageResource(R.drawable.profile_pic_5);
                        break;
                    case 18:
                        // UPDATE IMMAGINE NAVBAR LATERALE
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_5);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.DEUTERANOPIA);
                        navbarProfilePic.setImageBitmap(filteredBitmap);
                        break;
                    case 19:
                        // UPDATE IMMAGINE NAVBAR LATERALE
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_5);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.PROTANOPIA);
                        navbarProfilePic.setImageBitmap(filteredBitmap);
                        break;
                    case 20:
                        // UPDATE IMMAGINE NAVBAR LATERALE
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_5);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.TRITANOPIA);
                        navbarProfilePic.setImageBitmap(filteredBitmap);
                        break;

                    // PROFILE PIC 6
                    case 21:
                        // UPDATE IMMAGINE NAVBAR LATERALE
                        navbarProfilePic.setImageResource(R.drawable.profile_pic_6);
                        break;
                    case 22:
                        // UPDATE IMMAGINE NAVBAR LATERALE
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_6);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.DEUTERANOPIA);
                        navbarProfilePic.setImageBitmap(filteredBitmap);
                        break;
                    case 23:
                        // UPDATE IMMAGINE NAVBAR LATERALE
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_6);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.PROTANOPIA);
                        navbarProfilePic.setImageBitmap(filteredBitmap);
                        break;
                    case 24:
                        // UPDATE IMMAGINE NAVBAR LATERALE
                        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_6);
                        filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.TRITANOPIA);
                        navbarProfilePic.setImageBitmap(filteredBitmap);
                        break;
                }
                break;
        }
    }
}