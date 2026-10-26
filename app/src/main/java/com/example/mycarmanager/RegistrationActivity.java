package com.example.mycarmanager;

import static com.example.mycarmanager.User.users;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import static com.example.mycarmanager.LoginActivity.currentUserIndex;

public class RegistrationActivity extends AppCompatActivity {

    private boolean isSecondPasswordEdited = false;
    private TextView loginLink;
    private MaterialButton signupButton;
    private TextInputEditText registrationUsername, registrationEmail, registrationPassword, registrationPasswordRepeat;
    private TextInputLayout usernameContainer, emailContainer, firstPasswordContainer, secondPasswordContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        loginLink = findViewById(R.id.loginLink);
        signupButton = findViewById(R.id.signupButton);
        registrationUsername = findViewById(R.id.registrationUsername);
        registrationEmail = findViewById(R.id.registrationEmail);
        registrationPassword = findViewById(R.id.registrationPassword);
        registrationPasswordRepeat = findViewById(R.id.registrationPasswordRepeat);
        usernameContainer = findViewById(R.id.usernameContainer);
        emailContainer = findViewById(R.id.emailContainer);
        firstPasswordContainer = findViewById(R.id.firstPasswordContainer);
        secondPasswordContainer = findViewById(R.id.secondPasswordContainer);

        // Controlli input
        initValidation();

        signupButton.setEnabled(false);

        // Quando si clicca sull'apposito link si viene mandati alla pagina di login
        // Tasto indietro: si torna alla pagina di login
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                goToLogin();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToLogin();
            }
        });

        // Quando ci si registra si viene mandati alla pagina di prima connessione all'auto
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isFormValid()) {

                    User newUser = new User
                                    (registrationUsername.getText().toString(),
                                    registrationPassword.getText().toString(),
                                    registrationEmail.getText().toString(),
                                    "default_profile_pic");

                    // Aggiorna il currentUserIndex con l'indice dell'utente appena registrato
                    currentUserIndex = users.indexOf(newUser);

                    Intent goToConnectionTutorial;

                    goToConnectionTutorial = new Intent(RegistrationActivity.this, ConnectionTutorialActivity.class);
                    startActivity(goToConnectionTutorial);
                    RegistrationActivity.this.finish();
                }
            }
        });
    }

    private void goToLogin(){
        Intent goToLoginPage;
        goToLoginPage = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(goToLoginPage);
        RegistrationActivity.this.finish();
    }

    // Ogni campo mostra il proprio errore mentre viene modificato
    private void initValidation(){

        registrationUsername.addTextChangedListener(new InputValidation.InputWatcher() {
            @Override
            public void onInputChanged() {
                int error = InputValidation.checkUsername(registrationUsername.getText().toString());
                usernameContainer.setHelperText(error != InputValidation.VALID ? getString(error) : "");
                updateSignupButton();
            }
        });

        registrationEmail.addTextChangedListener(new InputValidation.InputWatcher() {
            @Override
            public void onInputChanged() {
                int error = InputValidation.checkEmail(registrationEmail.getText().toString());
                emailContainer.setHelperText(error != InputValidation.VALID ? getString(error) : "");
                updateSignupButton();
            }
        });

        registrationPassword.addTextChangedListener(new InputValidation.InputWatcher() {
            @Override
            public void onInputChanged() {
                int error = InputValidation.checkPassword(registrationPassword.getText().toString());
                firstPasswordContainer.setHelperText(error != InputValidation.VALID ? getString(error) : null);

                // Se la conferma è già stata inserita va ricontrollata con la nuova password
                if(isSecondPasswordEdited)
                    checkPasswordsMatch();

                updateSignupButton();
            }
        });

        registrationPasswordRepeat.addTextChangedListener(new InputValidation.InputWatcher() {
            @Override
            public void onInputChanged() {
                isSecondPasswordEdited = true;
                checkPasswordsMatch();
                updateSignupButton();
            }
        });
    }

    // Controlla che la conferma coincida con la password
    private void checkPasswordsMatch(){
        if(passwordsMatch())
            secondPasswordContainer.setHelperText(null);
        else
            secondPasswordContainer.setHelperText(getString(R.string.error_passwords_not_same));
    }

    private boolean passwordsMatch(){
        return registrationPassword.getText().toString().equals(registrationPasswordRepeat.getText().toString());
    }

    // Ritorna true solo se tutti i campi di input sono validi
    private boolean isFormValid(){
        return InputValidation.checkUsername(registrationUsername.getText().toString()) == InputValidation.VALID
                && InputValidation.checkEmail(registrationEmail.getText().toString()) == InputValidation.VALID
                && InputValidation.checkPassword(registrationPassword.getText().toString()) == InputValidation.VALID
                && passwordsMatch();
    }

    // Abilita il pulsante di registrazione solo se tutti i campi sono validi
    private void updateSignupButton(){
        boolean valid = isFormValid();
        int color = valid ? R.color.ok_button : R.color.not_clickable_button;

        signupButton.setEnabled(valid);
        signupButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, color)));
    }
}