package com.example.mycarmanager;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class ConnectionTutorialActivity extends AppCompatActivity {

    MaterialButton tutorialEndButton, cancelConnectionButton;
    public SharedPreferences sharedPreferences;
    public int selectedTheme;
    public ImageView tutorialImg;
    private Dialog bluetoothDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Imposta il tema in base alle preferenze
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        selectedTheme = sharedPreferences.getInt("SelectedTheme", 1);
        changeTheme(selectedTheme);

        // Imposta il layout della pagina
        setContentView(R.layout.activity_connection_tutorial);

        // Inizializza gli elementi della pagina
        initViews();

        // Aggiorna i dati dell'utente
        updatePageColors(selectedTheme);

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
    }

    public void changeTheme (int newThemeId) {
        switch (newThemeId) {
            case 1:
                ConnectionTutorialActivity.this.setTheme(R.style.BASE_THEME);
                break;
            case 2:
                ConnectionTutorialActivity.this.setTheme(R.style.DEUTERAN_THEME);
                break;
            case 3:
                ConnectionTutorialActivity.this.setTheme(R.style.PROTAN_THEME);
                break;
            case 4:
                ConnectionTutorialActivity.this.setTheme(R.style.TRITAN_THEME);
                break;
        }
    }

    public void updatePageColors(int mode) {
        Bitmap originalBitmap, filteredBitmap;
        switch (mode) {
            // IMMAGINE TUTORIAL
            case 1:
                // Update immagine
                tutorialImg.setImageResource(R.drawable.odbexample);
                break;
            case 2:
                // Update immagine
                originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.odbexample);
                filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.DEUTERANOPIA);
                tutorialImg.setImageBitmap(filteredBitmap);
                break;
            case 3:
                // Update immagine
                originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.odbexample);
                filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.PROTANOPIA);
                tutorialImg.setImageBitmap(filteredBitmap);
                break;
            case 4:
                // Update immagine e testo
                originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.odbexample);
                filteredBitmap = ColorBlindFilter.applyFilter(originalBitmap, ColorBlindFilter.ColorBlindType.TRITANOPIA);
                tutorialImg.setImageBitmap(filteredBitmap);
                break;
            }
        }
}