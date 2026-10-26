package com.example.mycarmanager;

import static com.example.mycarmanager.User.users;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    public static int currentCarIndex = 0;
    public static int currentUserIndex = 0;
    private TextView signupLink, loginErrorMessage;
    private TextInputEditText usernameLogin, passwordLogin;
    private MaterialButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // L'utente demo viene creato una sola volta, al primo avvio dell'applicazione
        if(users.isEmpty()){
            new User("user", "user",
                    "user@email.it", "default_profile_pic");
        }

        signupLink = findViewById(R.id.signupLink);
        loginButton = findViewById(R.id.loginButton);
        usernameLogin = findViewById(R.id.usernameLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        loginErrorMessage = findViewById(R.id.loginErrorMessage);

        // Ogni volta che si accede a questa activity i valori vengono reimpostati su default
        currentCarIndex = 0;
        currentUserIndex = 0;

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Controllo input login
                if (checkUserData()){

                    // Riporto la visibilità del messaggio di errore a GONE
                    if(loginErrorMessage.getVisibility() == View.VISIBLE)
                        loginErrorMessage.setVisibility(View.GONE);

                    // Se l'utente registrato non ha ancora auto nel garage viene indirizzato
                    // alla pagine di connessione alla vettura
                    // Prima di poter entrare nel garage deve avere almeno un auto collegata
                    if(users.get(currentUserIndex).getGarage().isEmpty()){

                        Intent goToConnectionTutorial;

                        goToConnectionTutorial = new Intent(LoginActivity.this, ConnectionTutorialActivity.class);
                        startActivity(goToConnectionTutorial);
                    } else {

                        Intent goToHomePage;
                        goToHomePage = new Intent(LoginActivity.this, CarGarageActivity.class);
                        startActivity(goToHomePage);
                    }

                } else {

                    // Messaggio di errore quando dati login errati
                    loginErrorMessage.setVisibility(View.VISIBLE);
                }
            }
        });

        // Quando si clicca sull'apposito link si viene mandati alla pagina di registrazione
        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goToSignupPage;
                goToSignupPage = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(goToSignupPage);
            }
        });
    }

    protected boolean checkUserData(){
        int numberOfUsers = users.size();

        for (int i = 0; i < numberOfUsers; i++){
            if(usernameLogin.getText().toString().equals(users.get(i).getUsername()) &&
                    passwordLogin.getText().toString().equals(users.get(i).getPassword())){

                currentUserIndex = i;

                return true;
            }
        }
        return false;
    }

    // finishAffinity chiude tutte le activity dello stack, quindi l'applicazione
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}