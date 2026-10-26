package com.example.mycarmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import static com.example.mycarmanager.LoginActivity.currentUserIndex;
import static com.example.mycarmanager.User.users;
import androidx.appcompat.app.AlertDialog;
import com.google.android.material.button.MaterialButton;

public class DeviceSelectionActivity extends ThemedActivity {
    private MaterialButton cancelConnectionButton, connectButton;
    private String deviceSelected;
    private LinearLayout obdDevice, otherDevice, otherDevice2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        // Listener tasto indietro
        cancelConnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Chi ha già almeno un'auto torna al garage, altrimenti al login
                if(users.get(currentUserIndex).getGarage().size() > 0)
                    showAbortDialog(R.string.abort_connection_garage, CarGarageActivity.class);
                else
                    showAbortDialog(R.string.abort_connection_login, LoginActivity.class);
            }
        });
    }

    // Chiede conferma prima di interrompere la connessione e tornare alla pagina indicata
    private void showAbortDialog(int pageNameId, final Class<?> returnActivity) {
        AlertDialog alertDialog = new AlertDialog.Builder(DeviceSelectionActivity.this, R.style.TutorialAlertDialogStyle).create();
        alertDialog.setTitle(getString(R.string.abort_connection_title));
        alertDialog.setMessage(getString(R.string.abort_connection_message, getString(pageNameId)));

        // Tasto negativo [continua con la connessione]
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.dialog_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        // Tasto affermativo [esci dalla pagina]
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Torna alla pagina già aperta invece di aprirne una nuova
                        Intent returnIntent = new Intent(DeviceSelectionActivity.this, returnActivity);
                        returnIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(returnIntent);
                        DeviceSelectionActivity.this.finish();
                    }
                });
        alertDialog.show();
    }
}