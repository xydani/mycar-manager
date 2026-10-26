package com.example.mycarmanager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import static com.example.mycarmanager.LoginActivity.currentUserIndex;
import static com.example.mycarmanager.User.users;


public class AccountActivity extends NavigationActivity {

    private boolean usrB = false;
    private boolean usrE = false;
    private boolean emlB = false;
    private boolean emlE = false;
    private boolean pswB = false;
    private boolean pswE = false;

    private CircleImageView profilePic;
    private MaterialButton logoutButton, saveProfileButton;
    private TextInputEditText usernameField, emailField, passwordField;
    private TextInputLayout passwordLayout, usernameLayout, emailLayout;
    private ImageView uploadProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Imposta il layout della pagina
        setContentView(R.layout.activity_account);

        // Inizializza gli elementi della pagina
        initViews();

        // Inizializza navbar e dati dell'utente
        initNavigation();

        // Aggiorna i dati dell'utente [immagine profilo e dati profilo]
        updateData();

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
                currentUser.setImageUri(imgUri.toString());
                updateUserData();
                updateData();
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
        profilePic = findViewById(R.id.profilePic);
        logoutButton = findViewById(R.id.logoutButton);
        uploadProfileImage = findViewById(R.id.uploadProfileImage);
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

        // Listener tasto di apertura del pop-up di modifica del profilo
        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfile();
            }
        });

        // Listener pulsante "Logout" [pagina]
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    public void updateData() {

        // Recupera i dati dell'utente corrente
        currentUser = users.get(currentUserIndex);

        // Dati profilo
        usernameField.setText(currentUser.getUsername());
        emailField.setText(currentUser.getEmail());
        passwordField.setText(currentUser.getPassword());

        // Immagine del profilo [main page]
        setProfileImage(profilePic);
    }

    public void checkInputData(){

        saveProfileButton.setEnabled(false);

        usernameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String currentUsername = currentUser.getUsername();

                if(currentUsername.equals(usernameField.getText().toString())){
                    usrB = false;
                    usrE = false;
                    usernameLayout.setHelperText("");
                } else if(isUsernameInUse(usernameField.getText().toString())){
                    usrB = true;
                    usrE = true;
                    usernameLayout.setHelperText("Username already in use.");
                } else if(usernameField.getText().toString().equals("")){
                    usrB = true;
                    usrE = true;
                    usernameLayout.setHelperText("Username cannot be empty");
                } else {
                    usrB = true;
                    usrE = false;
                    usernameLayout.setHelperText("");
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

                if(currentEmail.equals(emailField.getText().toString())){
                    emlB = false;
                    emlE = false;
                    emailLayout.setHelperText("");
                } else if(isEmailInUse(emailField.getText().toString())){
                    emlB = true;
                    emlE = true;
                    emailLayout.setHelperText("Email already in use");
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

    // Ritorna true se lo username è già usato da un utente registrato
    private boolean isUsernameInUse(String username){
        for(User user : users){
            if(user.getUsername().equals(username))
                return true;
        }
        return false;
    }

    // Ritorna true se l'email è già usata da un utente registrato
    private boolean isEmailInUse(String email){
        for(User user : users){
            if(user.getEmail().equals(email))
                return true;
        }
        return false;
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
        updateUserData();
        updateData();
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
}