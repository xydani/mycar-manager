package com.example.mycarmanager;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import com.google.android.material.button.MaterialButton;

public class ConnectionTutorialActivity extends ThemedActivity {

    MaterialButton tutorialEndButton, cancelConnectionButton;
    public ImageView tutorialImg;
    private Dialog bluetoothDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Imposta il layout della pagina
        setContentView(R.layout.activity_connection_tutorial);

        // Inizializza gli elementi della pagina
        initViews();

        // Immagine del tutorial con il filtro del tema scelto
        setFilteredImage(tutorialImg, R.drawable.odbexample);

        // Inizializzazione listeners
        initListeners();
    }

    private void initViews() {
        cancelConnectionButton = findViewById(R.id.cancelConnectionButton);
        tutorialEndButton = findViewById(R.id.tutorialEndButton);
        tutorialImg = findViewById(R.id.obd2_tutorial_img);
    }

    private void initListeners() {
        // Listener cancellazione della connessione
        cancelConnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Listener tasto chiusura tutorial
        tutorialEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // controllo se il bluetooth è acceso
                // in caso negativo avvisa con un alert
                // in caso positivo continua alla prossima activity
                if(!checkBluetooth()) {
                    bluetoothDialog = new Dialog(ConnectionTutorialActivity.this);
                    bluetoothDialog.setContentView(R.layout.bluetooth_off_dialog);
                    bluetoothDialog.getWindow().getAttributes().windowAnimations = R.style.buttonDialog;
                    bluetoothDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    bluetoothDialog.getWindow().getAttributes().gravity = Gravity.TOP;

                    MaterialButton closeBluetoothDialog = bluetoothDialog.findViewById(R.id.closeDialogButton);

                    closeBluetoothDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            bluetoothDialog.dismiss();
                        }
                    });

                    bluetoothDialog.show();
                }
                else {
                    Intent goToDeviceSelectionActivity;
                    goToDeviceSelectionActivity = new Intent(ConnectionTutorialActivity.this, DeviceSelectionActivity.class);
                    startActivity(goToDeviceSelectionActivity);
                    ConnectionTutorialActivity.this.finish();
                }
            }
        });
    }

    private boolean checkBluetooth() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Se il BluetoothAdapter è nullo o il Bluetooth non è abilitato, restituisci false.
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) { return false; }

        // Se il Bluetooth è disponibile e abilitato, restituisci true.
        return true;
    }}
