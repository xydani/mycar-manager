package com.example.mycarmanager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import static com.example.mycarmanager.LoginActivity.currentUserIndex;
import static com.example.mycarmanager.User.users;


public class AccountActivity extends NavigationActivity {

    private CircleImageView profilePic;
    private MaterialButton logoutButton, saveProfileButton;
    private TextInputEditText usernameField, emailField, passwordField;
    private TextInputLayout passwordLayout, usernameLayout, emailLayout;
    private ImageView uploadProfileImage;

    // Selezione dell'immagine del profilo dai documenti del dispositivo
    private final ActivityResultLauncher<String[]> pickProfileImage = registerForActivityResult(
            new ActivityResultContracts.OpenDocument(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri imgUri) {
                    // imgUri è null se l'utente chiude la selezione senza scegliere un file
                    if(imgUri != null)
                        updateProfileImage(imgUri);
                }
            });

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

        // Controlli input
        initValidation();
    }

    private void updateProfileImage(Uri imgUri){
        String fileType = getContentResolver().getType(imgUri);

        if(fileType != null && (fileType.equals("image/jpeg") || fileType.equals("image/png"))) {

            // Mantiene l'accesso al file anche dopo il riavvio dell'activity
            getContentResolver().takePersistableUriPermission(imgUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            currentUser.setImageUri(imgUri.toString());
            updateUserData();
            updateData();
        } else {
            Toast.makeText(this, R.string.error_image_type, Toast.LENGTH_SHORT).show();
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

                pickProfileImage.launch(new String[] {"image/jpeg", "image/png"});
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

    // Ogni campo mostra il proprio errore mentre viene modificato
    // Un valore uguale a quello attuale dell'utente è sempre valido
    private void initValidation(){

        saveProfileButton.setEnabled(false);

        usernameField.addTextChangedListener(new InputValidation.InputWatcher() {
            @Override
            public void onInputChanged() {
                int error = getUsernameError();
                usernameLayout.setHelperText(error != InputValidation.VALID ? getString(error) : "");
                updateSaveButton();
            }
        });

        emailField.addTextChangedListener(new InputValidation.InputWatcher() {
            @Override
            public void onInputChanged() {
                int error = getEmailError();
                emailLayout.setHelperText(error != InputValidation.VALID ? getString(error) : "");
                updateSaveButton();
            }
        });

        passwordField.addTextChangedListener(new InputValidation.InputWatcher() {
            @Override
            public void onInputChanged() {
                if(passwordField.getText().toString().equals(currentUser.getPassword()))
                    passwordLayout.setHelperText("");
                else if(getPasswordError() != InputValidation.VALID)
                    passwordLayout.setHelperText(getString(getPasswordError()));
                else
                    passwordLayout.setHelperText(null);

                updateSaveButton();
            }
        });
    }

    private int getUsernameError(){
        String username = usernameField.getText().toString();
        return username.equals(currentUser.getUsername()) ? InputValidation.VALID : InputValidation.checkUsername(username);
    }

    private int getEmailError(){
        String email = emailField.getText().toString();
        return email.equals(currentUser.getEmail()) ? InputValidation.VALID : InputValidation.checkEmail(email);
    }

    private int getPasswordError(){
        String password = passwordField.getText().toString();
        return password.equals(currentUser.getPassword()) ? InputValidation.VALID : InputValidation.checkPassword(password);
    }

    // Abilita il pulsante Save solo se almeno un campo è stato modificato e tutti i campi sono validi
    private void updateSaveButton(){
        boolean edited = !usernameField.getText().toString().equals(currentUser.getUsername())
                || !emailField.getText().toString().equals(currentUser.getEmail())
                || !passwordField.getText().toString().equals(currentUser.getPassword());
        boolean valid = getUsernameError() == InputValidation.VALID && getEmailError() == InputValidation.VALID
                && getPasswordError() == InputValidation.VALID;
        int color = edited && valid ? R.color.ok_button : R.color.not_clickable_button;

        saveProfileButton.setEnabled(edited && valid);
        saveProfileButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, color)));
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