package com.example.mycarmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.LinearLayout;
import static com.example.mycarmanager.LoginActivity.currentUserIndex;
import static com.example.mycarmanager.User.users;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class DeviceSelectionActivity extends AppCompatActivity {
    private MaterialButton cancelConnectionButton, connectButton;
    public int selectedTheme;
    public SharedPreferences sharedPreferences;
    private String deviceSelected;
    private LinearLayout obdDevice, otherDevice, otherDevice2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Imposta il tema in base alle preferenze
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        selectedTheme = sharedPreferences.getInt("SelectedTheme", 1);
        changeTheme(selectedTheme);

        // Imposta il layout della pagina
        setContentView(R.layout.activity_device_selection);

        // Inizializza gli elementi della pagina
        initViews();

        // Inizializzazione listeners
        initListeners();
    }

    private void initViews() {
        cancelConnectionButton = findViewById(R.id.cancelConnectionButton);
        connectButton = findViewById(R.id.connectButton);
        obdDevice = findViewById(R.id.obdDevice);
        otherDevice = findViewById(R.id.otherDevice);
        otherDevice2 = findViewById(R.id.otherDevice2);

        // Inizializzazione "deviceSelected" per evitare nullPointerException quando non si seleziona nulla
        deviceSelected = "";
    }

    private void initListeners() {
        // Listener selezione device funzionante [tabella - alto sx]
        obdDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deviceSelected = "obd";
                obdDevice.setBackgroundColor(0xFFA3C0CD);
                otherDevice.setBackgroundColor(0x00000000);
                otherDevice2.setBackgroundColor(0x00000000);
            }
        });

        // Listener selezione device non funzionante [tabella - basso sx]
        otherDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deviceSelected = "other";
                otherDevice.setBackgroundColor(0xFFA3C0CD);
                obdDevice.setBackgroundColor(0x00000000);
                otherDevice2.setBackgroundColor(0x00000000);
            }
        });

        // Listener selezione device non funzionante [tabella - basso sx]
        otherDevice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deviceSelected = "other";
                otherDevice2.setBackgroundColor(0xFFA3C0CD);
                obdDevice.setBackgroundColor(0x00000000);
                otherDevice.setBackgroundColor(0x00000000);
            }
        });

        // Listener tasto connessione
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(deviceSelected.equals("obd")) {
                    if(users.get(currentUserIndex).getGarage().size() >= 6){

                        Intent failedConnection;
                        failedConnection = new Intent(DeviceSelectionActivity.this, FailedConnectionActivity.class);
                        startActivity(failedConnection);
                        DeviceSelectionActivity.this.finish();
                    } else {

                        Intent successfullConnection;
                        successfullConnection = new Intent(DeviceSelectionActivity.this, SuccessfullConnectionActivity.class);
                        startActivity(successfullConnection);
                        DeviceSelectionActivity.this.finish();
                    }
                }
                else if (deviceSelected.equals("other")) {
                    Intent failedConnection;
                    failedConnection = new Intent(DeviceSelectionActivity.this, FailedConnectionActivity.class);
                    startActivity(failedConnection);
                    DeviceSelectionActivity.this.finish();
                }
            }
        });

        // DA IMPLEMENTARE: Sistema che redirecti l'utente al garage se loggato, altrimenti al login
        // Listener tasto indietro
        cancelConnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(users.get(currentUserIndex).getGarage().size() > 0){

                    AlertDialog alertDialog = new AlertDialog.Builder(DeviceSelectionActivity.this, R.style.TutorialAlertDialogStyle).create();
                    alertDialog.setTitle("ABORT CONNECTION?");
                    alertDialog.setMessage("Are you sure you want to abort the connection process?" +
                            " By clicking YES you will be redirect to the Garage page!");

                    // Tasto negativo [continua con la connessione]
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    // Tasto affermativo [esci dalla pagina]
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent returnToGarage;
                                    returnToGarage = new Intent(DeviceSelectionActivity.this, CarGarageActivity.class);
                                    startActivity(returnToGarage);
                                    DeviceSelectionActivity.this.finish();
                                }
                            });
                    alertDialog.show();
                } else {

                    AlertDialog alertDialog = new AlertDialog.Builder(DeviceSelectionActivity.this, R.style.TutorialAlertDialogStyle).create();
                    alertDialog.setTitle("ABORT CONNECTION?");
                    alertDialog.setMessage("Are you sure you want to abort the connection process?" +
                            " By clicking YES you will be redirect to the Login page!");

                    // Tasto negativo [continua con la connessione]
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    // Tasto affermativo [esci dalla pagina]
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent returnToLogin;
                                    returnToLogin = new Intent(DeviceSelectionActivity.this, LoginActivity.class);
                                    startActivity(returnToLogin);
                                    DeviceSelectionActivity.this.finish();
                                }
                            });
                    alertDialog.show();
                }
            }
        });
    }

    public void changeTheme (int newThemeId) {
        switch (newThemeId) {
            case 1:
                DeviceSelectionActivity.this.setTheme(R.style.BASE_THEME);
                break;
            case 2:
                DeviceSelectionActivity.this.setTheme(R.style.DEUTERAN_THEME);
                break;
            case 3:
                DeviceSelectionActivity.this.setTheme(R.style.PROTAN_THEME);
                break;
            case 4:
                DeviceSelectionActivity.this.setTheme(R.style.TRITAN_THEME);
                break;
        }
    }
}