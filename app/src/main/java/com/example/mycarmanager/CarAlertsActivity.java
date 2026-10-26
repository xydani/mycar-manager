package com.example.mycarmanager;

import static com.example.mycarmanager.LoginActivity.currentCarIndex;
import static com.example.mycarmanager.LoginActivity.currentUserIndex;
import static com.example.mycarmanager.User.imageMap;
import static com.example.mycarmanager.User.users;

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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class CarAlertsActivity extends AppCompatActivity {
    private User currentUser;
    private Car currentCar;

    public ImageView leftArow, rightArrow;
    public LinearLayout bottomNavbarGarageButton, bottomNavbarManageButton, bottomNavbarMapButton,
            bottomNavbarFeaturesButton, navMenuButton, alertBox1, alertBox2, alertBox3, alertBox4,
            noAlerts;

    private TextView navBarUsername, navbarEmail, carName, carPlate;

    private NavigationView navMenu;
    private CircleImageView navbarProfilePic;
    private MaterialButton navbarGarageButton, navbarManageButton, navbarMapButton, navbarFeaturesButton,
            navbarAccountButton, navbarNewCarButton, navbarColorCorrectionButton,
            navbarLogoutButton;
    private DrawerLayout drawerLayout;

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
        setContentView(R.layout.activity_car_alerts);

        // Inizializza gli elementi della pagina
        initViews();

        // Recupera i dati dell'utente corrente
        currentUser = users.get(currentUserIndex);

        // Aggiorna i dati dell'utente
        updateData(selectedTheme);

        // Imposta gli alert per la macchina corrente
        updateAlertBoxes();

        // Inizializzazione listeners
        initListeners();
    }

    private void initViews () {
        bottomNavbarFeaturesButton = findViewById(R.id.featuresButtonContainer);
        bottomNavbarGarageButton = findViewById(R.id.garageButtonContainer);
        bottomNavbarManageButton = findViewById(R.id.manageButtonContainer);
        bottomNavbarMapButton = findViewById(R.id.mapButtonContainer);
        carName = findViewById(R.id.carName);
        carPlate = findViewById(R.id.carPlate);
        leftArow = findViewById(R.id.garageLeftArrow);
        rightArrow = findViewById(R.id.garageRightArrow);
        navBarUsername = findViewById(R.id.navBarUsername);
        navMenu = findViewById(R.id.nav_view);
        navMenuButton = findViewById(R.id.navMenuButton);
        navbarAccountButton = findViewById(R.id.navbarAccountButton);
        navbarColorCorrectionButton = findViewById(R.id.navbarColorCorrectionButton);
        navbarEmail = findViewById(R.id.navbarEmail);
        navbarFeaturesButton = findViewById(R.id.navbarFeaturesButton);
        navbarGarageButton = findViewById(R.id.navbarGarageButton);
        navbarLogoutButton = findViewById(R.id.navbarLogoutButton);
        navbarManageButton = findViewById(R.id.navbarManageButton);
        navbarMapButton = findViewById(R.id.navbarMapButton);
        navbarNewCarButton = findViewById(R.id.navbarNewCarButton);
        navbarProfilePic = findViewById(R.id.navbarProfilePic);
        drawerLayout = findViewById(R.id.drawerLayout);

        alertBox1 = findViewById(R.id.alertBox1);
        alertBox2 = findViewById(R.id.alertBox2);
        alertBox3 = findViewById(R.id.alertBox3);
        alertBox4 = findViewById(R.id.alertBox4);
        noAlerts = findViewById(R.id.noAlerts);
    }

    private void initListeners () {
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
                intent = new Intent(CarAlertsActivity.this, LoginActivity.class);
                startActivity(intent);
                finishAffinity();
                finish();
            }
        });

        // Listener pulsante "Color Correction" [navbar laterale]
        navbarColorCorrectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;
                intent = new Intent(CarAlertsActivity.this, ColorBlindActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "New Car" [navbar laterale]
        navbarNewCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;
                intent = new Intent(CarAlertsActivity.this, ConnectionTutorialActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Account" [navbar laterale]
        navbarAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;
                intent = new Intent(CarAlertsActivity.this, AccountActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Features" [navbar laterale]
        navbarFeaturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;
                intent = new Intent(CarAlertsActivity.this, CarFeaturesActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Map" [navbar laterale]
        navbarMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;
                intent = new Intent(CarAlertsActivity.this, CarMapActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Manage" [navbar laterale]
        navbarManageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;
                intent = new Intent(CarAlertsActivity.this, CarManageActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Garage" [navbar laterale]
        navbarGarageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;
                intent = new Intent(CarAlertsActivity.this, CarGarageActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Manage" [navbar in basso]
        bottomNavbarManageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goToManageActivity;
                goToManageActivity = new Intent(CarAlertsActivity.this, CarManageActivity.class);
                startActivity(goToManageActivity);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Map" [navbar in basso]
        bottomNavbarMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goToMapActivity;
                goToMapActivity = new Intent(CarAlertsActivity.this, CarMapActivity.class);
                startActivity(goToMapActivity);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Features" [navbar in basso]
        bottomNavbarFeaturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goToFeaturesActivity;
                goToFeaturesActivity = new Intent(CarAlertsActivity.this, CarFeaturesActivity.class);
                startActivity(goToFeaturesActivity);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Garage" [navbar in basso]
        bottomNavbarGarageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goToFeaturesActivity;
                goToFeaturesActivity = new Intent(CarAlertsActivity.this, CarGarageActivity.class);
                startActivity(goToFeaturesActivity);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener freccia selezione auto [dx]
        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextCar(selectedTheme);
            }
        });

        // Listener freccia selezione auto [sx]
        leftArow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prevCar(selectedTheme);
            }
        });
    }

    private void openDrawer() {
        // Apre la navbar laterale
        drawerLayout.openDrawer(GravityCompat.START);
        navMenu.bringToFront();
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
        updateCarInformations();
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
        updateCarInformations();
    }

    private void updateCarInformations() {
        // Informazioni generali dell'auto
        String carBrandName = currentCar.getBrand() + " " + currentCar.getName();
        carName.setText(carBrandName);
        carPlate.setText(currentCar.getPlate());
        updateAlertBoxes();
    }

    private void updateAlertBoxes () {
        // Impostazione logo automobile
        switch (currentCar.getBrand()) {
            case "BMW":
                alertBox1.setVisibility(View.GONE);
                alertBox2.setVisibility(View.GONE);
                alertBox3.setVisibility(View.GONE);
                alertBox4.setVisibility(View.GONE);
                noAlerts.setVisibility(View.VISIBLE);
                break;

            case "Volkswagen":
                alertBox1.setVisibility(View.VISIBLE);
                alertBox2.setVisibility(View.GONE);
                alertBox3.setVisibility(View.GONE);
                alertBox4.setVisibility(View.GONE);
                noAlerts.setVisibility(View.GONE);
                break;

            case "Jeep":
                alertBox1.setVisibility(View.GONE);
                alertBox2.setVisibility(View.VISIBLE);
                alertBox3.setVisibility(View.VISIBLE);
                alertBox4.setVisibility(View.VISIBLE);
                noAlerts.setVisibility(View.GONE);
                break;

            case "Skoda":
                alertBox1.setVisibility(View.GONE);
                alertBox2.setVisibility(View.GONE);
                alertBox3.setVisibility(View.VISIBLE);
                alertBox4.setVisibility(View.VISIBLE);
                noAlerts.setVisibility(View.GONE);
                break;

            case "Dodge":
                alertBox1.setVisibility(View.VISIBLE);
                alertBox2.setVisibility(View.VISIBLE);
                alertBox3.setVisibility(View.VISIBLE);
                alertBox4.setVisibility(View.VISIBLE);
                noAlerts.setVisibility(View.GONE);
                break;

            case "Toyota":
                alertBox1.setVisibility(View.GONE);
                alertBox2.setVisibility(View.GONE);
                alertBox3.setVisibility(View.GONE);
                alertBox4.setVisibility(View.VISIBLE);
                noAlerts.setVisibility(View.GONE);
                break;

            default: break;
        }
    }

    public void updateData(int filter) {
        // Aggiorna informazioni utente [navbar laterale]
        navBarUsername.setText(currentUser.getUsername());
        navbarEmail.setText(currentUser.getEmail());

        // Aggiorna informazioni auto corrente
        currentCar = currentUser.getGarage().get(currentCarIndex);
        updateCarInformations();

        //Immagine del profilo, sia navbar che main page
        Uri uri;
        String stringUri;
        if((stringUri = imageMap.get(currentUser.getUsername())) != null) {
            uri = Uri.parse(stringUri);
            navbarProfilePic.setImageURI(uri);
        } else {
            // Imposta immagine del profilo
            switch (currentUser.getImgPath()) {
                case "default_profile_pic":
                    switch (filter) {
                        case 1:
                            updatePageColors(1);
                            break;
                        case 2:
                            updatePageColors(2);
                            break;
                        case 3:
                            updatePageColors(3);
                            break;
                        case 4:
                            updatePageColors(4);
                            break;
                    }
                    break;

                case "profile_pic_2":
                    switch (filter) {
                        case 1:
                            updatePageColors(5);
                            break;
                        case 2:
                            updatePageColors(6);
                            break;
                        case 3:
                            updatePageColors(7);
                            break;
                        case 4:
                            updatePageColors(8);
                            break;
                    }
                    break;

                case "profile_pic_3":
                    switch (filter) {
                        case 1:
                            updatePageColors(9);
                            break;
                        case 2:
                            updatePageColors(10);
                            break;
                        case 3:
                            updatePageColors(11);
                            break;
                        case 4:
                            updatePageColors(12);
                            break;
                    }
                    break;

                case "profile_pic4":
                    switch (filter) {
                        case 1:
                            updatePageColors(13);
                            break;
                        case 2:
                            updatePageColors(14);
                            break;
                        case 3:
                            updatePageColors(15);
                            break;
                        case 4:
                            updatePageColors(16);
                            break;
                    }
                    break;

                case "profile_pic_5":
                    switch (filter) {
                        case 1:
                            updatePageColors(17);
                            break;
                        case 2:
                            updatePageColors(18);
                            break;
                        case 3:
                            updatePageColors(19);
                            break;
                        case 4:
                            updatePageColors(20);
                            break;
                    }
                    break;

                case "profile_pic_6":
                    switch (filter) {
                        case 1:
                            updatePageColors(21);
                            break;
                        case 2:
                            updatePageColors(22);
                            break;
                        case 3:
                            updatePageColors(23);
                            break;
                        case 4:
                            updatePageColors(24);
                            break;
                    }
                    break;

                default:
                    break;
            }
        }
    }

    public void changeTheme (int newThemeId) {
        switch (newThemeId) {
            case 1:
                CarAlertsActivity.this.setTheme(R.style.BASE_THEME);
                break;
            case 2:
                CarAlertsActivity.this.setTheme(R.style.DEUTERAN_THEME);
                break;
            case 3:
                CarAlertsActivity.this.setTheme(R.style.PROTAN_THEME);
                break;
            case 4:
                CarAlertsActivity.this.setTheme(R.style.TRITAN_THEME);
                break;
        }
    }

    public void updatePageColors (int mode) {
        Bitmap originalBitmap, filteredBitmap;

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
    }

}