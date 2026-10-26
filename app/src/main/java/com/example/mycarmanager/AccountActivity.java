package com.example.mycarmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import static com.example.mycarmanager.LoginActivity.currentUserIndex;
import static com.example.mycarmanager.User.users;
import static com.example.mycarmanager.User.imageMap;


public class AccountActivity extends AppCompatActivity {

    private static boolean usrB = false;
    private static boolean usrE = false;
    private static boolean emlB = false;
    private static boolean emlE = false;
    private static boolean pswB = false;
    private static boolean pswE = false;

    private CircleImageView navbarProfilePic, profilePic;
    private DrawerLayout drawerLayout;
    private int selectedTheme;
    private LinearLayout bottomNavbarFeaturesButton, bottomNavbarGarageButton,
            bottomNavbarManageButton, bottomNavbarMapButton, navMenuButton, bottomNavbarAlertsButton;
    private MaterialButton navbarColorCorrectionButton, navbarFeaturesButton, navbarGarageButton,
            navbarLogoutButton, navbarManageButton, navbarMapButton, navbarNewCarButton, logoutButton,
            saveProfileButton, navbarAlertsButton;
    private NavigationView navMenu;
    public SharedPreferences sharedPreferences;
    private TextView navbarEmail, navBarUsername;
    private User currentUser;
    private TextInputEditText usernameField, emailField, passwordField;
    private TextInputLayout passwordLayout, usernameLayout, emailLayout;
    private ImageView uploadProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Imposta il tema in base alle preferenze
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        selectedTheme = sharedPreferences.getInt("SelectedTheme", 1);
        changeTheme(selectedTheme);

        // Imposta il layout della pagina
        setContentView(R.layout.activity_account);

        // Inizializza gli elementi della pagina
        initViews();

        // Recupera i dati dell'utente corrente
        currentUser = users.get(currentUserIndex);

        // Aggiorna i dati dell'utente [navbar laterale, immagine profilo e dati profilo]
        updateData(selectedTheme);

        // Inizializzazione listeners
        initListeners();

        checkInputData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri imgUri = data.getData();

            String fileType = getContentResolver().getType(imgUri);

