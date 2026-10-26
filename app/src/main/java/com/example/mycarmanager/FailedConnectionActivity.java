package com.example.mycarmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class FailedConnectionActivity extends ThemedActivity {
    private MaterialButton tryAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Imposta il layout della pagina
        setContentView(R.layout.activity_failed_connection);

        // Inizializza gli elementi della pagina
        initViews();

        // Inizializzazione listeners
        initListeners();
    }

    private void initViews () {
        tryAgainButton = findViewById(R.id.tryagainButton);
    }

    private void initListeners() {
        // Listener pulsante riprova
        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnToDeviceSelection;
                returnToDeviceSelection = new Intent(FailedConnectionActivity.this, DeviceSelectionActivity.class);
                startActivity(returnToDeviceSelection);
                FailedConnectionActivity.this.finish();
            }
        });
    }
}