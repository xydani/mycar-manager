package com.example.mycarmanager;

import static com.example.mycarmanager.User.users;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import static com.example.mycarmanager.LoginActivity.currentUserIndex;

public class RegistrationActivity extends AppCompatActivity {

    private static boolean isUsernameEdited = false;
    private static boolean hasUsernameErrors = false;
    private static boolean isEmailEdited = false;
    private static boolean hasEmailErrors = false;
    private static boolean isFirstPasswordEdited = false;
    private static boolean hasFirstPasswordErrors = false;
    private static boolean isSecondPasswordEdited = false;
    private static boolean hasSecondPasswordErrors = false;
    private static boolean isUsernameChecked = false;
    private static boolean isEmailChecked = false;
    private static boolean isFirstPasswordChecked = false;
    private static boolean isSecondPasswordChecked = false;
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
        checkUsername();
        checkEmail();
        checkFirstPassword();
        checkSecondPassword();

        signupButton.setEnabled(false);

        // Quando si clicca sull'apposito link si viene mandati alla pagina di login
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

                if(checkRegistrationData()) {

                    User newUser = new User
                                    (registrationUsername.getText().toString(),
                                    registrationPassword.getText().toString(),
                                    registrationEmail.getText().toString(),
                                    "default_profile_pic");

                    int numberOfUsers = users.size();

                    // Aggiorna il currentUserIndex con l'indice dell'utente appena registrato
                    for (int i = 0; i < numberOfUsers; i++){
                        if(newUser.getUsername().equals(users.get(i).getUsername()) &&
                                newUser.getPassword().equals(users.get(i).getPassword())){

                            currentUserIndex = i;
                        }
                    }

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

    protected boolean checkRegistrationData(){

        return checkInputValidation(isUsernameEdited, isEmailEdited, isFirstPasswordEdited, isSecondPasswordEdited,
                        hasUsernameErrors, hasEmailErrors, hasFirstPasswordErrors, hasSecondPasswordErrors);
    }

    public boolean checkUsername(){

        registrationUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(isUsernameInUse(registrationUsername.getText().toString())){
                    isUsernameEdited = true;
                    hasUsernameErrors = true;
                    usernameContainer.setHelperText("Username already in use");
                } else if(registrationUsername.getText().toString().equals("")){
                    isUsernameEdited = true;
                    hasUsernameErrors = true;
                    usernameContainer.setHelperText("Username cannot be empty");
                } else {
                    isUsernameEdited = true;
                    hasUsernameErrors = false;
                    usernameContainer.setHelperText("");
                }

                isUsernameChecked = checkInputValidation
                        (
                        isUsernameEdited, isEmailEdited, isFirstPasswordEdited, isSecondPasswordEdited,
                        hasUsernameErrors, hasEmailErrors, hasFirstPasswordErrors, hasSecondPasswordErrors
                        );
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        return isUsernameChecked;
    }

    public boolean checkEmail(){

        registrationEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(isEmailInUse(registrationEmail.getText().toString())){
                    isEmailEdited = true;
                    hasEmailErrors = true;
                    emailContainer.setHelperText("Email already in use");
                } else if(registrationEmail.getText().toString().equals("")){
                    isEmailEdited = true;
                    hasEmailErrors = true;
                    emailContainer.setHelperText("Email cannot be empty");
                } else if(!registrationEmail.getText().toString().matches("^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$")){
                    isEmailEdited = true;
                    hasEmailErrors = true;
                    emailContainer.setHelperText("Invalid email format");
                } else {
                    isEmailEdited = true;
                    hasEmailErrors = false;
                    emailContainer.setHelperText("");
                }

                isEmailChecked = checkInputValidation
                        (
                                isUsernameEdited, isEmailEdited, isFirstPasswordEdited, isSecondPasswordEdited,
                                hasUsernameErrors, hasEmailErrors, hasFirstPasswordErrors, hasSecondPasswordErrors
                        );
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        return isEmailChecked;
    }

    public boolean checkFirstPassword(){

        registrationPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(registrationPassword.getText().toString().equals("")) {
                    isFirstPasswordEdited = true;
                    hasFirstPasswordErrors = true;
                    firstPasswordContainer.setHelperText("Password cannot be empty");
                } else if(registrationPassword.getText().toString().length() < 8){
                    isFirstPasswordEdited = true;
                    hasFirstPasswordErrors = true;
                    firstPasswordContainer.setHelperText("Must be at least 8 characters");
                } else if(!registrationPassword.getText().toString().matches(".*[0-9].*")){
                    isFirstPasswordEdited = true;
                    hasFirstPasswordErrors = true;
                    firstPasswordContainer.setHelperText("Must contain 1 number");
                } else if(!registrationPassword.getText().toString().matches(".*[a-z].*")){
                    isFirstPasswordEdited = true;
                    hasFirstPasswordErrors = true;
                    firstPasswordContainer.setHelperText("Must contain 1 lower-case letter");
                } else if(!registrationPassword.getText().toString().matches(".*[A-Z].*")){
                    isFirstPasswordEdited = true;
                    hasFirstPasswordErrors = true;
                    firstPasswordContainer.setHelperText("Must contain 1 upper-case letter");
                } else if(registrationPassword.getText().toString().matches("[a-zA-Z0-9]*")){
                    isFirstPasswordEdited = true;
                    hasFirstPasswordErrors = true;
                    firstPasswordContainer.setHelperText("Must contain 1 special character");
                } else {
                    isFirstPasswordEdited = true;
                    hasFirstPasswordErrors = false;
                    firstPasswordContainer.setHelperText(null);
                }

                isFirstPasswordChecked = checkInputValidation
                        (
                                isUsernameEdited, isEmailEdited, isFirstPasswordEdited, isSecondPasswordEdited,
                                hasUsernameErrors, hasEmailErrors, hasFirstPasswordErrors, hasSecondPasswordErrors
                        );
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        return isFirstPasswordChecked;
    }

    public boolean checkSecondPassword(){

        registrationPasswordRepeat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!registrationPassword.getText().toString().equals(registrationPasswordRepeat.getText().toString())) {
                    isSecondPasswordEdited = true;
                    hasSecondPasswordErrors = true;
                    secondPasswordContainer.setHelperText("Passwords are not the same");
                } else {
                    isSecondPasswordEdited = true;
                    hasSecondPasswordErrors = false;
                    secondPasswordContainer.setHelperText(null);
                }

                isSecondPasswordChecked = checkInputValidation
                        (
                                isUsernameEdited, isEmailEdited, isFirstPasswordEdited, isSecondPasswordEdited,
                                hasUsernameErrors, hasEmailErrors, hasFirstPasswordErrors, hasSecondPasswordErrors
                        );
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        return isSecondPasswordChecked;
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
    public boolean checkInputValidation(boolean... args){
        if(args.length < 8)
            return false;

        boolean edited = args[0] && args[1] && args[2] && args[3];
        boolean errors = !args[4] && !args[5] && !args[6] && !args[7];

        if(edited && errors){
            signupButton.setEnabled(true);
            signupButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.ok_button)));
            return true;
        } else {
            signupButton.setEnabled(false);
            signupButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.not_clickable_button)));
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        goToLogin();
    }
}