            if(fileType != null && (fileType.equals("image/jpeg") || fileType.equals("image/png"))) {

                getContentResolver().takePersistableUriPermission(imgUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                profilePic.setImageURI(imgUri);
                navbarProfilePic.setImageURI(imgUri);
                String imgStringUri = imgUri.toString();
                imageMap.put(currentUser.getUsername(), imgStringUri);
                updateData(selectedTheme);
            } else {
                Toast.makeText(this, "Select a PNG or JPG file", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initViews () {

        // Views necessarie per la modifica del profilo
        saveProfileButton = findViewById(R.id.saveProfileButton);

        usernameField = findViewById(R.id.usernameField);
        usernameLayout = findViewById(R.id.usernameLayout);

        emailField = findViewById(R.id.emailField);
        emailLayout = findViewById(R.id.email);

        passwordField = findViewById(R.id.passwordField);
        passwordLayout = findViewById(R.id.password);

        // Main page
        drawerLayout = findViewById(R.id.drawerLayout);
        profilePic = findViewById(R.id.profilePic);
        logoutButton = findViewById(R.id.logoutButton);
        uploadProfileImage = findViewById(R.id.uploadProfileImage);

        // Navbar [laterale]
        navMenuButton = findViewById(R.id.navMenuButton);
        navbarColorCorrectionButton = findViewById(R.id.navbarColorCorrectionButton);
        navbarEmail = findViewById(R.id.navbarEmail);
        navbarFeaturesButton = findViewById(R.id.navbarFeaturesButton);
        navbarGarageButton = findViewById(R.id.navbarGarageButton);
        navbarLogoutButton = findViewById(R.id.navbarLogoutButton);
        navbarManageButton = findViewById(R.id.navbarManageButton);
        navbarMapButton = findViewById(R.id.navbarMapButton);
        navbarNewCarButton = findViewById(R.id.navbarNewCarButton);
        navbarProfilePic = findViewById(R.id.navbarProfilePic);
        navBarUsername = findViewById(R.id.navBarUsername);
        navbarAlertsButton = findViewById(R.id.navbarAlertsButton);

        // Navbar [in basso]
        navMenu = findViewById(R.id.nav_view);
        bottomNavbarManageButton = findViewById(R.id.manageButtonContainer);
        bottomNavbarFeaturesButton = findViewById(R.id.featuresButtonContainer);
        bottomNavbarGarageButton = findViewById(R.id.garageButtonContainer);
        bottomNavbarMapButton = findViewById(R.id.mapButtonContainer);
        bottomNavbarAlertsButton = findViewById(R.id.alertsButtonContainer);
    }

    public void initListeners() {

        // Listener tasto di upload dell'immagine del profilo
        uploadProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent uploadImageIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                uploadImageIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(uploadImageIntent, 1);
            }
        });

        // Listener tasto di apertura della navbar laterale [alto sx]
        navMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDrawer();
            }
        });

        // Listener tasto di apertura del pop-up di modifica del profilo
        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfile();
            }
        });

        // Listener pulsante "Manage" [navbar in basso]
        bottomNavbarManageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToManageActivity;
                goToManageActivity = new Intent(AccountActivity.this, CarManageActivity.class);
                startActivity(goToManageActivity);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Map" [navbar in basso]
        bottomNavbarMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToMapActivity;
                goToMapActivity = new Intent(AccountActivity.this, CarMapActivity.class);
                startActivity(goToMapActivity);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Garage" [navbar in basso]
        bottomNavbarGarageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToMapActivity;
                goToMapActivity = new Intent(AccountActivity.this, CarGarageActivity.class);
                startActivity(goToMapActivity);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Features" [navbar in basso]
        bottomNavbarFeaturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToFeaturesActivity;
                goToFeaturesActivity = new Intent(AccountActivity.this, CarFeaturesActivity.class);
                startActivity(goToFeaturesActivity);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Alerts" [navbar in basso]
        bottomNavbarAlertsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goToFeaturesActivity;
                goToFeaturesActivity = new Intent(AccountActivity.this, CarAlertsActivity.class);
                startActivity(goToFeaturesActivity);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Color Correction" [navbar laterale]
        navbarColorCorrectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(AccountActivity.this, ColorBlindActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Logout" [navbar laterale]
        navbarLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(AccountActivity.this, LoginActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        // Listener pulsante "Logout" [pagina]
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(AccountActivity.this, LoginActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        // Listener pulsante "New Car" [navbar laterale]
        navbarNewCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(AccountActivity.this, ConnectionTutorialActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Features" [navbar laterale]
        navbarFeaturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(AccountActivity.this, CarFeaturesActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Map" [navbar laterale]
        navbarMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(AccountActivity.this, CarMapActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Manage" [navbar laterale]
        navbarManageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(AccountActivity.this, CarManageActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Garage" [navbar laterale]
        navbarGarageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(AccountActivity.this, CarGarageActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        // Listener pulsante "Alerts" [navbar laterale]
        navbarAlertsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(AccountActivity.this, CarAlertsActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }

    public void updateData(int filter) {

        // Recupera i dati dell'utente corrente
        currentUser = users.get(currentUserIndex);

        // Aggiorna informazioni utente [navbar laterale]
        navBarUsername.setText(currentUser.getUsername());
        navbarEmail.setText(currentUser.getEmail());

        // Dati profilo
        usernameField.setText(currentUser.getUsername());
        emailField.setText(currentUser.getEmail());
        passwordField.setText(currentUser.getPassword());

        //Immagine del profilo, sia navbar che main page
        Uri uri;
        String stringUri;
        if((stringUri = imageMap.get(currentUser.getUsername())) != null){
            uri = Uri.parse(stringUri);
            profilePic.setImageURI(uri);
            navbarProfilePic.setImageURI(uri);
        } else{

            // Imposta immagine del profilo
            switch (currentUser.getImgPath()) {
                case "default_profile_pic":
                    switch (filter) {
                        case 1: updatePageColors(1); break;
                        case 2: updatePageColors(2); break;
                        case 3: updatePageColors(3); break;
                        case 4: updatePageColors(4); break;
                    }
                    break;

                case "profile_pic_2":
                    switch (filter) {
                        case 1: updatePageColors(5); break;
                        case 2: updatePageColors(6); break;
                        case 3: updatePageColors(7); break;
                        case 4: updatePageColors(8); break;
                    }
                    break;

                case "profile_pic_3":
                    switch (filter) {
                        case 1: updatePageColors(9); break;
                        case 2: updatePageColors(10); break;
                        case 3: updatePageColors(11); break;
                        case 4: updatePageColors(12); break;
                    }
                    break;

                case "profile_pic4":
                    switch (filter) {
                        case 1: updatePageColors(13); break;
                        case 2: updatePageColors(14); break;
                        case 3: updatePageColors(15); break;
                        case 4: updatePageColors(16); break;
                    }
                    break;

                case "profile_pic_5":
                    switch (filter) {
                        case 1: updatePageColors(17); break;
                        case 2: updatePageColors(18); break;
                        case 3: updatePageColors(19); break;
                        case 4: updatePageColors(20); break;
                    }
                    break;

                case "profile_pic_6":
                    switch (filter) {
                        case 1: updatePageColors(21); break;
                        case 2: updatePageColors(22); break;
                        case 3: updatePageColors(23); break;
                        case 4: updatePageColors(24); break;
                    }
                    break;

                default: break;
            }
        }
    }

    public void checkInputData(){

        saveProfileButton.setEnabled(false);

        usernameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String currentUsername = currentUser.getUsername();
                int numberOfUsers = users.size();

                for(int c = 0; c < numberOfUsers; c++){
                    if(usernameField.getText().toString().equals(users.get(c).getUsername())){
                        if(currentUsername.equals(usernameField.getText().toString())){
                            usrB = false;
                            usrE = false;
                            usernameLayout.setHelperText("");
                        } else {
                            usrB = true;
                            usrE = true;
                            usernameLayout.setHelperText("Username already in use.");
                        }
                    } else if(usernameField.getText().toString().equals("")){
                        usrB = true;
                        usrE = true;
                        usernameLayout.setHelperText("Username cannot be empty");
                    } else {
                        usrB = true;
                        usrE = false;
                        usernameLayout.setHelperText("");
                    }
                }

                if(checkInputValidation(usrB, emlB, pswB, usrE, emlE, pswE))
                    saveProfileButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.ok_button)));
                else
                    saveProfileButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.not_clickable_button)));
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        emailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String currentEmail = currentUser.getEmail();
                int numberOfUsers = users.size();

                for(int c = 0; c < numberOfUsers; c++){
                    if(emailField.getText().toString().equals(users.get(c).getEmail())){
                        if(currentEmail.equals(emailField.getText().toString())){
                            emlB = false;
                            emlE = false;
                            emailLayout.setHelperText("");
                        } else {
                            emlB = true;
                            emlE = true;
                            emailLayout.setHelperText("Email already in use");
                        }
                    } else if(emailField.getText().toString().equals("")) {
                        emlB = true;
                        emlE = true;
                        emailLayout.setHelperText("Email cannot be empty");
                    } else if(!emailField.getText().toString().matches("^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$")){
                        emlB = true;
                        emlE = true;
                        emailLayout.setHelperText("Invalid email format");
                    } else {
                        emlB = true;
                        emlE = false;
                        emailLayout.setHelperText("");
                    }
                }

                if(checkInputValidation(usrB, emlB, pswB, usrE, emlE, pswE))
                    saveProfileButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.ok_button)));
                else
                    saveProfileButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.not_clickable_button)));
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        passwordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String currentPassword = currentUser.getPassword();

                if(currentPassword.equals(passwordField.getText().toString())){
                    pswB = false;
                    pswE = false;
                    passwordLayout.setHelperText("");
                } else if(passwordField.getText().toString().equals("")) {
                    pswB = true;
                    pswE = true;
                    passwordLayout.setHelperText("Password cannot be empty");
                } else if(passwordField.getText().toString().length() < 8){
                    pswB = true;
                    pswE = true;
                    passwordLayout.setHelperText("Must be at least 8 characters");
                } else if(!passwordField.getText().toString().matches(".*[0-9].*")){
                    pswB = true;
                    pswE = true;
                    passwordLayout.setHelperText("Must contain 1 number");
                } else if(!passwordField.getText().toString().matches(".*[a-z].*")){
                    pswB = true;
                    pswE = true;
                    passwordLayout.setHelperText("Must contain 1 lower-case letter");
                } else if(!passwordField.getText().toString().matches(".*[A-Z].*")){
                    pswB = true;
                    pswE = true;
                    passwordLayout.setHelperText("Must contain 1 upper-case letter");
                } else if(passwordField.getText().toString().matches("[a-zA-Z0-9]*")){
                    pswB = true;
                    pswE = true;
                    passwordLayout.setHelperText("Must contain 1 special character");
                } else {
                    pswB = true;
                    pswE = false;
                    passwordLayout.setHelperText(null);
                }

                if(checkInputValidation(usrB, emlB, pswB, usrE, emlE, pswE))
                    saveProfileButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.ok_button)));
                else
                    saveProfileButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.not_clickable_button)));
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void openDrawer() {
        // Apre la navbar laterale
        drawerLayout.openDrawer(GravityCompat.START);
        navMenu.bringToFront();
    }

    // Ritorna true solo se tutti i campi di input sono validati correttamente
    // Abilita o disabilita di conseguenza il pulsante Save
    public boolean checkInputValidation(boolean usrB, boolean emlB, boolean pswB, boolean usrE, boolean emlE, boolean pswE){
        boolean edited = usrB || emlB ||  pswB;
        boolean errors = !usrE && !emlE && !pswE;

        if(edited && errors){
            saveProfileButton.setEnabled(true);
            return true;
        } else {
            saveProfileButton.setEnabled(false);
            return false;
        }
    }

    public void editProfile() {

        String newUsername = usernameField.getText().toString();
        String newEmail = emailField.getText().toString();
        String newPassword = passwordField.getText().toString();
        users.get(currentUserIndex).setUsername(newUsername);
        users.get(currentUserIndex).setEmail(newEmail);
        users.get(currentUserIndex).setPassword(newPassword);
        updateData(selectedTheme);
        showSuccessToast();
    }

    // Mostra toast di conferma modifica dati profilo
    public void showSuccessToast(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.confirm_toast_layout, (ViewGroup) findViewById(R.id.confirmToastRoot));

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP, 0, 200);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public void changeTheme (int newThemeId) {
        switch (newThemeId) {
            case 1:
                AccountActivity.this.setTheme(R.style.BASE_THEME);
                break;
            case 2:
                AccountActivity.this.setTheme(R.style.DEUTERAN_THEME);
                break;
            case 3:
                AccountActivity.this.setTheme(R.style.PROTAN_THEME);
                break;
            case 4:
                AccountActivity.this.setTheme(R.style.TRITAN_THEME);
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

                // UPDATE IMMAGINE PROFILO
                profilePic.setImageResource(R.drawable.default_profile_pic);
                break;
            case 2:
                // UPDATE IMMAGINE NAVBAR LATERALE
                originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_profile_pic);
                filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.DEUTERANOPIA);
                navbarProfilePic.setImageBitmap(filteredBitmap);

                // UPDATE IMMAGINE PROFILO
                profilePic.setImageBitmap(filteredBitmap);
                break;
            case 3:
                // UPDATE IMMAGINE NAVBAR LATERALE
                originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_profile_pic);
                filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.PROTANOPIA);
                navbarProfilePic.setImageBitmap(filteredBitmap);

                // UPDATE IMMAGINE PROFILO
                profilePic.setImageBitmap(filteredBitmap);
                break;
            case 4:
                // UPDATE IMMAGINE NAVBAR LATERALE
                originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_profile_pic);
                filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.TRITANOPIA);
                navbarProfilePic.setImageBitmap(filteredBitmap);

                // UPDATE IMMAGINE PROFILO
                profilePic.setImageBitmap(filteredBitmap);
                break;

            // PROFILE PIC 2
            case 5:
                // UPDATE IMMAGINE NAVBAR LATERALE
                navbarProfilePic.setImageResource(R.drawable.profile_pic_2);

                // UPDATE IMMAGINE PROFILO
                profilePic.setImageResource(R.drawable.profile_pic_2);
                break;
            case 6:
                // UPDATE IMMAGINE NAVBAR LATERALE
                originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_2);
                filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.DEUTERANOPIA);
                navbarProfilePic.setImageBitmap(filteredBitmap);

                // UPDATE IMMAGINE PROFILO
                profilePic.setImageBitmap(filteredBitmap);
                break;
            case 7:
                // UPDATE IMMAGINE NAVBAR LATERALE
                originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_2);
                filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.PROTANOPIA);
                navbarProfilePic.setImageBitmap(filteredBitmap);

                // UPDATE IMMAGINE PROFILO
                profilePic.setImageBitmap(filteredBitmap);
                break;
            case 8:
                // UPDATE IMMAGINE NAVBAR LATERALE
                originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_2);
                filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.TRITANOPIA);
                navbarProfilePic.setImageBitmap(filteredBitmap);

                // UPDATE IMMAGINE PROFILO
                profilePic.setImageBitmap(filteredBitmap);
                break;

            // PROFILE PIC 3
            case 9:
                // UPDATE IMMAGINE NAVBAR LATERALE
                navbarProfilePic.setImageResource(R.drawable.profile_pic_3);

                // UPDATE IMMAGINE PROFILO
                profilePic.setImageResource(R.drawable.profile_pic_3);
                break;
            case 10:
                // UPDATE IMMAGINE NAVBAR LATERALE
                originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_3);
                filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.DEUTERANOPIA);
                navbarProfilePic.setImageBitmap(filteredBitmap);

                // UPDATE IMMAGINE PROFILO
                profilePic.setImageBitmap(filteredBitmap);
                break;
            case 11:
                // UPDATE IMMAGINE NAVBAR LATERALE
                originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_3);
                filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.PROTANOPIA);
                navbarProfilePic.setImageBitmap(filteredBitmap);

                // UPDATE IMMAGINE PROFILO
                profilePic.setImageBitmap(filteredBitmap);
                break;
            case 12:
                // UPDATE IMMAGINE NAVBAR LATERALE
                originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_3);
                filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.TRITANOPIA);
                navbarProfilePic.setImageBitmap(filteredBitmap);

                // UPDATE IMMAGINE PROFILO
                profilePic.setImageBitmap(filteredBitmap);
                break;

            // PROFILE PIC 4
            case 13:
                // UPDATE IMMAGINE NAVBAR LATERALE
                navbarProfilePic.setImageResource(R.drawable.profile_pic_4);

                // UPDATE IMMAGINE PROFILO
                profilePic.setImageResource(R.drawable.profile_pic_4);
                break;
            case 14:
                // UPDATE IMMAGINE NAVBAR LATERALE
                originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_4);
                filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.DEUTERANOPIA);
                navbarProfilePic.setImageBitmap(filteredBitmap);

                // UPDATE IMMAGINE PROFILO
                profilePic.setImageBitmap(filteredBitmap);
                break;
            case 15:
                // UPDATE IMMAGINE NAVBAR LATERALE
                originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_4);
                filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.PROTANOPIA);
                navbarProfilePic.setImageBitmap(filteredBitmap);

                // UPDATE IMMAGINE PROFILO
                profilePic.setImageBitmap(filteredBitmap);
                break;
            case 16:
                // UPDATE IMMAGINE NAVBAR LATERALE
                originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_4);
                filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.TRITANOPIA);
                navbarProfilePic.setImageBitmap(filteredBitmap);

                // UPDATE IMMAGINE PROFILO
                profilePic.setImageBitmap(filteredBitmap);
                break;

            // PROFILE PIC 5
            case 17:
                // UPDATE IMMAGINE NAVBAR LATERALE
                navbarProfilePic.setImageResource(R.drawable.profile_pic_5);

                // UPDATE IMMAGINE PROFILO
                profilePic.setImageResource(R.drawable.profile_pic_5);
                break;
            case 18:
                // UPDATE IMMAGINE NAVBAR LATERALE
                originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_5);
                filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.DEUTERANOPIA);
                navbarProfilePic.setImageBitmap(filteredBitmap);

                // UPDATE IMMAGINE PROFILO
                profilePic.setImageBitmap(filteredBitmap);
                break;
            case 19:
                // UPDATE IMMAGINE NAVBAR LATERALE
                originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_5);
                filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.PROTANOPIA);
                navbarProfilePic.setImageBitmap(filteredBitmap);

                // UPDATE IMMAGINE PROFILO
                profilePic.setImageBitmap(filteredBitmap);
                break;
            case 20:
                // UPDATE IMMAGINE NAVBAR LATERALE
                originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_5);
                filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.TRITANOPIA);
                navbarProfilePic.setImageBitmap(filteredBitmap);

                // UPDATE IMMAGINE PROFILO
                profilePic.setImageBitmap(filteredBitmap);
                break;

            // PROFILE PIC 6
            case 21:
                // UPDATE IMMAGINE NAVBAR LATERALE
                navbarProfilePic.setImageResource(R.drawable.profile_pic_6);

                // UPDATE IMMAGINE PROFILO
                profilePic.setImageResource(R.drawable.profile_pic_6);
                break;
            case 22:
                // UPDATE IMMAGINE NAVBAR LATERALE
                originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_6);
                filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.DEUTERANOPIA);
                navbarProfilePic.setImageBitmap(filteredBitmap);

                // UPDATE IMMAGINE PROFILO
                profilePic.setImageBitmap(filteredBitmap);
                break;
            case 23:
                // UPDATE IMMAGINE NAVBAR LATERALE
                originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_6);
                filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.PROTANOPIA);
                navbarProfilePic.setImageBitmap(filteredBitmap);

                // UPDATE IMMAGINE PROFILO
                profilePic.setImageBitmap(filteredBitmap);
                break;
            case 24:
                // UPDATE IMMAGINE NAVBAR LATERALE
                originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_pic_6);
                filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.TRITANOPIA);
                navbarProfilePic.setImageBitmap(filteredBitmap);

                // UPDATE IMMAGINE PROFILO
                profilePic.setImageBitmap(filteredBitmap);
                break;
        }
    }

}